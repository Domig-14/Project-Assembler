import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class pepParser {

    public static String parse(File pepFile){
        assembler assembler = new assembler();

        //initialize all of our variables
        //NULL is not used, as it will always print at the beginning of outputted machine code
        String result = "";
        String line;

        //Read pep file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(pepFile))){
            int i = 0;
            int lineNum = 0;
            while ((line = reader.readLine()) != null){
                String firstInst = getFirstInstruction(line); //get first instruction
                boolean isCustom = validate(firstInst); //see if that instruction is a custom instruction
                if(isCustom){ //if the first instruction is a custom instruction, save the line of that instruction for later
                    lineNum = i * 3;
                }

                //send line, and the line of the custom instruction to the assembler to generate machine code
                result = (result + assembler.assemble(line, lineNum));

                i++;
            }
        } catch (IOException e){
            //If there is any error reading file, tell the user what's fucked up
            System.out.println("Error: Unable to read pep file");
            System.out.println("PLEASE ENSURE FILE IS IN SAME DIRECTORY AS PROGRAM");
        }

        return result;

    }

    private static boolean validate(String firstInst) {
        //compare with every supported instruction, if it is not a supported instruction, it is seen as a custom instruction
        return switch(firstInst){
            case "LDBA", "LDWA", "STBA", "STWA", "ANDA", "ASLA", "ASRA", "STOP", ".END", "CPBA", "BRNE", "BREQ", "ADDA", "SUBA" -> false;
            default -> true;
        };
    }

    private static String getFirstInstruction(String line) {
        String[] bits = line.trim().split("\\s+"); //cut the line up into individual words
        String[] chunks = new String[bits.length];
        //save the words into a new array
        //don't ask me why I did this, whenever I used the first array, it would throw a temper tantrum
        int i = 0;
        for (String bit: bits){
            chunks[i] = bit;
            i++;
        }

        return bits[0]; //return the first word
    }

}
