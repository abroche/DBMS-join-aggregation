import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the command: ");
        String input = scanner.nextLine();

        String regexPattern = "^SELECT Col2, (SUM\\(RandomV\\)|AVG\\(RandomV\\)) FROM ([AB]) GROUP BY Col2$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(input);


        Join join = new Join();
        join.buildHashtableA();
        if (input.equalsIgnoreCase("SELECT A.Col1, A.Col2, B.Col1, B.Col2 FROM A, B WHERE A.RandomV = B.RandomV")) {

            long start = System.currentTimeMillis();
            join.joinTables();
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println("Time Time taken to execute query: " + time + " ms");

        } else if(input.equalsIgnoreCase("SELECT count(*) FROM A, B WHERE A.RandomV > B.RandomV")) {
            long start = System.currentTimeMillis();
            System.out.println("Count = " + join.nestedLoopJoin());
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println("Time taken to execute query: " + time + " ms" );

        } else if(matcher.matches()){
            String dataset = matcher.group(2); // Group 2 corresponds to (A|B)

            String aggregationFunction = matcher.group(1); // Group 1 corresponds to (SUM(RandomV)|AVG(RandomV))

            long start = System.currentTimeMillis();
            join.aggregation(aggregationFunction, dataset);
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println("Time taken to execute query: " + time + " ms" );
        }

        scanner.close();


    }
}