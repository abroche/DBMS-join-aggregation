import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.LinkedList;

public class Join {
    private static final int recordSize = 40;



    static int buckets = 50;
    Hashtable<Integer, LinkedList<String>> hashtableA = new Hashtable<>();
    // {hashValue: [record1, record2,...] (bucket)
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



    public void buildHashtableA(){
        // for each file in datasetA
        for (int file = 1; file < 100; file++) {
            String filePath = getFilePath(file, "A");
            String fileContent = readFile(filePath);
            //for each record
            for (int r = 1; r < 101; r++) {
                String record = getRecord(r, fileContent);
                int randomV = getRandomV(record);
                int directory = hashFunction(randomV); // some value between 0 and 49

                if(hashtableA.containsKey(directory)){
                    hashtableA.get(directory).add(record);
                }
                else{
                    LinkedList<String> records = new LinkedList<>();
                    records.add(record);
                    hashtableA.put(directory, records);
                }
            }
        }
    }


    public void joinTables(){
        // for each file in datasetB
        for (int file = 1; file < 100; file++) {
            String filePath  = getFilePath(file, "B");
            String fileContent = readFile(filePath);
            //for each record in the file of datasetB
            for (int r = 1; r < 101; r++) {
                String recordB = getRecord(r, fileContent);
                int randomVB = getRandomV(recordB);
                int directory = hashFunction(randomVB);

                for (String recordA :
                        hashtableA.get(directory)) {
                    int randomVA = getRandomV(recordA);
                    if(randomVA == randomVB){
                        String[] columnsA = recordA.split(",",-1);
                        String[] columnsB = recordB.split(",",-1);
                        System.out.println(columnsA[0] + " | " +  columnsA[1] + " | " + columnsB[0] + " | " + columnsB[1]);
                    }
                }
            }
        }
    }


    public static int hashFunction(int randomV){
        return randomV % buckets;
    }


    /**
     * Gets the specified record
     * @param recordNum is the record number
     * @param fileContent content of the file
     * @return the specified record
     */
    public String getRecord(int recordNum, String fileContent) {
        int start = ((recordNum - 1) % 100) * recordSize; //start location
        return fileContent.substring(start, start + 40);
    }

    public String getFilePath(int fileNum, String dataset){
        return "Project3Dataset" + File.separator + "Project3Dataset-" + dataset + File.separator + "A" + fileNum + ".txt";
    }

    public int getRandomV(String record){
        return Integer.parseInt(record.substring(recordSize - 7, recordSize - 3));
    }


    /**
     * Reads the specified file
     *
     * @param filename is the path of the file to read
     * @return the contents of the file
     */
    public static String readFile(String filename) {
        String strRecords = null;
        try {
            byte[] records = Files.readAllBytes(Path.of(filename));
            strRecords = new String(records);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return strRecords;
    }
}
