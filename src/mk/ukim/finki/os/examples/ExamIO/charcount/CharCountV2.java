package mk.ukim.finki.os.examples.ExamIO.charcount;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CharCountV2 {
    public static void main(String[] args) throws IOException {
        List<byte[]> result = read("data\\input.txt");

        for(byte[] element : result){
            for(byte b : element){
                System.out.print(b - '0');
            }
            System.out.println();
        }

    }
    private static List<byte[]> read(String input) throws IOException{
        File inputFile = new File(input);

        List<byte[]> result = new ArrayList<>();

        try(
            InputStream is = new FileInputStream(inputFile);
        ){
            int len, b;
            while ((b = is.read()) != -1){
                len = b - '0';
                byte[] element;
                element = readElement(is,len);
                result.add(element);
            }

        }
        return result;
    }

    private static byte[] readElement(InputStream is, int len) throws IOException {
        byte[] element = new byte[len];

        for(int i=0;i<len;i++){
            int b = is.read();
            if(b==-1){
                throw new IllegalStateException("NEma "+ len +" bajtovi vo fajlot!");
            }
            element[i] = (byte) b;
        }
        return  element;
    }
}
