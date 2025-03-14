import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 9999;
    private static final int MAX_CLIENTS = 10; // Set the number of allowed clients
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private ServerSocket serverSocket;
    private int clientCount;

    public Server() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT, MAX_CLIENTS, InetAddress.getByName(SERVER_IP));
            System.out.println("Server started on " + SERVER_IP + ":" + SERVER_PORT);
            clientCount = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            while (clientCount < MAX_CLIENTS) {
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
