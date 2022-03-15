package dev.lotnest.rikaapi.model.lesson;

import dev.lotnest.rikaapi.enums.EnumSemester;
import dev.lotnest.rikaapi.registry.PlanRegistry;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Plan {

    private final @NotNull String planFileName;
    private final int semester;
    private @NotNull List<AbstractLesson> lessons;

    public Plan(@NotNull EnumSemester enumSemester) {
        this.semester = enumSemester.getNumber();
        this.planFileName = PlanRegistry.PLANS_PATH + "PlanSem{0}.json".replace("{0}", String.valueOf(semester));
        lessons = new LinkedList<>();
    }
}
