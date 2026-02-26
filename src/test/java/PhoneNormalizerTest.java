import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import phone.PhoneNormalizer;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для нормализатора телефонных номеров")
class PhoneNormalizerTest {

    private PhoneNormalizer normalizer;

    @BeforeEach
    void setUp() {
        normalizer = new PhoneNormalizer();
    }

    @Test
    @DisplayName("Тест нормализации российского номера")
    void testNormalizeRussianPhoneNumber() {
        String input = "Позвоните по номеру 8 (999) 123-45-67";
        String expected = "Позвоните по номеру +1 (999) 123-45-67";
        String result = normalizer.normalizePhoneNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Тест нормализации номера с международным форматом")
    void testNormalizeInternationalPhoneNumber() {
        String input = "Мой номер +7 495 123-45-67";
        String expected = "Мой номер +1 (495) 123-45-67";
        String result = normalizer.normalizePhoneNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Тест нормализации нескольких номеров в тексте")
    void testMultiplePhoneNumbers() {
        String input = "Первый: 8-912-345-67-89, Второй: +7 (495) 987-65-43";
        String expected = "Первый: +1 (912) 345-67-89, Второй: +1 (495) 987-65-43";
        String result = normalizer.normalizePhoneNumbers(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Тест с текстом без номеров")
    void testTextWithoutPhoneNumbers() {
        String input = "Просто текст без номеров";
        String result = normalizer.normalizePhoneNumbers(input);
        assertEquals(input, result);
    }

    @Test
    @DisplayName("Тест с пустой строкой")
    void testEmptyString() {
        String input = "";
        String result = normalizer.normalizePhoneNumbers(input);
        assertEquals("", result);
    }

    @Test
    @DisplayName("Тест с номерами в разных форматах")
    void testVariousFormats() {
        String input = "Номера: 89991234567, 8-999-123-45-67, +7(999)1234567";
        String result = normalizer.normalizePhoneNumbers(input);

        // Проверяем, что все номера заменены
        assertTrue(result.contains("+1 (999) 123-45-67"));

        // Проверяем, что исходные форматы исчезли
        assertFalse(result.contains("89991234567"));
        assertFalse(result.contains("8-999-123-45-67"));
        assertFalse(result.contains("+7(999)1234567"));
    }

    @Test
    @DisplayName("Тест с номерами без разделителей")
    void testNumbersWithoutSeparators() {
        String input = "Номера: 89991234567 и 74951234567";
        String expected = "Номера: +1 (999) 123-45-67 и +1 (495) 123-45-67";
        String result = normalizer.normalizePhoneNumbers(input);
        assertEquals(expected, result);
    }
}