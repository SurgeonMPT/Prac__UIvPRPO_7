package phone;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class PhoneProcessor {

    public String processFile(Path input, Path output) throws IOException {
        String text = Files.readString(input, StandardCharsets.UTF_8);
        String result = new PhoneNormalizer().normalizePhoneNumbers(text);
        Files.writeString(output, result, StandardCharsets.UTF_8);
        return result;
    }
}