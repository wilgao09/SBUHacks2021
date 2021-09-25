

import java.net.Inet4Address;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;


public class P2P {

    public static class UIState {
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

    public static UIState currentState;

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

    public static boolean isValidState(UIState s) {
        boolean vIP = isValidIP(s.ip);
        boolean vPort = ( Integer.parseInt(s.port) < 20000);
        boolean vFOUT = isValidFileOut(s.fOut);
        boolean vFIN = isValidFolderIn(s.fIn);

        return vIP && vPort && vFOUT && vFIN;
    }

    //assume b works
    public static void sendFile(UIState s ) {
        if (b.isListening()) {
            b.breakListen();
        }
        b.setDestination(s.ip);
        File f = new File(s.fIn);
        try {
            byte[] data = Files.readAllBytes(f.toPath());
            b.send(data);
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

    public static void performState() {
        if (!isValidState(P2P.currentState)) {
            throw new RuntimeException("Invalid State");
        }
        P2P.Action a = P2P.currentState.click;
        switch (a) {
            case NONE :
                //pass
                break;
            case SEND :
                P2P.sendFile(P2P.currentState);
                break;
            case LISTEN :
                
                break;
            case GET_IP :
                break;
        }
    }


    public static void main(String[] args) {
        //sender
        try {
            b = new Body(8080, false);
            P2P.UIState s = new P2P.UIState();
            s.ip = "127.0.0.1";
            s.port = "8080";
            s.fOut = "";
            s.fIn = "";
            s.click = P2P.Action.NONE;
        } catch (Exception e) {}

        P2P.UIState s = new P2P.UIState();
        s.ip = "172.24.196.187";
        s.port = "8080";
        s.fOut = "my_file.pl";
        s.fIn = "a_folder/";
        s.click = P2P.Action.SEND;

        P2P.currentState = s;

    }
}
