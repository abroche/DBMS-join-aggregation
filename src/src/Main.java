import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the command: ");
        String input = scanner.nextLine();
        String cmd = "SELECT A.Col1, A.Col2, B.Col1, B.Col2 FROM A, B WHERE A.RandomV = B.RandomV";

        if (input.equals(cmd)) {
            Join join = new Join();
            join.buildHashtableA();
            System.out.println(join.hashtableA);
        } else {
            System.out.println("Not the expected command.");
        }

        scanner.close();


    }
}