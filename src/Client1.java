import java.io.*;
import java.net.Socket;

public class Client1 {
    BufferedReader keyboard;
    BufferedReader getMessage;
    PrintWriter writer;
    public static void main(String[] args) {
        Client1 c = new Client1();
        c.connectToTheServer();
    }

    public void connectToTheServer() {
        try {
            Socket socket = new Socket("localhost",2014);
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            getMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            String message = "";
            Thread t = new Thread(new ReceiveMessage());
            t.start();
            while (!message.equalsIgnoreCase("quit")) {
                System.out.println(">");
                message = keyboard.readLine();
                writer.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ReceiveMessage implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Server: "+getMessage.readLine());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }


        }
    }
}
