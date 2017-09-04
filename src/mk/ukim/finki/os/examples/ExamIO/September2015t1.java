package mk.ukim.finki.os.examples.ExamIO;


import java.io.*;

public class September2015t1 {

    public static void main(String[] args) throws IOException {
        DeleteRecurzivno("data\\out");
        Rekurzivno("data\\in", "data\\out");
    }

    public static void DeleteRecurzivno(String outDir) {
        File out = new File(outDir);
        File[] all = out.listFiles();
        for (File f : all) {
            if (f.isDirectory()) {
                DeleteRecurzivno(f.getAbsolutePath());
            } else {
                if (!f.getName().equals("big.bin")) {
                    f.delete();
                }
            }
        }
    }

    public static void Rekurzivno(String inDir, String outDir) throws IOException {
        File source = new File(inDir);

        if (!source.exists()) {
            System.out.println("in ne postoi");
            return;
        }


        File[] lista = source.listFiles();

        for (File f : lista) {

            if (CheckBin(f)) {
                if (f.length() < 10000) {
                    copyThisFile(f, "data\\out");
                } else {
                    CopyToBig(f, source, "data\\out\\big.bin");
                }
            }
            if (f.isDirectory()) {
                Rekurzivno(f.getAbsolutePath(), outDir);
            }
        }
    }

    private static void CopyToBig(File f, File source, String outBig) throws IOException {
        File big = new File(outBig);
        File temp = new File(source, "temp");

        readFromSourceWriteToDestination(big, temp);
        big.delete();
        big.createNewFile();

        readFromSourceWriteToDestination(f, big);

        readFromSourceAppendToDestination(temp, big);
        temp.delete();

    }

    public static void readFromSourceWriteToDestination(File source, File destination) throws IOException {
        try(
                RandomAccessFile vlez = new RandomAccessFile(source,"r");
                RandomAccessFile izlez = new RandomAccessFile(destination, "rw");
        ){
            int b;
            while ((b = vlez.read()) != -1){
                izlez.write(b);
            }
        }
    }

    public static void readFromSourceAppendToDestination(File source, File destination) throws IOException {
        try(
                FileInputStream vlez = new FileInputStream(source);
                FileOutputStream izlez = new FileOutputStream(destination,true)
        ){
            int b = 0;
            while (( b = vlez.read()) != -1){
                izlez.write(b);
            }
        }
    }

    public static boolean CheckBin(File f) {
        if (f.getName().endsWith(".bin")) {
            return true;
        }
        return false;
    }


    public static void copyThisFile(File file, String outDir) throws IOException {
        File temp = new File(outDir, file.getName());

        BufferedReader vlez = null;
        BufferedWriter izlez = null;
        try {
            vlez = new BufferedReader(new FileReader(file));
            izlez = new BufferedWriter(new FileWriter(temp));

            String line;
            while ((line = vlez.readLine()) != null) {
                izlez.write(line);
            }
        } finally {
            if (vlez != null) {
                vlez.close();
            }
            if (izlez != null) {
                izlez.close();
            }
        }
    }
}

