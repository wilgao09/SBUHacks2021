

import java.net.Inet4Address;
import java.io.File;


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

    private static Body b = new Body(8080, false);

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
    public static boolean isValidFolderOut(String s) {
        File f = new File(s);
        return f.canWrite();
    }

    public static void sendFile(UIState s ) {

    }
}
