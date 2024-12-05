


import java.io.*;
import java.net.*;

public class Client1 {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "127.0.0.1"; // Change if connecting to a remote server
        final int SERVER_PORT = 4000; // Ensure this matches the server's port

        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader keyReader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.print("Enter the file name: ");
            String fileName = keyReader.readLine();
            out.println(fileName); // Send the file name to the server

            String line;
            while ((line = socketReader.readLine()) != null) {
                if (line.equals("File not found")) {
                    System.out.println("Error: File not found on server.");
                    break;
                }
                if (line.equals("EOF")) {
                    System.out.println("End of file received.");
                    break;
                }
                System.out.println(line); // Print each line of the file
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
