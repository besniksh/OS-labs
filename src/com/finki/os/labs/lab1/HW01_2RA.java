package com.finki.os.labs.lab1;

import java.io.*;
import java.util.ArrayList;

/// ---- BAJT PO BAJT ------ ///

public class HW01_2RA {
    public static void main(String[] args) throws IOException {
        RandomAccessFile fos = null;
        RandomAccessFile fin = null;
        RandomAccessFile db = null;
        String source = "data/izvor.txt";
        String destination = "data/destinacija.txt";
        String baza = "data/databaza.txt";

        try {
            fin = new RandomAccessFile(source, "r");
            fos = new RandomAccessFile(destination, "rw");
            db = new RandomAccessFile(baza, "rw");

            long finlength = fin.length();
            long foslength = fos.length();
            long dblength = db.length();
            int pos=0;

            while (pos<finlength) {
                fin.seek(pos);
                db.write(fin.read());
                pos++;
            }

        } finally {
            if (fos != null) {
                fos.close();
            }
            if (fin != null) {
                fin.close();
            }
            if(db != null){
                db.close();
            }
        }
    }
}