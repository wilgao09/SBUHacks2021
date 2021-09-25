
import java.io.DataInputStream;
import java.io.IOException;

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
                try {
                    DataInputStream ds = new DataInputStream(s.getInputStream());
                    String strr = ds.readUTF();
                    System.out.println(strr);
                } catch(IOException e) {
                    System.out.println("Failed to read");
                }



            }
        );
    }   
}
