
import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address; //local local

public class Body {
    private Socket dest;
    private ServerSocket server;
    private boolean inServerMode = false;
    private boolean inSenderMode = true;
    private int port = 0;

    public Body(int port) throws IOException {
        this.port = port;
        this.server = new ServerSocket(port);
    }

    /**
     * Precondition that this object is not in server mode
     * @param ip the local destination
     * @return returns false if connection fails
     */
    public boolean setDestination(Inet4Address ip) {
        if (this.inServerMode) {
            //TODO: throw an error
        }
        try {
            dest = new Socket(ip, port);
            this.inServerMode = true;
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
            OutputStream outS = dest.getOutputStream();
            DataOutputStream o = new DataOutputStream(outS);

            o.write(data);
        }
    }

    public void listen() {
        if (this.inSenderMode) {
            //TODO: throw an error
        }
        this.inServerMode = true;
        while (this.inServerMode) {
            try {
                Socket s = server.accept();
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