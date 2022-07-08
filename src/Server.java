import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    BufferedReader getMessage;
    BufferedReader keyboard;
    PrintWriter writer;
    public static void main(String[] args) {
        Server s = new Server();
        s.acceptClient();
    }

    public void acceptClient() {
        try {
            ServerSocket serverSocket = new ServerSocket(2014);
            Socket clientSocket = serverSocket.accept();
            getMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);
            Thread t = new Thread(new sendMessage());
            t.start();
            while (true) {
                System.out.println("Client: "+getMessage.readLine());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    class sendMessage implements Runnable{
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("> ");
                    String message = keyboard.readLine();
                    writer.println(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

}
