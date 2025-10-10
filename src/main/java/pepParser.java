import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class pepParser {

    public void parse(File pepFile){
        assembler assembler = new assembler();

        //initialize all of our variables
        //NULL is not used, as it will always print at the beginning of outputted machine code
        String result = "";
        String line;

        //Read pep file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(pepFile))){
            while ((line = reader.readLine()) != null){
                //send line to the assembler to generate machine code
                result = (result + " " + assembler.assemble(line));
            }
        } catch (IOException e){
            //If there is any error reading file, tell the user what's fucked up
            System.out.println("Error: Unable to read pep file");
            System.out.println("PLEASE ENSURE FILE IS IN SAME DIRECTORY AS PROGRAM");
        }

        //print out our machine code
        System.out.println(result);

    }
}
