package com.finki.os.labs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class HW01_4 {


    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        BufferedReader br=new BufferedReader(new FileReader(args[0]));

        String line=null;

        int count=0;
        while((line=br.readLine()) != null){
            String pom[]=line.split(" ");
            for(int i=0;i< pom.length;i++){
                if(pom[i].toLowerCase().compareTo(args[1].toLowerCase()) == 0)
                    count++;
            }
        }

        br.close();
        System.out.println(count);
    }

}