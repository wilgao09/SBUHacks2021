
import java.io.IOException;
import java.net.Inet4Address;

public class Clienta {

    public static String DEST = "172.24.196.187";

    public static void main(String[] args) {
        Body b = null;
        try {
            b = new Body(8080, false);
        }catch (IOException e) {
            System.out.println("Faield to create body");
            System.exit(1);
        }
        b.setDestination(DEST);

        byte[] dat = "this is a test string".getBytes();

        try {
            b.send(dat);
            System.out.println("Sent data");
        } catch (IOException e) {
            System.out.println("Failed to send bytes\nAborting . . .");
            System.exit(1);
            
        } 
        b.breakSend();
        
    }
}
