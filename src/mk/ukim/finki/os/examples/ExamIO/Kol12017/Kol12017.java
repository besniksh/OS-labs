package mk.ukim.finki.os.examples.ExamIO.Kol12017;

import java.io.*;

public class Kol12017 {
    public static void main(String[] main) throws IOException {
        work("C:\\finki", "data\\dirto");
    }
    public static int nrFiles = 0;

    private static void work(String dirFrom, String dirTo) throws IOException {
        File fromDir = new File(dirFrom);
        File toDir = new File(dirTo);

        if(!fromDir.exists()){
            System.out.println("dirFrom ne postoi");
            return;
        }

        if(!toDir.exists()){
            toDir.mkdirs();
        }
        DeleteData(toDir);
        RecursiveDir(fromDir,toDir);
        System.out.println("Broj na fajllovi: " + nrFiles);
        printDatabase("data\\dirto\\details.bin");

    }

    private static void printDatabase(String s) throws IOException {
        try (
                RandomAccessFile randomAccessFile = new RandomAccessFile(s,"r");
        ){

            for(int i= 0;i < nrFiles ; i++){
                String xx = randomAccessFile.readUTF();
                String filename = randomAccessFile.readUTF();
                String yyy = randomAccessFile.readUTF();
                String fullpath = randomAccessFile.readUTF();
                String rwx = randomAccessFile.readUTF();
                System.out.println(xx+filename+yyy+fullpath+rwx);
            }


        }
    }

    private static void DeleteData(File dirTo) {
        File[] allFiles = dirTo.listFiles();

        for(File file : allFiles){
            if(file.isFile()){
                file.delete();
            }
            else if(file.isDirectory()){
                DeleteData(file);
                file.delete();
            }
        }
    }

    private static void RecursiveDir(File fromDir, File toDir) throws IOException {
        File[] allFiles = fromDir.listFiles();
        File details = new File(toDir, "details.bin");
        details.delete();
        details.createNewFile();

        for(File file : allFiles){
            if(file.isFile()){
                nrFiles++;
                copyThisFile(file,toDir);
                processFile(file,details);
            }
            else if(file.isDirectory()){
                RecursiveDir(file, toDir);
            }
        }
    }

    private static void processFile(File file, File details) throws IOException {
        String perm = "";

        if(file.canRead() && file.canWrite() && file.canExecute()){
            perm = "rwx";
        }else if(file.canRead() && file.canWrite() && !file.canExecute()){
            perm = "rw-";
        }else if(file.canRead() && !file.canWrite() && file.canExecute()){
            perm = "r-x";
        }else if(file.canRead() && !file.canWrite() && !file.canExecute()){
            perm = "r--";
        }else if(!file.canRead() && file.canWrite() && file.canExecute()){
            perm = "-wx";
        }else if(!file.canRead() && !file.canWrite() && file.canExecute()){
            perm = "--x";
        }else if(!file.canRead() && file.canWrite() && !file.canExecute()){
            perm = "-w-";
        }
        else if(!file.canRead() && file.canWrite() && file.canExecute()){
            perm = "---";
        }

        String fullpath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length()-file.getName().length());
        String fileName = file.getName();
        String xx = String.valueOf(fileName.length());
        String yyy = String.valueOf(fullpath.length());
        String rwx = perm;

        xx = CheckForTwoBytes(xx);
        yyy = CheckForThreeBytes(yyy);


        writeToDatabase(xx,fileName,yyy,fullpath,rwx,details);
    }



    private static void writeToDatabase(String xx, String fileName, String yyy, String fullpath, String rwx,File details) throws IOException {

        try(
//                FileOutputStream fos = new FileOutputStream(details,true);
                RandomAccessFile randomAccessFile = new RandomAccessFile(details,"rw");

        ){
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.writeUTF(xx);
            randomAccessFile.writeUTF(fileName);
            randomAccessFile.writeUTF(yyy);
            randomAccessFile.writeUTF(fullpath);
            randomAccessFile.writeUTF(rwx);
//            fos.write(xx.getBytes());
//            fos.write(fileName.getBytes());
//            fos.write(yyy.getBytes());
//            fos.write(fullpath.getBytes());
//            fos.write(rwx.getBytes());
        }

    }

    private static String CheckForTwoBytes(String xx) {
        String nov = null;
        if(xx.length()==1){
            nov = "0"+xx;
            return nov;
        }
        return xx;
    }

    private static String CheckForThreeBytes(String yyy) {
        String nov = null;
        if(yyy.length()==1){
            nov = "00"+yyy;
            return nov;
        }
        if(yyy.length()==2){
            nov = "0"+yyy;
            return nov;
        }
        return yyy;
    }

    private static void copyThisFile(File file, File toDir) throws IOException {
        File nov = new File(toDir, file.getName());
        copy(file,nov);
    }

    private static void copy(File file, File nov) throws IOException {
        try(
                FileInputStream is = new FileInputStream(file);
                FileOutputStream os = new FileOutputStream(nov);
        ){
            int b = 0;
            while((b = is.read()) != -1){
                os.write(b);
            }
        }
    }
}
