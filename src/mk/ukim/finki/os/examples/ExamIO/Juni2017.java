package mk.ukim.finki.os.examples.ExamIO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Juni2017 {
    public static void main(String[] args) throws IOException {
        traverse("data\\in","data\\out");
    }

    private static void traverse(String in, String out) throws IOException {
        File inDir = new File(in);

        File[] list = inDir.listFiles();

        for(File file : list){
            if(file.isDirectory()){
                traverse(file.getAbsolutePath(), out);
            }
            else if(file.isFile()){
                if(file.getName().endsWith(".dat") && file.canRead() && file.canWrite()){
                    copyFile(file,out);
                }
            }
        }
    }

    private static void copyFile(File file, String dest) throws IOException {

        File nov = new File(dest,file.getName());

        if(nov.exists()){
            nov.delete();
        }
        nov.createNewFile();

        RandomAccessFile vlez = null;
        RandomAccessFile izlez = null;
        try{
            vlez = new RandomAccessFile(file,"r");
            izlez = new RandomAccessFile(nov, "rw");

            int b = 0;

            while((b = vlez.read()) != -1){
                izlez.write(b);
            }
        }finally {
            if(vlez != null) vlez.close();
            if(izlez != null) izlez.close();
        }

    }
}
