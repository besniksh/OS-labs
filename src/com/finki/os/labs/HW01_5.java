package com.finki.os.labs;

import javax.jws.soap.SOAPBinding;
import java.io.*;

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


        String novRed = br.readLine();
        String predmeti[] = novRed.split(",");

        int brOceni = predmeti.length-1;
        int zbirOceni[] = new int[novRed.length()];
        int vkStudenti = 0;

        System.out.println(novRed);
        bw.write(novRed.replace(",", "\t")+"\n");

        while ((novRed = br.readLine()) != null){
            String oceniUcenik[] = novRed.split(",");
            int zbir = 0;
            vkStudenti++;

            for (int i=1; i<oceniUcenik.length; i++){
                zbir += Integer.parseInt(oceniUcenik[i]);
                zbirOceni[i] += Integer.parseInt(oceniUcenik[i]);
            }

            System.out.println("Studentot: "+oceniUcenik[0]+" ima prosek: "+(zbir*1.0/brOceni));

            bw.write(novRed.replace(",", "\t")+"\n");
        }

        for (int i=1; i<predmeti.length; i++)
            System.out.println("Po predmetot: "+predmeti[i]+" prosecnata ocena e: "+(zbirOceni[i]*1.0/vkStudenti));

        br.close();
        bw.flush();
        bw.close();
    }
}