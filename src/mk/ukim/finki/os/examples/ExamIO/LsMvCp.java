package mk.ukim.finki.os.examples.ExamIO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LsMvCp {


    public static void main(String[] args) throws IOException {

        new LsMvCp().work("data\\in", "data\\out");
    }

    private void work(String in, String out) throws IOException {

        File inDir = new File(in);
        File outDir = new File(out);

        if (!inDir.exists()) {
            System.out.println("prazno");
            return;
        }

        if (!outDir.exists()) {
            outDir.mkdirs();
        } else {
            removeDirectoryContent(outDir);
        }
        Rekurzivno(inDir, outDir);
    }

    private void Rekurzivno(File inDir, File outDir) throws IOException {
        File[] files = inDir.listFiles();


        List<File> childrenFiles = CheckForChildrensOrDir(files, outDir);

        if (childrenFiles.size() > 5) {
            moveFiles(childrenFiles, outDir);
        } else if (childrenFiles.size() < 5) {
            copyFiles(childrenFiles, outDir);
        }
    }

    private List<File> CheckForChildrensOrDir(File[] children, File outDir) throws IOException {
        List<File> childrenFiles = new ArrayList<>();
        for (File file : children) {
            if (file.isFile()) {
                childrenFiles.add(file);
            } else if (file.isDirectory()) {
                Rekurzivno(file, outDir);
            }
        }
        return childrenFiles;
    }

    private void copyFiles(List<File> children, File outDir) throws IOException {
        for (File file : children) {
            File destination = new File(outDir, file.getName());
            copy(file, destination);
        }
    }

    private void copy(File file, File destination) throws IOException {
        try(
                RandomAccessFile fromFile = new RandomAccessFile(file,"r");
                RandomAccessFile toFile = new RandomAccessFile(destination, "rw");
                ){
            int b = 0;
            while ((b = fromFile.read()) != -1) {
                toFile.write(b);
            }
        }
    }

    private void moveFiles(List<File> children, File outDir) {
        for (File file : children) {
            File destination = new File(outDir, file.getName());
            file.renameTo(destination);
        }
    }

    private void removeDirectoryContent(File dir) {
        File[] files = dir.listFiles();
        for (File f : files) {
            f.delete();
        }
    }
}