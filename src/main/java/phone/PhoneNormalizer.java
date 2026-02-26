package phone;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNormalizer {
    private static final String TARGET_COUNTRY = "1"; // +1 для США/Канады

    public String normalizePhoneNumbers(String text) {
        text = normalizeWhitespace(text);
        Matcher m = Patterns.PHONE.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String normalized = cleanAndNormalize(m.group());
            m.appendReplacement(sb, Matcher.quoteReplacement(normalized));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String normalizeWhitespace(String s) {
        return s.replace('\u00A0', ' ')
                .replace('\u202F', ' ')
                .replace('\u2007', ' ')
                .replace("\r\n", "\n")
                .replace('\r', '\n')
                .replace('\t', ' ');
    }

    private String cleanAndNormalize(String raw) {
        // Оставляем только цифры
        String digits = raw.replaceAll("\\D+", "");
        if (digits.length() < 7) return raw; // слишком коротко — не номер

        // Извлекаем последние 10 цифр (локальный номер)
        String localNumber;
        if (digits.length() >= 10) {
            localNumber = digits.substring(digits.length() - 10);
        } else {
            return raw; // недостаточно цифр для нормализации
        }

        // Форматируем с целевым кодом страны +1
        String area = localNumber.substring(0, 3);
        String part1 = localNumber.substring(3, 6);
        String part2 = localNumber.substring(6, 8);
        String part3 = localNumber.substring(8, 10);

        return String.format("+%s (%s) %s-%s-%s", TARGET_COUNTRY, area, part1, part2, part3);
    }
}