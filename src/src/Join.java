import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Join {

    int capacity = 50;
    Hashtable<String, String> hashTableA= new Hashtable<>(capacity);

    /*
    Section 2

    - build a hash table on dataset-A with 50 buckets
    - buckets store the entire record content
    - hashing of each record to know the bucket it is in should be based on the join column
        - the sql command gives the which column is the join column
    - for loop to read dataset-B file-by-file and record-by-record
        - apply same hash function on join column to know the bucket to check from dataset-A
    - if A.RandomV = B.RandomV then output the record with the needed columns
      from the sql command

    - print out the execution time taken to execute the command
    - print out the records that qualify
     */






    /**
     * Reads the specified file
     *
     * @param fileNumber the blockId that is meant to be read
     * @return the contents of the file
     */
    public String readFile(int fileNumber) {
        String filename = "Project1/F" + fileNumber + ".txt";
        String blockContent = null;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            blockContent = bufferedReader.readLine();

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blockContent;
    }

}
