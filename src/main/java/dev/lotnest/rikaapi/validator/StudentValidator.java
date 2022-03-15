package dev.lotnest.rikaapi.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Getter
public class StudentValidator {

    public static final Pattern STUDENT_REGEX = Pattern.compile("[Ss](\\d){3,5},? [AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż]{2,},? [AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż]{2,}");

    private final @NotNull String input;

    public boolean matches() {
        return STUDENT_REGEX.matcher(input).matches();
    }
}
