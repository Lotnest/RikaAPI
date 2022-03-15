package dev.lotnest.rikaapi.service;

import dev.lotnest.rikaapi.model.lesson.AbstractLesson;
import dev.lotnest.rikaapi.model.lesson.Plan;
import dev.lotnest.rikaapi.registry.PlanRegistry;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PlanService {

    public static final Plan CURRENT_PLAN = PlanRegistry.getPlan(0);
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private boolean isStartTimeAfterOrEqual(@NotNull LocalDateTime startTime1, @NotNull LocalDateTime startTime2) {
        return startTime1.isAfter(startTime2) || startTime1.isEqual(startTime2);
    }

    private boolean isEndTimeBeforeOrEqual(@NotNull LocalDateTime endTime1, @NotNull LocalDateTime endTime2) {
        return endTime1.isBefore(endTime2) || endTime1.isEqual(endTime2);
    }

    public @NotNull CompletableFuture<List<AbstractLesson>> getLessonsDuringPeriod(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {
        return getLessonsDuringPeriod(startTime, endTime, Long.MAX_VALUE);
    }

    public @NotNull CompletableFuture<List<AbstractLesson>> getLessonsDuringPeriod(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, long limit) {
        return CompletableFuture.supplyAsync(() -> CURRENT_PLAN.getLessons().stream()
                .filter(lesson -> isStartTimeAfterOrEqual(lesson.getStartTime(), startTime) &&
                        isEndTimeBeforeOrEqual(lesson.getEndTime(), endTime))
                .limit(limit)
                .collect(Collectors.toList()), EXECUTOR_SERVICE);
    }

    @SneakyThrows
    public @NotNull AbstractLesson getNextLesson() {
        LocalDateTime now = LocalDateTime.now();
        return getLessonsDuringPeriod(now, now.plusMonths(1), 1)
                .get(10L, TimeUnit.SECONDS)
                .get(0);
    }

    public @NotNull List<AbstractLesson> getLessons() {
        return CURRENT_PLAN.getLessons();
    }
}
