
import java.io.*;
import java.net.*;

public class WebServer {

	public static void main(String[] args) throws IOException {

		WebServer.startServer(9000);
	}

	public static void startServer(int port) throws IOException {
		ServerSocket Server = new ServerSocket(port, 10);
		System.out.println("Server Listening");

		while (true) {
			Socket newClient = Server.accept();
			Service client = new Service(newClient);
			client.start();
		}
	}

}
