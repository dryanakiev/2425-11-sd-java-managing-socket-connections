import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 9999;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private BufferedReader buffer;

    public Client() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            buffer = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connected to the server.");
            System.out.println("Server: " + reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread listener = new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println("Server: " + serverMessage);
                }
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        });
        listener.start();

        try {
            String userMessage;
            System.out.print("Enter message: ");
            while ((userMessage = buffer.readLine()) != null) {
                System.out.print("Enter message: ");
                writer.println(userMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
