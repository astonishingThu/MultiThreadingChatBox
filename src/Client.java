import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client {
    BufferedReader keyboard;
    BufferedReader getMessage;
    PrintWriter writer;
    JTextField outcoming;
    JTextArea incoming;

    public static void main(String[] args) {
        Client c = new Client();
        c.buildGUI();
        c.connectToTheServer();
    }

    public void buildGUI() {
        JFrame frame = new JFrame("Client");
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JButton sendButton = new JButton("Send");
        outcoming = new JTextField(50);
        incoming = new JTextArea(20,50);
        northPanel.add(incoming);
        southPanel.add(outcoming);
        southPanel.add(sendButton);
        sendButton.addActionListener(new SendListener());
        JScrollPane scrollPane = new JScrollPane(incoming);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        northPanel.add(scrollPane);
        frame.getContentPane().add(BorderLayout.SOUTH,southPanel);
        frame.getContentPane().add(northPanel);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void connectToTheServer() {
        try {
            Socket socket = new Socket("localhost",2014);
            getMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            Thread t = new Thread(new ReceiveMessage());
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ReceiveMessage implements Runnable {
        @Override
        public void run() {
            String inMessage = "";
            while (!inMessage.equalsIgnoreCase("quit")) {
                try {
                    inMessage = getMessage.readLine();
                    incoming.append("Server: "+inMessage+"\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class SendListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String outMessage = outcoming.getText();
            outcoming.setText("");
            writer.println(outMessage);
            incoming.append("\t\t\t\tYou: "+outMessage+"\n");
        }
    }
}
