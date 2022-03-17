package dev.lotnest.rikaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RikaApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RikaApiApplication.class, args);
    }
}
