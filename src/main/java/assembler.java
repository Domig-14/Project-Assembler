import java.util.Objects;

public class assembler {

    public String assemble(String line){
        String result = ""; //Initialize the result string
        System.out.println(line);

        //Split line into chunks for Instruction, Hex Number, and Addressing mode
        String[] chunks = line.split("\\s+") ;

        String instruction = chunks[0];
        String hexValue = chunks[1];
        String addressingType = chunks[2];

        if (instruction != null) {
            //Add the appropriate instruction hex value to the result
            //Make sure to account for the addressing type, if it applies to the instruction
            String instValue = "";
            switch (instruction) {
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
                    instValue = "1A ";
                    break;
                case "BREQ":
                    instValue = "18 ";
                    break;
                case "STOP":
                    instValue = "00 ";
                    break;
            }

            result = result + instValue; //update the result with the instruction's hex value
        }
        if (hexValue != null){
            //cut the hex value into both of it's bytes
            String firstByte = hexValue.substring(2,3);
            String secondByte = hexValue.substring(4,5);

            result = result + firstByte + " " + secondByte + " "; //Update the result with those bytes
        }

        return result;
    }
}
