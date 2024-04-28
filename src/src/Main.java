import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the command: ");
        String input = scanner.nextLine();
        String cmd = "SELECT A.Col1, A.Col2, B.Col1, B.Col2 FROM A, B WHERE A.RandomV = B.RandomV";

        if (input.equals(cmd)) {
            System.out.println("User input matches the predefined SQL command.");
        } else {
            System.out.println("User input does not match the predefined SQL command.");
        }

        scanner.close();


    }
}