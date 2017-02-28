package com.finki.os.labs;

import java.io.*;
import java.nio.BufferOverflowException;

/**
 * @author Riste Stojanov
 */
public class SafeReadFileIntoBufferEdited{

    public static void main(String[] args) throws IOException {
        //InputStream inputStream = new FileInputStream(
             //   new File("data/izvor.txt")
        //);
        FileInputStream inputStream;
        try{
            inputStream = new FileInputStream("data/izvor.txt");

        }catch (FileNotFoundException e){
            System.out.println("Ne moze da se otvori fajlot!");
            return;
        }

        byte[] data = new byte[100];
        int index = 0;
        int current;

        while ((current = inputStream.read()) != -1) {
            if (index == data.length) {
                throw new BufferOverflowException();
            }
            // This is ok :)
            // Only one byte is read by inputStream.read() in range between 0-255, so the cast is safe
            data[index] = (byte) current;
            index++;
        }

//        FileOutputStream fos;
//
//        try{
//            fos = new FileOutputStream("data/destinacija.txt");
//        }catch (FileNotFoundException e){
//            System.out.println("Ne moze da se otvori fajlot!");
//            return;
//        }



        System.out.println("Successfully read "+index+" bytes");
        System.out.println("The content of the file is: ");
        System.out.println(new String(data, 0, index));

    }
}
