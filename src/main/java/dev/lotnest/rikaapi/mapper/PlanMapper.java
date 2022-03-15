package dev.lotnest.rikaapi.mapper;

import dev.lotnest.rikaapi.enums.EnumLessonType;
import dev.lotnest.rikaapi.enums.EnumSemester;
import dev.lotnest.rikaapi.model.lesson.AbstractLesson;
import dev.lotnest.rikaapi.model.lesson.Plan;
import dev.lotnest.rikaapi.parser.LessonParser;
import dev.lotnest.rikaapi.utils.TimeUtils;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PlanMapper {

    @SneakyThrows
    public Plan mapFromSemester(@NotNull EnumSemester enumSemester) {
        Plan result = new Plan(enumSemester);
        File planFile = new File(result.getPlanFileName());

        if (!planFile.exists()) {
            throw new IOException(String.format("File does not exist: '%s'", result.getPlanFileName()));
        }

        JSONTokener tokener = new JSONTokener(new InputStreamReader(new FileInputStream(planFile)));
        JSONObject root = new JSONObject(tokener);

        JSONArray vCalendar = root.getJSONArray("VCALENDAR");
        JSONObject vCalendarObject = vCalendar.getJSONObject(0);
        JSONArray vEvents = vCalendarObject.getJSONArray("VEVENT");
        List<AbstractLesson> lessons = new LinkedList<>();

        for (int i = 0; i < vEvents.length(); i++) {
            JSONObject lessonJson = vEvents.getJSONObject(i);

            LocalDateTime dtStart = LocalDateTime.parse(lessonJson.getString("DTSTART"), TimeUtils.PLAN_DATE_TIME_FORMATTER).plusHours(1);
            LocalDateTime dtEnd = LocalDateTime.parse(lessonJson.getString("DTEND"), TimeUtils.PLAN_DATE_TIME_FORMATTER).plusHours(1);

            String[] summary = lessonJson.getString("SUMMARY").split(" ");
            String lessonCode = summary[0];

            LessonParser lessonParser = new LessonParser(summary[1]);
            EnumLessonType enumLessonType = lessonParser.parseType().orElse(findExamKeyword(summary));

            if (enumLessonType.equals(EnumLessonType.EXERCISE)) {
                lessonParser = new LessonParser(summary[0]);
                enumLessonType = lessonParser.parseType().orElse(EnumLessonType.EXERCISE);
            }

            String lessonRoom = getLessonRoomFromSummary(summary);

            AbstractLesson lesson = lessonParser.parse(dtStart, dtEnd, lessonCode, enumLessonType, lessonRoom);
            lessons.add(lesson);
        }

        result.setLessons(lessons);

        return result;
    }

    @NotNull
    private EnumLessonType findExamKeyword(String @NotNull [] summary) {
        int examKeywordIndex = -1;
        for (int i = 0; i < summary.length; i++) {
            if (summary[i].equals(EnumLessonType.EXAM.getIdentifyingKeyword())) {
                examKeywordIndex = i;
            }
        }

        if (examKeywordIndex == -1) {
            return EnumLessonType.UNKNOWN;
        }

        return EnumLessonType.EXAM;
    }

    @NotNull
    private String getLessonRoomFromSummary(String @NotNull [] summary) {
        if (summary.length == 4) {
            return summary[3];
        } else {
            StringBuilder lessonRoomBuilder = new StringBuilder();
            for (int i = 3; i < summary.length; i++) {
                if (!Objects.equals(summary[i], EnumLessonType.LANGUAGE.getIdentifyingKeyword())
                        && !Objects.equals(summary[i], EnumLessonType.EXAM.getIdentifyingKeyword())
                        && !summary[i].equals("s.")) {
                    lessonRoomBuilder.append(summary[i])
                            .append(" ");
                }
            }

            return lessonRoomBuilder.toString();
        }
    }
}
