import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static List<User> users = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        users.add(new Student("stefi","1234", "381222028","123456789"));
        users.add(new Student("nikita", "3434", "381222002", "987654321"));
        users.add(new Teacher("sasho", "2222", "sahso@tu-sofia.bg"));
        users.add(new Admin("pepi", "9090"));
        try (
            ServerSocket server = new ServerSocket(8888);    // the try block ensures that the ServerSocket and ExecutorService resources are properly closed when the block is exited
            ExecutorService pool = Executors.newFixedThreadPool(5);
        ) {
            while (true) {
                 /**
             * Accepting a new connection
             */
                Socket socket = server.accept(); // When a client connects, it accepts the connection and creates a Socket object to communicate with the client.
                
                /**
             * Creating a new thread for the connection
             */
                pool.execute(new UserThread(socket));
                System.out.println("client is connected");
            }
        }
    }

    public static User getUser(String username, String password) {
        for(User user : users) {
            if (user.checkPassword(password) && user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
