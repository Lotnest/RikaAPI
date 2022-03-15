package dev.lotnest.rikaapi.controller;

import dev.lotnest.rikaapi.model.lesson.AbstractLesson;
import dev.lotnest.rikaapi.service.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/lesson", method = RequestMethod.GET)
public class LessonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LessonController.class);

    @Autowired
    private PlanService planService;

    @GetMapping("/all")
    public ResponseEntity<List<AbstractLesson>> getLessons() {
        return ResponseEntity.ok(planService.getLessons());
    }

    @GetMapping(value = "/next")
    public ResponseEntity<AbstractLesson> getNextLesson() {
        return ResponseEntity.ok(planService.getNextLesson());
    }
}
