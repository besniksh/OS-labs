package mk.ukim.finki.os.examples.ExamIO.charcount;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CharCount {

    public static void main(String[] args) throws IOException {
        CharCount charCount = new CharCount();
        List<byte[]> result = charCount.read("data\\input.txt");
        int s = result.size();
        System.out.print("[");
        for (byte[] element : result) {
            System.out.print("[");
            for (byte b : element) {
                System.out.print(b - '0');
            }
            s--;
            System.out.print("]");
            if(s>0){
                System.out.print(",");
            }

        }
        System.out.print("]");
    }

    private List<byte[]> read(String input) throws IOException {

        List<byte[]> result = new ArrayList<>();

        try(
                InputStream is = new FileInputStream(input)
        ){
            int b, len;
            while((b = is.read()) != -1){
                len = b - '0';
                System.out.println("len : " + len);
                byte[] element = readElement(is,len);
                result.add(element);
            }
        }
        return result;
    }

    private byte[] readElement(InputStream is, int len) throws IOException {
        byte[] element = new byte[len];
        for(int i= 0;i<len;i++){
            int b = is.read();
            if(b==-1){
                throw new IllegalStateException("Nema " + len + " bajti vo fajlot za da se porocita element.");
            }
            element[i] = (byte)b;
        }
        return  element;
    }
}
