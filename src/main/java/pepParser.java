import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class pepParser {

    public void parse(File pepFile){
        assembler assembler = new assembler();
        String result = "";
        String line;
        //Read pep file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(pepFile))){
            while ((line = reader.readLine()) != null){
                result = (result + " " + assembler.assemble(line));
            }
        } catch (IOException e){
            System.out.println("Error: Unable to read pep file");
            System.out.println("PLEASE ENSURE FILE IS IN SAME DIRECTORY AS PROGRAM");
        }

        System.out.println(result);

    }
}
