package dev.lotnest.rikaapi.model.lesson.impl;

import dev.lotnest.rikaapi.enums.EnumLessonType;
import dev.lotnest.rikaapi.model.lesson.AbstractLesson;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class LectureLesson extends AbstractLesson {

    public LectureLesson(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String code, @NotNull String room) {
        super(startTime, endTime, code, EnumLessonType.LECTURE.getIdentifyingKeyword(), room);
    }
}
