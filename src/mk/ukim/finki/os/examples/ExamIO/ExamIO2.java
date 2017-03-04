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

        //moveWritableTxtFiles("data/dirfrom", "data/dirto", 100);

        ArrayList<byte[]> data = new ArrayList<byte[]>();
        byte[] element = {'b','e','s','n','i','k'};
        data.add(element);
        serializeData("data/destinacija.txt", data);



        try {
            byte[] rez = deserializeDataAtPosition("data/izvor.txt", 0, 3);
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


    public static boolean moveFile(File file, String newParent) {
        return moveAndRenameFile(file, newParent, file.getName());
    }

    public static boolean moveAndRenameFile(File file, String newParent, String newName) {
        File parent = new File(newParent);
        parent.mkdir();

        if (!parent.isDirectory()) {
            throw new NotDirectoryException(parent.getAbsolutePath());
        }

        File renamedFile = new File(parent, newName);

        if (renamedFile.exists()) {
            throw new NotDirectoryException(renamedFile.getAbsolutePath());
        }

        return file.renameTo(renamedFile);
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




