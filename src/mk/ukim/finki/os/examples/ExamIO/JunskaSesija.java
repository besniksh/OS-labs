package mk.ukim.finki.os.examples.ExamIO;

import mk.ukim.finki.os.io.exceptions.NotDirectoryException;

import java.io.*;
import java.nio.BufferOverflowException;
import java.util.ArrayList;

/**
 * Created by besniksh on 3/5/17.
 */
public class JunskaSesija {
    private static String DATAIN = "data/dirfrom";
    private static String DATATO = "data/dirto/";

    public static void main(String[] args) throws IOException {
        manage(DATAIN,DATATO);
    }

    public static void manage(String in, String out) throws IOException {
        File fin = new File(in);
        File fout = new File(out);

        File[] alldat =  fin.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".dat");
            }
        });
        FileInputStream fis = null;
        FileOutputStream fos = null;

        if(!fin.exists()){
            System.out.println("ne postoi");
            return;
        }
        if(fout.exists()){
            for(File f: fout.listFiles()){
                f.delete();
            }
        }

        if(alldat.length > 0 ) {
            for (File f : alldat) {
                if(f.isHidden()){
                    //                    f.delete();
                    System.out.println("zbunet sum : " + f.getAbsolutePath());
                }
                else if (f.canWrite()) {
                    moveFile(f, out);
                    System.out.println("Pomestuvam : "+ f.getAbsolutePath());
                }
                else if(!f.canWrite()){
                    File writtable = new File("data/resources/writable-content.txt");


                    BufferedReader br = new BufferedReader(new FileReader(f));
                    BufferedReader br2 = new BufferedReader(new FileReader(writtable));

                    String line;
                    String result = "";

                    while((line = br.readLine()) != null){
                        result = result + line; //total of 'file'
                    }
                    String result2 = "";
                    String line2;

                    while((line2 = br2.readLine()) != null){
                        result2 = result2 + line2;
                    }
                    fos = new FileOutputStream(writtable);

                    String total = result + result2;
                    fos.write(total.getBytes());





                    System.out.println("dopisuvam : "+ f.getAbsolutePath());
                }
            }
        }else{
            System.out.println("Ne postoi nitu eden file so ekstenzija .dat");
        }

    }

    public static boolean moveFile(File file,String newparent){
        return moveFileandRename(file,newparent,file.getName());
    }

    public static boolean moveFileandRename(File file,String newParent,String newName){
        File parent = new File(newParent);


        if(!parent.isDirectory()){
            System.out.println("Ne Postoi PARENT!");
            return false;
        }

        File renamedfile = new File(parent,newName);

        if(renamedfile.exists()){
            System.out.println("OVOJ FILE VEKE POSTOI");
            return false;
        }

        return file.renameTo(renamedfile);
    }
}
