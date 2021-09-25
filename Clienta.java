
import java.io.IOException;
import java.net.Inet4Address;

public class Clienta {

    public static String DEST = "172.24.112.100";

    public static void main(String[] args) {
        Body b = null;
        try {
            b = new Body(8080);
        }catch (IOException e) {
            System.out.println("Faield to create body");
            System.exit(1);
        }
        b.setDestination(DEST);

        byte[] dat = "Welcome to the spirit realm".getBytes();

        try {
            b.send(dat);
            System.out.println("Sent data");
            Thread.sleep(10000);
        } catch (IOException e) {
            System.out.println("Failed to send bytes\nAborting . . .");
            System.exit(1);
            
        } catch (InterruptedException e) {}

        b.breakSend();
        
    }
}
