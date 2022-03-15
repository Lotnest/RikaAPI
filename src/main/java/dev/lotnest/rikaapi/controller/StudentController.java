package dev.lotnest.rikaapi.controller;

import dev.lotnest.rikaapi.validator.StudentValidator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/student", method = RequestMethod.GET)
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/validate/{input}")
    public ResponseEntity<Boolean> validateStudent(@PathVariable("input") @NotNull String input) {
        StudentValidator studentValidator = new StudentValidator(input);
        boolean isValidStudent = studentValidator.matches();
        LOGGER.info("Validating student: {} (result: {})", input, isValidStudent);
        return ResponseEntity.ok(isValidStudent);
    }
}
