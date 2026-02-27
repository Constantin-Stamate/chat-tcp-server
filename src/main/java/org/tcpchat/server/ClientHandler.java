package org.tcpchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.tcpchat.server.Server.*;

public class ClientHandler extends Thread {

    private static int ipCounter = 2;

    private final Socket socket;
    private PrintWriter out;
    private String clientName;
    private String virtualIP;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            synchronized (clientWriters) {
                clientWriters.add(out);
            }

            clientName = in.readLine();
            virtualIP = "127.0.0." + ipCounter++;
            userIPMapping.put(clientName, virtualIP);

            System.out.println(clientName + " connected with virtual IP: " + virtualIP);
            broadcast(clientName + " (" + virtualIP + ") joined the conversation");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase(clientName + ": exit")) {
                    System.out.println(clientName + " disconnected");
                    break;
                }

                System.out.println("Message received from " + virtualIP + ": " + message);
                broadcast("[" + virtualIP + "] " + message);
            }
        } catch (IOException e) {
            System.out.println(clientName + " connection lost");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error while closing socket");
            }

            synchronized (clientWriters) {
                clientWriters.remove(out);
            }

            userIPMapping.remove(clientName);
            broadcast(clientName + " (" + virtualIP + ") left the conversation");
        }
    }
}