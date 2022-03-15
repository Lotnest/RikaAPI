package dev.lotnest.rikaapi.registry;

import dev.lotnest.rikaapi.enums.EnumSemester;
import dev.lotnest.rikaapi.mapper.PlanMapper;
import dev.lotnest.rikaapi.model.lesson.Plan;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class PlanRegistry {

    public static final EnumSemester CURRENT_SEMESTER = EnumSemester.FOUR;
    public static final String PLANS_PATH = "src/main/resources/plans/";
    @NotNull
    private static final List<Plan> PLANS = initializePlans();

    private static @NotNull List<Plan> initializePlans() {
        List<Plan> plans = new LinkedList<>();
        PlanMapper planMapper = new PlanMapper();

        Plan currentSemesterPlan = planMapper.mapFromSemester(CURRENT_SEMESTER);
        plans.add(currentSemesterPlan);
        return plans;
    }

    @Contract(" -> new")
    public static @NotNull List<Plan> getPlans() {
        return new LinkedList<>(PLANS);
    }

    public static boolean addPlan(@NotNull Plan plan) {
        return PLANS.add(plan);
    }

    public static boolean removePlan(@NotNull Plan plan) {
        return PLANS.remove(plan);
    }

    public static Plan getPlan(int index) {
        return PLANS.get(index);
    }
}
