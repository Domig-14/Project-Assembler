import java.io.File;

public class pepasm {

    public static void main(String[] args){

        if (args.length > 0) {
            //access the file via arguments
            String filePath = args[0];
            File pepFile = new File(filePath);

            //send the pep file to the parser
            pepParser parser = new pepParser();
            String output = parser.parse(pepFile);

            System.out.println(output);
        }
        else {
            //if there are no arguments, let the user know that something is fucked up
            System.out.println("Error: No file detected.");
        }
    }
}
