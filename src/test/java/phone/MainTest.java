package phone;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @TempDir
    Path tempDir;

    private PhoneProcessor processor;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        processor = new PhoneProcessor();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void testProcessFileWithValidInput() throws Exception {
        // Создаем входной файл
        Path input = tempDir.resolve("input.txt");
        Path output = tempDir.resolve("output.txt");
        Files.writeString(input, "Test 8-999-123-45-67");

        // Вызываем processor вместо main
        String result = processor.processFile(input, output);

        // Проверяем результат
        assertTrue(result.contains("+1 (999) 123-45-67"));
        assertTrue(Files.exists(output));
    }

    @Test
    void testProcessFileWithInvalidInput() {
        Path nonExistentFile = tempDir.resolve("nonexistent.txt");
        Path output = tempDir.resolve("output.txt");

        // Проверяем, что выбрасывается IOException
        assertThrows(IOException.class, () -> {
            processor.processFile(nonExistentFile, output);
        });
    }

    @Test
    void testMainClassExists() {
        // Простой тест для проверки, что класс Main существует
        assertNotNull(Main.class);
    }
}