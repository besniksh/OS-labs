package mk.ukim.finki.os.examples.ExamIO.arraydb;

import java.io.*;

public class mySolution {
    public static final String CSV_FILE = "data/arraydb_mySolution/CsvToOrderedBinaryDatabase.csv";
    public static final String BIN_FILE = "data/arraydb_mySolution/CsvToOrderedBinaryDatabase.osdb";
    public static final int ELEMENT_SIZE = 8 + 8 + 1 + 4;

    public static void main(String[] main) throws IOException {
        parseCsv(CSV_FILE,BIN_FILE);
    }

    public static void parseCsv(String csvFile, String binFile) throws IOException {
        try(
                BufferedReader csvReader = openCsv(csvFile);
                RandomAccessFile randomAccessFile = createOrOpenBinFile(binFile);
                ){
            String line;
            while ((line = csvReader.readLine()) != null){
                saveLine(line,randomAccessFile);
            }
        }
        printDatabase(binFile);
    }

    private static void printDatabase(String binFile) throws IOException {
        try(
                RandomAccessFile dbFile = new RandomAccessFile(binFile,"rw");
        ){
            Long numberOfElements = dbFile.length() / ELEMENT_SIZE;
            for(int index = 0; index < numberOfElements; index++){
                printElement(dbFile,index);
            }
        }
    }

    private static void printElement(RandomAccessFile dbFile, int index) throws IOException {
        System.out.println("Printing line number :" + index);
        dbFile.seek(index * ELEMENT_SIZE);
        Long rowNumber = dbFile.readLong();
        String value = dbFile.readUTF();
        String status = dbFile.readUTF();

        System.out.println(rowNumber + ", " +value+ ", " + status);
    }

    private static void saveLine(String line, RandomAccessFile binFile) throws IOException {
        String[] elements = validateAndGetElements(line);

        String lineNumber = elements[0].trim();
        String value = elements[1].trim();
        String status = elements[2].trim();

        validateElements(lineNumber, value,status);
        Long lineNr = Long.parseLong(lineNumber);
        binFile.writeLong(lineNr);
        binFile.writeUTF(value);
        binFile.writeUTF(status);
    }

    private static void validateElements(String lineNr, String value, String status) {
        if(lineNr.length() == 0){
            throw  new IllegalArgumentException("Vrednosta na Line Number e invalidna") ;
        }
        if(value.length() != 8){
            throw new IllegalArgumentException("Vo linija " + lineNr +" Vrednosta ne e so 8 karakteri!");
        }
        if(status.length() != 1){
            throw  new IllegalArgumentException("Vo linija " + lineNr + "Statusot ne e so 1 karakter!");
        }
    }

    private static String[] validateAndGetElements(String line) {
        String[] elements = line.split(",");
        if(elements.length != 3){
            throw new IllegalStateException("Vo edna linija ne se 3 elementi!");
        }
        return elements;
    }

    private static RandomAccessFile createOrOpenBinFile(String binFile) throws FileNotFoundException {
        return new RandomAccessFile(binFile,"rw");
    }

    private static BufferedReader openCsv(String csv) throws IOException {
        File csvFile = new File(csv);
        if(!csvFile.exists()){
            throw new IllegalStateException(csv + " Ne postoi ");
        }else{
            return new BufferedReader(new FileReader(csvFile));
        }

    }
}
