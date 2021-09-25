
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Clientb {
    public static void main(String[] args) {
        Body b = null;
        try {
            b = new Body(8080, true);
        }catch (IOException e) {
            System.out.println("Faield to create body");
            System.exit(1);
        }
        b.listen(
            (s) -> {
                System.out.println("got stream");
                try {
                    InputStream i = s.getInputStream();
                    System.out.println(i);
                    DataInputStream ds = new DataInputStream(i);
                    System.out.println(ds);
                    BufferedReader in = new BufferedReader(new InputStreamReader(i));
                    
                    String o = in.readLine();
                    System.out.println(o);
                    // int g = in.read();
                    // while (g != 0) {
                    //     System.out.println("R");
                    //     System.out.print((char)(g));
                    // }
                    

                    
                } catch(IOException e) {
                    
                    System.out.println("Failed to read");
                }



            }
        );
    }   
}
