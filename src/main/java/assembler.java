import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class assembler {
    public String assemble(String line, int lineNum){
        String result = "";//Initialize the result string
        line = line.trim();

        //Split line into chunks for Instruction, Hex Number, and Addressing mode
        String[] chunks = line.split("\\s+") ;
        int items = chunks.length;

        //Make a new array, and assign each element of that array to the new one
        //Yes, this is theoretically redundant... but if it wasn't here, java would throw a temper tantrum
        String[] chunk = new String[items];
        int i = 0;
        for (String element: chunks){
            chunk[i] = element;
            i++;
        }

        chunk = validate(chunk);
        //System.out.println(Arrays.toString(chunk));

        //Assign our chunks into variables
        String instruction = chunk[0];
        String hexValue = chunk[1];
        String addressingType = null;

        //make sure that it will only include the addressing type, if it actually exists
        //some instructions do not need an addressing type
        if(chunk.length >= 3){
            addressingType = chunk[2];
            if (addressingType == null){
                //account for if there is a space between the hex value, and the addressing type
                String[] spacing = hexValue.split(",");
                chunk[1] = spacing[0];
                if (spacing.length >= 2){
                    chunk[2] = spacing[1];
                }
            }
        }

        //get the hex value for our instruction
        String instValue = getInstValue(instruction, addressingType, lineNum);

        //make sure our instruction's value is not NULL
        if (instValue != null){
            //add our instruction value into our result
            result = instValue;
        }

        //process the hex number (e.x. 0x0000) into two bytes, and format them as it would occur in the Object Code
        String processHex = hexProcess(instruction, hexValue);
        //Some instructions do not need a hex number with them, do not add those to our object code
        if (processHex != null){
            //Instructions like BRNE or BREQ need a label instead of a hex number, don't add labels to the Object Code
            if (processHex != "isLabel"){
                result = result + processHex; //Add the processed hex number to our result
            }
        }

        return result;
    }

    private String hexProcess(String instruction, String hexValue) {
        String processedHex = null; //initialize our result

        //ensure that we actually have something as our hex value
        if (hexValue != null){
            String firstByte; //initialize the first byte of our hex value
            String secondByte; //initialize the second byte of our hex value
            switch (instruction) {
                //Ensure that it is an instruction that actually needs a hex value
                //If it is not an instruction that needs a hex value, mark it as a label for a custom instruction

                //Instructions are seen as "0x0000," in the program, we only need the two bits
                //Separate the two bits, and throw away the junk that does not show up in the object code
                case "STBA":
                    hexValue = hexValue.substring(2,hexValue.length() - 1);
                    if (hexValue.length() >= 4){
                        firstByte = hexValue.substring(0,2);
                        secondByte = hexValue.substring(2,4);
                    } else {
                        secondByte = hexValue.substring(hexValue.length() - 2);
                        firstByte = "00";
                    }
                    processedHex = (firstByte + " " + secondByte + " ");
                    break;
                case "LDBA":
                    hexValue = hexValue.substring(2,hexValue.length() - 1);
                    if (hexValue.length() >= 4){
                        firstByte = hexValue.substring(0,2);
                        secondByte = hexValue.substring(2,4);
                    } else {
                        secondByte = hexValue.substring(hexValue.length() - 2);
                        firstByte = "00";
                    }
                    processedHex = (firstByte + " " + secondByte + " ");
                    break;
                case "STWA":
                    hexValue = hexValue.substring(2,hexValue.length() - 1);
                    if (hexValue.length() >= 4){
                        firstByte = hexValue.substring(0,2);
                        secondByte = hexValue.substring(2,4);
                    } else {
                        secondByte = hexValue.substring(hexValue.length() - 2);
                        firstByte = "00";
                    }
                    processedHex = (firstByte + " " + secondByte + " ");
                    break;
                case "LDWA":
                    hexValue = hexValue.substring(2,hexValue.length() - 1);
                    if (hexValue.length() >= 4){
                        firstByte = hexValue.substring(0,2);
                        secondByte = hexValue.substring(2,4);
                    } else {
                        secondByte = hexValue.substring(hexValue.length() - 2);
                        firstByte = "00";
                    }
                    processedHex = (firstByte + " " + secondByte + " ");
                    break;
                case "ANDA":
                    hexValue = hexValue.substring(2,hexValue.length() - 1);
                    if (hexValue.length() >= 4){
                        firstByte = hexValue.substring(0,2);
                        secondByte = hexValue.substring(2,4);
                    } else {
                        secondByte = hexValue.substring(hexValue.length() - 2);
                        firstByte = "00";
                    }
                    processedHex = (firstByte + " " + secondByte + " ");
                    break;
                case "CPBA":
                    hexValue = hexValue.substring(2,hexValue.length() - 1);
                    if (hexValue.length() >= 4){
                        firstByte = hexValue.substring(0,2);
                        secondByte = hexValue.substring(2,4);
                    } else {
                        secondByte = hexValue.substring(hexValue.length() - 2);
                        firstByte = "00";
                    }
                    processedHex = (firstByte + " " + secondByte + " ");
                    break;
                case "ADDA":
                    hexValue = hexValue.substring(2,hexValue.length() - 1);
                    if (hexValue.length() >= 4){
                        firstByte = hexValue.substring(0,2);
                        secondByte = hexValue.substring(2,4);
                    } else {
                        secondByte = hexValue.substring(hexValue.length() - 2);
                        firstByte = "00";
                    }
                    processedHex = (firstByte + " " + secondByte + " ");
                    break;
                default:
                    processedHex = "isLabel";
                    break;
            }
        }
        //If we do not have something as the hex value, return nothing
        return processedHex;
    }

    public String[] validate(String[] line){
        int items = line.length; //ensure that we have a measurement for how many words are in our line of code

        String[] item = new String[items + 1]; //make another array for our words
        //fill that array as we see fit
        int i = 0;
        for (String element: line){
            item[i] = element;
            i++;
        }

        i = 0; //We will reuse the counter, so reset it

        //Sort our words so that the first element is actually a valid instruction, and not a custom instruction
        for (String element: item){
            item[i] = element;
            if (element != null){
                switch (element) {
                    //Ensure that the first 3 elements of the array are an instruction, a hex value, and an address type
                    //EXAMPLE: [LDBA,0x0000,,i]

                    //We also must ensure we don't throw away any custom instructions, if the array has more than 3 elements, move the first one to the back
                    case "STBA":
                        if (item.length >= 4){
                            item[0] = item[3];
                        }
                        item[0] = element;
                        item[1] = item[i+1];
                        item[2] = item[i+2];
                        break;
                    case "LDBA":
                        if (item.length >= 4){
                            item[0] = item[3];
                        }
                        item[0] = element;
                        item[1] = item[i+1];
                        item[2] = item[i+2];
                        break;
                    case "STWA":
                        if (item.length >= 4){
                            item[0] = item[3];
                        }
                        item[0] = element;
                        item[1] = item[i+1];
                        item[2] = item[i+2];
                        break;
                    case "LDWA":
                        if (item.length >= 4){
                            item[0] = item[3];
                        }
                        item[0] = element;
                        item[1] = item[i+1];
                        item[2] = item[i+2];
                        break;
                    case "ANDA":
                        if (item.length >= 4){
                            item[0] = item[3];
                        }
                        item[0] = element;
                        item[1] = item[i+1];
                        item[2] = item[i+2];
                        break;
                    case "ASLA":
                        //ASLA is a special case, as it only needs to have itself on the line
                        item[0] = element;
                        break;
                    case "ASRA":
                        //ASRA is a special case, as it only needs to have itself on the line
                        item[0] = element;
                        break;
                    case "CPBA":
                        if (item.length >= 4){
                            item[0] = item[3];
                        }
                        item[0] = element;
                        item[1] = item[i+1];
                        item[2] = item[i+2];
                        break;
                    case "BRNE":
                        if (item.length >= 4){
                            item[0] = item[3];
                        }
                        item[0] = element;
                        item[1] = item[i+1];
                        item[2] = item[i+2];
                        break;
                    case "BREQ":
                        if (item.length >= 4){
                            item[0] = item[3];
                        }
                        item[0] = element;
                        item[1] = item[i+1];
                        item[2] = item[i+2];
                        break;
                    default:
                        break;
                }
            }
            i++;
        }
        return item;
    }

    public String getInstValue(String instruction, String addressingType, int lineNum){
        instruction = instruction.trim(); //make sure that there are no spaces in front of our instruction

        if(addressingType != null){ //ensure that we have an addressing type
            addressingType = addressingType.trim(); //if we do, trim it as well
        }
        String instValue = null; //initialize the value of our instruction

        if (instruction != null) {
            //Add the appropriate instruction hex value to the result
            //Make sure to account for the addressing type, if it applies to the instruction
            switch (instruction) {
                case "ADDA":
                    instValue = "60 ";
                    break;
                case "STBA":
                    instValue = "F1 ";
                    break;
                case "LDBA":
                    if (Objects.equals(addressingType, "d")) {
                        instValue = "D1 ";
                    } else if (Objects.equals(addressingType, "i")) {
                        instValue = "D0 ";
                    }
                    break;
                case "STWA":
                    instValue = "E1 ";
                    break;
                case "LDWA":
                    if (Objects.equals(addressingType, "d")) {
                        instValue = "C1 ";
                    } else if (Objects.equals(addressingType, "i")) {
                        instValue = "C0 ";
                    }
                    break;
                case "ANDA":
                    if (Objects.equals(addressingType, "d")) {
                        instValue = "81 ";
                    } else if (Objects.equals(addressingType, "i")) {
                        instValue = "80 ";
                    }
                    break;
                case "ASLA":
                    instValue = "0A ";
                    break;
                case "ASRA":
                    instValue = "0C ";
                    break;
                case "CPBA":
                    if (Objects.equals(addressingType, "d")) {
                        instValue = "B1 ";
                    } else if (Objects.equals(addressingType, "i")) {
                        instValue = "B0 ";
                    }
                    break;
                case "BRNE":
                    instValue = ("1A 00 0" + lineNum + " ");
                    break;
                case "BREQ":
                    instValue = ("18 00 0" + lineNum + " ");
                    break;
                case "STOP":
                    instValue = "00 ";
                    break;
            }
        }
        return instValue;
    }
}
