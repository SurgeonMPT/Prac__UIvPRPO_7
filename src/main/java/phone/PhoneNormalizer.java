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
        // return
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

        // Определяем код страны
        String countryCode = TARGET_COUNTRY;
        String rest = digits;
        if (digits.length() > 10) {
            countryCode = digits.substring(0, digits.length() - 10);
            rest = digits.substring(digits.length() - 10);
        } else if (digits.length() == 11 && digits.startsWith("8")) {
            // Российский формат 8XXX... -> код 7
            countryCode = "7";
            rest = digits.substring(1);
        } else if (digits.length() == 10) {
            // 10 цифр — считаем, что код страны отсутствует
            rest = digits;
        }

        // Форматируем оставшиеся 10 цифр: XXX XXX XX XX
        if (rest.length() >= 10) {
            String area = rest.substring(0, 3);
            String part1 = rest.substring(3, 6);
            String part2 = rest.substring(6, 8);
            String part3 = rest.substring(8, 10);
            return String.format("+%s (%s) %s-%s-%s", countryCode, area, part1, part2, part3);
        }
        return raw; // на всякий случай возвращаем исходное
    }
}