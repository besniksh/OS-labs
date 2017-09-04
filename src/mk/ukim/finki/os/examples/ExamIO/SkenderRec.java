package mk.ukim.finki.os.examples.ExamIO;

import java.io.*;


public class SkenderRec {
    public static void main(String[] args) throws IOException {
        work("data\\skender");
    }

    private static void work(String inDir) throws IOException {
        File in = new File(inDir);
        File dest = new File("data\\details.txt");
        File[] files = in.listFiles();
        BufferedWriter csvWritter = new BufferedWriter(new FileWriter(dest,true),',');
        for(File file : files){
            if(file.isDirectory()){
                work(file.getAbsolutePath());
            }else
            {
                System.out.println("File : " + file.getName() +" -> "+ file.length());
            }
        }
    }
}
