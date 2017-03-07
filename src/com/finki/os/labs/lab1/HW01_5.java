package com.finki.os.labs.lab1;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.util.Scanner;

public class HW01_5 {
    public static void main(String[] args) throws IOException {

        BufferedReader br;


        try {
            br = new BufferedReader(new FileReader("data/rezultati.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Ne e pronajden takov fajl!");
            return;
        }

        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter("data/rezultati.tsv"));
        } catch (FileNotFoundException e) {
            System.out.println("Ne e mozno da se otvori toj fajl!");
            return;
        }


        String line = br.readLine();
        String predmeti[] = line.split(",");


        int brOceni = predmeti.length-1;


        int zbirOceniPoPredmet[] = new int[line.length()];
        int students = 0;

        System.out.println(line);
        bw.write(line.replace(",", "\t")+"\n");

        while ((line = br.readLine()) != null){
            String oceniUcenik[] = line.split(",");
            int zbir = 0;
            students++;

            for (int i=1; i<oceniUcenik.length; i++){
                zbir += Integer.parseInt(oceniUcenik[i]);
                zbirOceniPoPredmet[i] += Integer.parseInt(oceniUcenik[i]);
            }

            System.out.println("Studentot: "+oceniUcenik[0]+" ima prosek: "+(zbir*1.0/brOceni));

            bw.write(line.replace(",", "\t")+"\n");
        }

        for (int i=1; i<predmeti.length; i++)
            System.out.println("Po predmetot: "+predmeti[i]+" prosecnata ocena e: "+(zbirOceniPoPredmet[i]*1.0/students));

        br.close();
        bw.flush();
        bw.close();
    }
}