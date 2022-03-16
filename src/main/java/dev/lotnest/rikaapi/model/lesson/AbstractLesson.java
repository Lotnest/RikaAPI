package dev.lotnest.rikaapi.model.lesson;

import dev.lotnest.rikaapi.enums.EnumLessonType;
import dev.lotnest.rikaapi.enums.EnumSemester;
import dev.lotnest.rikaapi.registry.PlanRegistry;
import dev.lotnest.rikaapi.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Getter
public class AbstractLesson {

    private final @NotNull EnumSemester semester = PlanRegistry.CURRENT_SEMESTER;
    private final int semesterNumber = semester.getNumber();
    private final @NotNull LocalDateTime startTime;
    private final @NotNull LocalDateTime endTime;
    private final @NotNull String code;
    private final @NotNull EnumLessonType type;
    private final @NotNull String identifyingKeyword;
    private final @NotNull String room;
    @Setter
    private @Nullable String timeLeft;

    public AbstractLesson(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String code, @NotNull EnumLessonType type, @NotNull String room) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.code = code;
        this.type = type;
        this.room = room;
        identifyingKeyword = type.getIdentifyingKeyword();
    }
}
