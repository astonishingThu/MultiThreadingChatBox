import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    BufferedReader getMessage;
    BufferedReader keyboard;
    PrintWriter writer;
    JFrame frame;
    JTextArea incoming;
    JTextField outcoming;
    public static void main(String[] args) {
        Server s = new Server();
        s.buildGUI();
        s.startServer();
    }

    public void buildGUI() {
        frame = new JFrame("Server");
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendListener());
        incoming = new JTextArea(20,50);
        outcoming = new JTextField(50);
        northPanel.add(incoming);
        southPanel.add(outcoming);
        southPanel.add(sendButton);
        JScrollPane scrollPane = new JScrollPane(incoming);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        northPanel.add(scrollPane);
        frame.getContentPane().add(BorderLayout.SOUTH,southPanel);
        frame.getContentPane().add(northPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(2014);
            Socket clientSocket = serverSocket.accept();
            getMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);
            Thread t = new Thread(new ReceiveMessage());
            t.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    private class SendListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String outMessage = outcoming.getText();
            writer.println(outMessage);
            outcoming.setText("");
            incoming.append("\t \t \t \t You: "+outMessage+"\n");
        }
    }

    class ReceiveMessage implements Runnable {
        @Override
        public void run() {
            try {
                String inMessage = "";
                while (!inMessage.equalsIgnoreCase("quit")) {
                    inMessage = getMessage.readLine();
                    incoming.append("Client: "+inMessage+"\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
