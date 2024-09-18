import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

public class Server extends WebSocketServer {


    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection from " + conn.getRemoteSocketAddress());
        conn.send("Welcome to the chat server!"); // Send a welcome message
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received: " + message);
        // Broadcast the message to all connected clients except the sender
        for (WebSocket client : getConnections()) {
            if (client != conn) {  // Avoid sending back to the sender
                client.send("Client: " + message);
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {

    }

    public static void main(String[] args) {
        int port = 7777; // Port for WebSocket server
        Server server = new Server(port);
        server.start();
        System.out.println("ChatServer started on port: " + port);
    }
}
