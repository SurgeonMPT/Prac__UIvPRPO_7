package phone;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Main <input> <output>");
            System.exit(1);
        }

        try {
            PhoneProcessor processor = new PhoneProcessor();
            processor.processFile(Path.of(args[0]), Path.of(args[1]));
            System.out.println("Done. Output: " + Path.of(args[1]).toAbsolutePath());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            System.exit(2);
        }
    }
}