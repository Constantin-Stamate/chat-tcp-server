package org.tcpchat.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static final int PORT = 65433;
    protected static final Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());
    protected static final Map<String, String> userIPMapping = Collections.synchronizedMap(new HashMap<>());

    private static volatile boolean isRunning = true;

    public static void main(String[] args) {
        System.out.println("Server is starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            Thread consoleThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);

                while (isRunning) {
                    String input = scanner.nextLine();

                    if (input.equalsIgnoreCase("server: exit")) {
                        isRunning = false;

                        System.out.println("Server is shutting down...");
                        broadcast("Server is shutting down and all clients will be disconnected");

                        try {
                            serverSocket.close();
                        } catch (IOException e) {
                            System.out.println("Error while closing the server: " + e.getMessage());
                        }

                        break;
                    }
                }

                scanner.close();
            });

            consoleThread.start();

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    new ClientHandler(clientSocket).start();
                } catch (IOException e) {
                    if (!isRunning) {
                        System.out.println("Server has been stopped");
                        break;
                    }

                    System.out.println("Error while accepting connection: " + e.getMessage());
                }
            }

            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.close();
                }

                clientWriters.clear();
            }

            System.out.println("Server stopped successfully");
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    public static void broadcast(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }
}