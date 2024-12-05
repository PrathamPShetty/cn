import java.io.*;
import java.net.*;

public class Server1 {
    public static void main(String[] args) {
        final int PORT = 4000; // Define the port number

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server ready for connection on port " + PORT);

            while (true) { // Allow the server to handle multiple client connections
                try (
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
                ) {
                    System.out.println("Client connected.");

                    // Read the requested file name
                    String fileName = input.readLine();
                    System.out.println("Client requested file: " + fileName);

                    File file = new File(fileName);

                    // Check if the file exists
                    if (!file.exists()) {
                        output.println("File not found");
                        System.out.println("File not found: " + fileName);
                    } else {
                        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                            System.out.println("Sending file contents to client...");
                            fileReader.lines().forEach(output::println); // Stream file contents to the client
                            output.println("EOF"); // Signal end of file transfer
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
