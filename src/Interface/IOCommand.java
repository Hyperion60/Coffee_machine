package Interface;

import java.io.*;
import java.net.Socket;

public class IOCommand {
    private final Socket socket;

    public IOCommand(Socket socket) {
        this.socket = socket;
    }

    public int writeNetwork(String text) {
        if (this.socket != null) {
            OutputStream outputStream;
            try {
                outputStream = this.socket.getOutputStream();
            } catch (IOException e) {
                return 1;
            }
            if (outputStream != null) {
                PrintStream out = new PrintStream(outputStream);
                out.println(text);
                return 0;
            }
        }
        return 1;
    }

    public String readNetwork() {
        String line;
        InputStream inputStream;
        if (this.socket != null) {
            try {
                inputStream = this.socket.getInputStream();
            } catch (IOException exception) {
                inputStream = null;
            }
            if (inputStream != null) {
                InputStreamReader in = new InputStreamReader(inputStream);
                BufferedReader input = new BufferedReader(in);
                try {
                    line = input.readLine();
                } catch (IOException exception) {
                    System.out.println("Failed to socket read");
                    return null;
                }
                return line;
            }
        }
        return null;
    }
}
