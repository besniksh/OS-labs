package mk.ukim.finki.os.examples.ExamIO;


import mk.ukim.finki.os.io.exceptions.NotDirectoryException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by besniksh on 3/3/17.
 */
public class ExamIO2 {


    public static void main(String[] args) throws IOException {

        moveWritableTxtFiles("data\\dirfrom", "data\\dirto", 1);

        ArrayList<byte[]> data = new ArrayList<byte[]>();
        byte[] element = {'b','e','s','n','i','k'};
        data.add(element);
        serializeData("data\\destination.txt", data);



        try {
            byte[] rez = deserializeDataAtPosition("data\\destination.txt", 1, 3);
            for (byte b : rez){
                System.out.print((char)b + " ");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void moveWritableTxtFiles(String from, String to, long size) {
        File fromf = new File(from);
        File tofile = new File(to);

        if(!tofile.exists()){
            tofile.mkdirs();
        }
        if (fromf.isDirectory()) {
            File[] allfiletxt = fromf.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".txt");
                }
            });

            if (allfiletxt.length > 0) {
                for (File file : allfiletxt) {
                    //System.out.println(f);
                    if (file.length() > size) {
                        moveFile(file, to);
                    }
                }
            } else {
                System.out.println("Не постои ниту една датотека со екстензија .txt");
            }

        } else {
            System.out.println("Ne Postoi!");
        }
    }


    public static boolean moveFile(File from, String to) {
        File nov = new File(to,from.getName());
        return from.renameTo(nov);
    }



    public static void serializeData(String destination, List<byte[]> data) throws IOException {

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(new File(destination));
            for (byte[] element : data) {
                fos.write(element);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (fos != null)
                fos.close();
        }


    }

    public static byte[] deserializeDataAtPosition(String source, long position, long elementLength) throws IOException
    {
        byte [] buffer = new byte[(int)elementLength];
        RandomAccessFile random = null;
        File s = new File(source);
        try {
            random = new RandomAccessFile(s,"r");
            random.seek(position*elementLength);
            for (int i=0; i<elementLength; i++){
                buffer[i] = random.readByte();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if (random != null)
                random.close();
        }
        return buffer;
    }

}




