import java.io.*;
import java.net.*;
import java.nio.file.*;

public class HttpServer {
    private String serverAddress = "localhost";
    private int port = 9999;
    private String webRoot = "./www";

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(serverAddress, port));
            System.out.println("Server started on " + serverAddress + ":" + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream stream = clientSocket.getOutputStream()) {

            String requestLine = reader.readLine();
            System.out.println("Received request: " + requestLine);

            String path = requestLine.split(" ")[1];

            if (path.equals("/")) {
                path = "pages/index.html";
            }
            else if (path.equals("/contact")) {
                path = "pages/contact.html";
            }
            else if (path.equals("/about")) {
                path = "pages/about.html";
            }
            else if (path.equals("/register")) {
                path = "pages/register.html";
            }
            else if (path.endsWith(".css")) {
                path = "styles/style.css";
            }
            else {
                path = "pages/index.html";
            }

            serveFile(stream, path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void serveFile(OutputStream stream, String filePath) throws IOException {
        File file = new File(webRoot, filePath);

        if (file.exists() && !file.isDirectory()) {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            String contentType;

            if (filePath.endsWith(".html")) {
                contentType = "text/html";
            } else if (filePath.endsWith(".css")) {
                contentType = "text/css";
            } else {
                contentType = "text/plain";
            }

            stream.write(("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "Content-Length: " + fileBytes.length + "\r\n" +
                    "\r\n").getBytes());

            stream.write(fileBytes);
        } else {
            String response = "<html><body><h1>404 Not Found</h1></body></html>";
            stream.write(("HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + response.length() + "\r\n" +
                    "\r\n" + response).getBytes());
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.start();
    }
}