package mk.ukim.finki.os.examples.ExamIO;


import mk.ukim.finki.os.io.exceptions.FileExistsException;
import mk.ukim.finki.os.io.exceptions.NotDirectoryException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by besniksh on 3/3/17.
 */
public class ExamIO {


    public static void main(String[] args) throws IOException {

        moveWritableTxtFiles("data/dirfrom", "data/dirto");

        List<byte[]> data=new ArrayList<>();
        deserializeData("data\\destination.txt",data,3);
        for(byte[] element : data){
            System.out.println(element);
        }

        //invertLargeFile("data/izvor.txt","data/destinacija.txt");

    }



    public static void moveWritableTxtFiles(String from, String to) {
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
                    if (file.canWrite()) {
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


    public static void deserializeData(String source, List<byte[]> data, int elementLength) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(source);
            while (true) {
                // Vcituvanje na sekoj od elementite

                // bafer za elementot
                byte[] buff = new byte[elementLength];

                // do koj bajt sum stignal so citanjeto
                // na elementot (za vo baferot)
                int offset = 0;
                // uste kolku elementi treba da procitam
                // vo baferot
                int len = elementLength;

                // kolku bajti se procitani vo baferot
                // pomegju 0 - elementLength
                int read = 0;


                // Ako e prekinato citanjeto, a ne se
                // procitani site bajti od elementot,
                // so ovoj while ke se docitaat
                while (offset != elementLength) {
                    // se obiduva da go popolni baferot,
                    // no ne mora da bide celosno (moze
                    // da e prekinato)
                    read = fis.read(buff, offset, len);
                    if (read == -1) {
                        break;
                    }
                    // pomesti go offsetot za procitanite bajti
                    offset += read;
                    // namali ja dolzinata na preostanatite bajti
                    // od elementot za procitanite
                    len = elementLength - read;
                }

                if (read == -1) {
                    break;
                }
                data.add(buff);
            }

        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }


    public static void invertLargeFile(String source, String destination) throws IOException {
        int dataLength = 1;
        RandomAccessFile fos = null;
        RandomAccessFile fin = null;
        try {
            fin = new RandomAccessFile(source, "r");
            fos = new RandomAccessFile(destination, "rw");

            long pos = fin.length();
            while (pos > 0) {
                fin.seek(pos - dataLength);
                fos.write(fin.read());
                pos -= dataLength;
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (fin != null) {
                fin.close();
            }
        }

    }
}




