
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Clientb {
    public static void main(String[] args) {
        Body b = null;
        try {
            b = new Body(8080);
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
                    String strr = ds.readUTF();
                    System.out.println(strr);
                } catch(IOException e) {
                    System.out.println("Failed to read");
                }



            }
        );
    }   
}
