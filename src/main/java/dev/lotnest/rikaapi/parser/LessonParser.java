package dev.lotnest.rikaapi.parser;

import dev.lotnest.rikaapi.enums.EnumLessonType;
import dev.lotnest.rikaapi.model.lesson.AbstractLesson;
import dev.lotnest.rikaapi.model.lesson.impl.ExerciseLesson;
import dev.lotnest.rikaapi.model.lesson.impl.LanguageLesson;
import dev.lotnest.rikaapi.model.lesson.impl.LectureLesson;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Getter
public class LessonParser {

    private final @NotNull String textToParse;

    public LessonParser(@NotNull String textToParse) {
        this.textToParse = textToParse.toLowerCase(Locale.ROOT);
    }

    public @NotNull Optional<EnumLessonType> parseType() {
        for (EnumLessonType lessonType : EnumLessonType.values()) {
            if (textToParse.contains(lessonType.getIdentifyingKeyword())) {
                return Optional.of(lessonType);
            }
        }
        return Optional.empty();
    }

    public @NotNull AbstractLesson parse(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String code, @NotNull EnumLessonType type, @NotNull String room) {
        return switch (type) {
            case LECTURE -> new LectureLesson(startTime, endTime, code, room);
            case EXERCISE -> new ExerciseLesson(startTime, endTime, code, room);
            case LANGUAGE -> new LanguageLesson(startTime, endTime, code, room);
            default -> new AbstractLesson(startTime, endTime, code, type, room) {
                @Override
                public @NotNull LocalDateTime getStartTime() {
                    return startTime;
                }

                @Override
                public @NotNull LocalDateTime getEndTime() {
                    return endTime;
                }

                @Override
                public @NotNull String getCode() {
                    return code;
                }

                @Override
                public @NotNull EnumLessonType getType() {
                    return type;
                }

                @Override
                public @NotNull String getRoom() {
                    return room;
                }
            };
        };
    }
}
