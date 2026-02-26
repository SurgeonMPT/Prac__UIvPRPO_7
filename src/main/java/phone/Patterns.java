package phone;

import java.util.regex.Pattern;

public final class Patterns {
    private Patterns() {}

    public static final Pattern PHONE = Pattern.compile(
            "(?<!\\w)" +                // не начало слова
                    "(?:\\+?\\d{1,3}[\\s-]?)?" + // опциональный код страны
                    "\\(?\\d{2,4}\\)?" +         // код города/оператора
                    "[\\s-]?" +                  // разделитель
                    "\\d{2,4}" +                 // первая часть номера
                    "[\\s-]?" +                  // разделитель
                    "\\d{2,4}" +                 // вторая часть
                    "(?:[\\s-]?\\d{2,4})?" +     // опциональная третья часть
                    "(?!\\d)"                    // не продолжение цифры
    );
}