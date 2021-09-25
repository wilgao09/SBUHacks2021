
import java.net.Socket;
import java.util.function.Consumer;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Body {
    private Socket dest;
    private ServerSocket server;
    private boolean inServerMode = false;
    private boolean inSenderMode = true;
    public int port = 0;

    public Body(int port, boolean asServer) throws IOException {
        this.port = port;
        if (asServer) {
            this.server = new ServerSocket(port);
        }
        
    }

    /**
     * Precondition that this object is not in server mode
     * @param ip the local dPog estination
     * @return returns false if connection fails
     */
    public boolean setDestination(String ip) {
        if (this.inServerMode) {
            System.out.println("Invalid state");
        }
        try {
            dest = new Socket(ip, port);
            this.inServerMode = true;
            System.out.println("Successfully set dest: " + ip);
        } catch (IOException e) {
            return false;
        }
        return true;
        
    }

    /**
     * Sends data to the destination iff this Body is in Sender mode only
     * @param data The data to send as a series of bytes
     */
    public void send(byte[] data) throws IOException{
        if (this.inSenderMode && !this.inServerMode) {
            System.out.println("Sending data to " + this.dest);
            OutputStream outS = dest.getOutputStream();
            DataOutputStream o = new DataOutputStream(outS);
            PrintWriter out = new PrintWriter(outS, true);
            out.println("this a string");
            out.flush();

            // o.writeUTF("frfrfr");
            // o.flush();
        } else {
            System.out.println("Tried to send a message, but couldn't (Body is set to server mdoe or is not in sender mode)");
        }
    }

    public void listen(Consumer<Socket> f) {
        if (this.inSenderMode) {
            System.out.println("Tried to set up server, but couldn't (Body si in sender mode)");
        }
        this.inServerMode = true;
        while (this.inServerMode) {
            try {
                System.out.println("Listening");
                Socket s = server.accept();
                System.out.println("Got a socket");
                f.accept(s);
                System.out.println("Processed socket");
                s.close();
                //TODO: let this function accept a lambda expression that handles a Socket
                //the function should have no delay/timeout/be async
            } catch (IOException e) {
                //do something
            }
            

        }
    }

    public void breakListen() {
        this.inServerMode = false;
        try {
            this.server.close();
        } catch (Exception e) {
            //handle error
        }
    }

    public void breakSend() {
        this.inSenderMode = false;
        try {
            this.dest.close();
        } catch (IOException e) {
            //handle error
        }
    }

    public boolean isListening() {
        return this.inServerMode;
    }

    public boolean isSending() {
        return this.inSenderMode;
    }

}