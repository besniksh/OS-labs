package com.finki.os.labs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/// ---- BAJT PO BAJT ------ ///

public class HW01_2{
    public static void main(String[] args) throws IOException {
        FileInputStream fis;

        try{
            fis = new FileInputStream("data/izvor.txt");
        } catch(FileNotFoundException e){
            System.out.println("Ne e pronajden takov fajl!");
            return;
        }

        ArrayList<Integer> tekst = new ArrayList<Integer>();
        int bajt = 0;

        while((bajt = fis.read()) != -1){
            tekst.add(bajt);
        }


        fis.close();

        FileOutputStream fos;

        try{
            fos = new FileOutputStream("data/destinacija.txt");
        } catch(FileNotFoundException e){
            System.out.println("Ne e mozno da se otvori fajlot!");
            return;
        }

        for (int i=tekst.size()-1; i>=0; i--)
            fos.write(tekst.get(i));

        fos.flush();
        fos.close();


    }
}