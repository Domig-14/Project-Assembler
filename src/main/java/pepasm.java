import java.io.File;

public class pepasm {

    public static void main(String[] args){

        if (args.length > 0) {
            //access the file via arguments
            String filePath = args[0];
            File pepFile = new File(filePath);

            //send the pep file to the parser
            pepParser parser = new pepParser();
            parser.parse(pepFile);

        }
        else {
            System.out.println("Error: No file detected");
        }
    }
}
