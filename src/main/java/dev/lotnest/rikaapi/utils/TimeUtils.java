package dev.lotnest.rikaapi.utils;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TimeUtils {

    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static final DateTimeFormatter PLAN_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    private TimeUtils() {
    }

    public static @NotNull String getTimeLeftString(@NotNull LocalDateTime dateTime) {
        Duration duration = Duration.ofMillis(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis());
        long durationDaysPart = duration.toDaysPart();
        long durationHoursPart = duration.toHoursPart();
        long durationMinutesPart = duration.toMinutesPart();
        long durationSecondsPart = duration.toSecondsPart();

        return durationDaysPart > 0 ? String.format("**%02d** dni **%02d** godzin **%02d** minut **%02d** sekund", durationDaysPart, durationHoursPart, durationMinutesPart, durationSecondsPart) :
                durationHoursPart > 0 ? String.format("**%02d** godzin **%02d** minut **%02d** sekund", durationHoursPart, durationMinutesPart, durationSecondsPart) :
                        durationMinutesPart > 0 ? String.format("**%02d** minut **%02d** sekund", durationMinutesPart, durationSecondsPart) :
                                durationSecondsPart > 0 ? String.format("**%02d** sekund", durationSecondsPart) :
                                        "**W trakcie lub skończona**";
    }
}
