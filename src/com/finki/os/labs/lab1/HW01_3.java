package com.finki.os.labs.lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HW01_3 {
    public static void main(String[] args) throws IOException{
        BufferedReader br;

        try{
            br = new BufferedReader(new FileReader("data/izvor.txt"));
        } catch (FileNotFoundException e){
            System.out.println("Ne e pronajden takov fajl!");
            return;
        }

        BufferedWriter bw;

        try{
            bw = new BufferedWriter(new FileWriter("data/destinacija.txt"));
        } catch (IOException e){
            System.out.println("Ne e mozno da se otvori fajlot!");
            br.close();
            return;
        }

        StringBuilder sb = new StringBuilder();
        String str;

        while ((str = br.readLine()) != null){
            sb = new StringBuilder(str);
            sb.reverse();
            bw.write(sb+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }
}