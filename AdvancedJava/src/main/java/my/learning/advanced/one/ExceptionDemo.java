package my.learning.advanced.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by tdongsi on 3/25/18.
 */
public class ExceptionDemo {

    public static void main(String[] args) {

        Path dir = Paths.get("src", "main", "java", "my", "learning", "advanced", "one");
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(dir.resolve("ExceptionDemo.java"));
            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
