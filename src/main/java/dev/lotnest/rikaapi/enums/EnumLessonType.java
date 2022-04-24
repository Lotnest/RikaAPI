package dev.lotnest.rikaapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public enum EnumLessonType {

    LECTURE("wykład"),
    EXERCISE("ćwiczenia"),
    LANGUAGE("lek"),
    EXAM("egzamin"),
    UNKNOWN("nieznany");

    @NotNull
    private final String typeName;

    @Override
    public String toString() {
        if (this.equals(EnumLessonType.LANGUAGE)) {
            return "lektorat";
        }
        return typeName;
    }
}
