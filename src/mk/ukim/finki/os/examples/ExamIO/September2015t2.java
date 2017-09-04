package mk.ukim.finki.os.examples.ExamIO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class September2015t2 {
    public static void main(String[] args) throws IOException {
        rabota("data\\vlez","data\\izlez");
    }

    private static void rabota(String vlez_value, String izlez_value) throws IOException {
        File vlez = new File(vlez_value);
        File izlez = new File(izlez_value);
        if(!vlez.exists()){
            System.out.println("vlez ne postoi");
            return;
        }
        rekurzivnoBrisenje(izlez);
        rekurzivnoListenje(vlez,izlez);
    }

    private static void rekurzivnoBrisenje(File izlez) {
        File[] list = izlez.listFiles();

        for(File f : list){
            if(f.isFile()){
                f.delete();
            }else{
                rekurzivnoBrisenje(f);
            }
        }
    }

    private static void rekurzivnoListenje(File vlez, File izlez) throws IOException {
        File[] list = vlez.listFiles();

        for(File f : list){
            if(f.isFile()){
                if(f.getName().endsWith(".dat")){
                    if(!f.canWrite() && f.canRead()){
                        copyFile(f,izlez);
                    }
                    else if(f.canWrite() && f.canRead()){
                        DodadiNaKraj(f,izlez);
                    }
                }
            }
            else if(f.isDirectory()){
                rekurzivnoListenje(f,izlez);
            }

        }
    }

    private static void DodadiNaKraj(File vlez_file, File izlez_value) throws IOException {

        File write = new File(izlez_value,"write.dat");
        write.createNewFile();

        RandomAccessFile vlez = null;
        RandomAccessFile izlez = null;

        try{
            vlez = new RandomAccessFile(vlez_file,"r");
            izlez = new RandomAccessFile(write,"rw");

            int b = 0;
            izlez.seek(vlez.length());
            while ((b = vlez.read()) != -1 ){
                izlez.write(b);
            }

        }finally {
            if(vlez != null) vlez.close();
            if(izlez != null) izlez.close();
        }
    }

    private static void copyFile(File f, File izlez_value) throws IOException {
        File nov = new File(izlez_value,f.getName());

        if(nov.exists()){
            nov.delete();
        }
        nov.createNewFile();

        RandomAccessFile vlez = null;
        RandomAccessFile izlez = null;

        try{
            vlez = new RandomAccessFile(f,"r");
            izlez = new RandomAccessFile(nov,"rw");
            int b = 0;
            while ((b = vlez.read()) != -1){
                izlez.write(b);
            }
        }finally {
            if(vlez != null) vlez.close();
            if(izlez != null) izlez.close();
        }
    }

}
