import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    
    public static void main(String[] args) throws IOException{

        try(
            Socket socket = new Socket("127.0.0.1",8888);
            PrintWriter sender = new PrintWriter(socket.getOutputStream(), true);  // send messages to the server via the output stream
            Scanner receiver = new Scanner(socket.getInputStream());   // read messages from the servers input stream
            Scanner scan = new Scanner(System.in);
        ) {
            while(true) {                             // keeps running until the server sends a message with the content "END."
                String message = receiver.nextLine();   //  receives a message from the server
                if(message.equals("END")){
                    break;
                }
                System.out.println(message);
                message = scan.nextLine();   // prompts the user to input a message
                sender.println(message);    // sends the user's message to the server

            }
        }
    }
}
