package dev.lotnest.rikaapi;

import dev.lotnest.rikaapi.utils.TimeUtils;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RikaApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RikaApiApplication.class, args);
        startDynoWakeUpTask();
    }

    @SneakyThrows
    private static void startDynoWakeUpTask() {
        URL url = new URL("https://rika-bot-api.herokuapp.com/");
        TimeUtils.SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
                    try {
                        url.openConnection();
                    } catch (IOException ignored) {
                    }
                },
                0L,
                30L,
                TimeUnit.MINUTES);
    }
}
