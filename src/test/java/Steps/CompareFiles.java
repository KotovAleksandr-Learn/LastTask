package Steps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class CompareFiles {

    public static boolean compareFiles(String firstFilePath, String secondFilePath) throws IOException {
        byte[] arrayBytesFirstFile = Files.readAllBytes(Paths.get(firstFilePath));
        byte[] arrayBytesSecondFile = Files.readAllBytes(Paths.get(secondFilePath));
        return Arrays.equals(arrayBytesFirstFile,arrayBytesSecondFile);
    }
}
