

import java.net.Inet4Address;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;


public class P2P {

    public class UIState {
        public String ip = "";
        public String port = "";
        public String fOut = "";
        public String fIn = "";
        public Action click = P2P.Action.NONE;
    }

    public enum Action {
        NONE,
        SEND,
        LISTEN,
        GET_IP
    }

    private static Body b;

    public static boolean isValidIP(String s) {
        try {
            Inet4Address a = (Inet4Address) Inet4Address.getByName(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidFileOut(String s) {
        File f = new File(s);
        return f.exists();
    }
    public static boolean isValidFolderIn(String s) {
        File f = new File(s);
        return f.canWrite();
    }

    //assume b works
    public static void sendFile(UIState s ) {
        boolean vIP = isValidIP(s.ip);
        boolean vPort = ( Integer.parseInt(s.port) < 20000);
        boolean vFOUT = isValidFileOut(s.fOut);
        boolean vFIN = isValidFolderIn(s.fIn);

        if (vIP && vPort && vFOUT && vFIN) {
            if (b.isListening()) {
                b.breakListen();
            }
            b.setDestination(s.ip);
            File f = new File(s.fIn);
            try {
                byte[] data = Files.readAllBytes(f);
            } catch (IOException e) {
                System.out.println("FAILED FOR UNKNOWN REASON");
                System.exit(1);
            } catch (OutOfMemoryError e) {
                System.out.println("NOT ENOUGH MEMORY");
                System.exit(1);
            } catch (Exception e) {
                System.out.println("Unhandled");
                System.exit(1);
            }
            
        }
    }

    public static void main(String[] args) {
        //sender
        try {
            b = new Body(8080, false);
        } catch (Exception e) {}

        

    }
}
