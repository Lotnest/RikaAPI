package dev.lotnest.rikaapi.model.lesson;

import dev.lotnest.rikaapi.enums.EnumLessonType;
import dev.lotnest.rikaapi.enums.EnumSemester;
import dev.lotnest.rikaapi.registry.PlanRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class AbstractLesson {

    private final @NotNull EnumSemester semester = PlanRegistry.CURRENT_SEMESTER;
    private final int semesterNumber = semester.getNumber();
    private final @NotNull LocalDateTime startTime;
    private final @NotNull LocalDateTime endTime;
    private final @NotNull String code;
    private final @NotNull EnumLessonType type;
    private final @NotNull String identifyingKeyword = type.getIdentifyingKeyword();
    private final @NotNull String room;
}
