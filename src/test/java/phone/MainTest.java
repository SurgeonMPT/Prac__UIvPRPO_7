package phone;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @TempDir
    Path tempDir;

    @Test
    void testMainWithValidFiles() throws Exception {
        // Создаем входной файл
        Path input = tempDir.resolve("input.txt");
        Path output = tempDir.resolve("output.txt");
        Files.writeString(input, "Test 8-999-123-45-67");

        // Запускаем main
        Main.main(new String[]{input.toString(), output.toString()});

        // Проверяем результат
        String result = Files.readString(output);
        assertTrue(result.contains("+1 (999) 123-45-67"));
    }

    @Test
    void testMainWithMissingArguments() {
        // Перенаправляем System.err для проверки
        // Запускаем main без аргументов
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Main.main(new String[]{});
        });
    }
}