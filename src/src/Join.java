import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

public class Join {
    private static final int recordSize = 40;



    static int buckets = 50;
    Hashtable<Integer, LinkedList<String>> hashtableA = new Hashtable<>();
    // {hashValue: [record1, record2,...] (bucket)

    ArrayList<Integer> recordsA = new ArrayList<>(9900);


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

    public String getRecordFromDisk(int recordNum, String filename){
        String record = null;
        try {
            byte[] records = Files.readAllBytes(Path.of(filename));
            int start = (recordNum-1) * recordSize;
            int end = start + recordSize;
            record = new String(Arrays.copyOfRange(records, start, end));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("Record " + recordNum + " in " + filename );
        return record;
    }


    /**
     * reads all the files in directory and each record in each file
     */
    public void buildArrayA() {
        //for each file in the directory
        for (int i = 1; i <= 99; i++) {
            String filePath = getFilePath(i, "A");
            String fileContent = readFile(filePath);
            //for each record
            for (int k = 1; k <= 100; k++) {
                String record = getRecord(k, fileContent);
                int randomV = getRandomV(record);
                recordsA.add(randomV);
            }
        }
    }


    public int nestedLoopJoin(){
        buildArrayA();

        int count = 0;

                for(int fileB = 1; fileB < 100; fileB++){
                    String filePathB = getFilePath(fileB, "B");
                    for(int rB = 1; rB < 101; rB++){
                        String recordB = getRecordFromDisk(rB, filePathB);
                        int randomVB = getRandomV(recordB);
                        for(Integer randomVA: recordsA){
                        if(randomVA > randomVB){
                            count++;
                        }
                    }
                }
        }
        return count;
    }

    public void aggregation(String aggregationFunction, String dataset){
        Hashtable<String, Integer> aggregationTable = new Hashtable<>();

        for(int file = 1; file < 100; file++){
            String filePath = getFilePath(file, dataset);
            String fileContent = readFile(filePath);
            for(int r = 1; r < 101; r++){
                String record = getRecord(r,fileContent);
                int randomV = getRandomV(record);
                String[] columns = record.split(",", -1);
                String col2 = columns[1];

                if(aggregationTable.containsKey(col2)){
                    int sum = aggregationTable.get(col2) + randomV;
                    aggregationTable.put(col2, sum);
                }
                else{
                    aggregationTable.put(col2, randomV);
                }
            }
        }
        
        
        if(aggregationFunction.equalsIgnoreCase("SUM(RandomV)")){
            for(String name: aggregationTable.keySet()){
                int sum = aggregationTable.get(name);
                System.out.println("Column 2: " + name + " has a RandomV sum of " + sum);
            }
        }
        else if (aggregationFunction.equalsIgnoreCase("AVG(RandomV)")){
            for(String name: aggregationTable.keySet()){
                int avg = aggregationTable.get(name) / 99;
                System.out.println("Column 2: " + name + " has average RandomV of " + avg);
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
        int start = ((recordNum - 1)) * recordSize; //start location
//        System.out.println("Record " + recordNum + "is " + fileContent.substring(start, start + 40));
        return fileContent.substring(start, start + 40);
    }

    public String getFilePath(int fileNum, String dataset){
        return "Project3Dataset" + File.separator + "Project3Dataset-" + dataset + File.separator + dataset + fileNum + ".txt";
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
