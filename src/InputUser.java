import java.util.Scanner;

public class InputUser {
    private InputUser(){

    }
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String nextLine(){
        return SCANNER.nextLine().toLowerCase();
    }

}
