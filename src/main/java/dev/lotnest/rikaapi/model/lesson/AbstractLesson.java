package dev.lotnest.rikaapi.model.lesson;

import dev.lotnest.rikaapi.enums.EnumLessonType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class AbstractLesson {

    private @NotNull LocalDateTime startTime;
    private @NotNull LocalDateTime endTime;
    private @NotNull String code;
    private @NotNull EnumLessonType type;
    private @NotNull String room;
}
