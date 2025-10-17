import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class projectTest {

    @Test
    public void file1Test(){
        File testFile = new File("program1.pep");

        String output = pepParser.parse(testFile);

        Assertions.assertEquals("D0 00 48 F1 FC 16 D0 00 69 F1 FC 16 00 ", output);
    }
    @Test
    public void file2Test(){
        File testFile = new File("program2.pep");

        String output = pepParser.parse(testFile);

        Assertions.assertEquals("C0 FC 16 E1 70 00 C0 00 41 0A 0C F1 FC 16 00 ", output);
    }
    @Test
    public void file3Test(){
        File testFile = new File("program3.pep");

        String output = pepParser.parse(testFile);

        Assertions.assertEquals("C0 FF FF 60 00 01 60 00 4D F1 FC 16 D0 00 6F F1 FC 16 F1 FC 16 D0 00 62 F1 FC 16 00 ", output);
    }
    @Test
    public void file4Test(){
        File testFile = new File("program4.pep");

        String output = pepParser.parse(testFile);

        Assertions.assertEquals("D0 00 41 F1 FC 16 60 00 01 B0 00 5B 1A 00 03 00 ", output);
    }
}
