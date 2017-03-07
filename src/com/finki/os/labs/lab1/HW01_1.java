package com.finki.os.labs.lab1;

import java.io.File;
import java.io.FilenameFilter;

public class HW01_1 {

    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Ne e vnesen nitu eden argument");
            return;
        }
        File imenik = new File(args[0]);
        if(!imenik.exists()){
            System.out.println("Ne postoi imenik so takvo ime!");
            return;
        }
        if(!imenik.isDirectory()){
            System.out.println("Fajlot so takvo ime ne e imenik!");
        }

        File[] files = imenik.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("txt");
            }
        });

        long vkupno=0;

        for(File f:files){
            vkupno += f.length();
        }

        double prosek = vkupno/files.length;

        System.out.println("Vkupnataa golemina na .txt fajlovite e "+vkupno+" bajti\n A prosocntata golemina e " +prosek+ " bajti");
    }
}
