import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class pepParser {

    public void parse(File pepFile){
        assembler assembler = new assembler();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(pepFile))){
            while ((line = reader.readLine()) != null){
                String result = assembler.assemble(line);
                System.out.println(result);
            }
        } catch (IOException e){
            System.out.println("Error: Unable to read pep file");
        }

    }
}
