import java.io.*;
import java.net.*;
import java.util.*;

public class Service extends Thread {

	Socket connection = null;

	public Service(Socket client) {
		this.connection = client;
	}

	public void run() {
		BufferedReader in = null;
		final String notFound = "HTTP/1.1 404 Not Found\r\n"
				+ "Content-type: text/html\r\n\r\n"
				+ "<html><head></head><body><h1>HTTP 404 FILE NOT FOUND</h1><br>"
				+ " not found</body></html>\n";
		DataOutputStream out = null;

		String getLine = null;
		String request = null;
		String requestMethod = null;

		try {

			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			out = new DataOutputStream(connection.getOutputStream());
			if ((getLine = in.readLine()) == null)
				return;

			StringTokenizer token = new StringTokenizer(getLine);
			if (token.hasMoreElements()) {
				requestMethod = token.nextToken();
				if (token.hasMoreElements())
					request = token.nextToken();
				else
					return;
			} else
				return;

			if (requestMethod.equalsIgnoreCase("POST")) {
				System.out.println("POST");
				do {
					getLine = in.readLine();
					if (getLine.contains("Content-Type: multipart/form-data") == false)
						continue;

					String post_bound = getLine.split("boundary=")[1];
					while (true) {
						getLine = in.readLine();
						if (getLine.indexOf("--" + post_bound) != -1) {
							request = in.readLine().split("filename=")[1]
									.replaceAll("\"", "");
							String[] filelist = request.split("\\" + "/");
							int len = filelist.length - 1;
							request = filelist[len];
							System.out.println("file - " + request);
							break;
						}
					}

					in.readLine();
					in.readLine();
					PrintWriter output = new PrintWriter(request);
					String last = in.readLine();
					getLine = in.readLine();

					while (true) {
						if (getLine.equals("--" + post_bound + "--")) {
							output.print(last);
							break;
						} else {
							output.println(last);
						}
						last = getLine;
						getLine = in.readLine();
					}

					out.writeBytes("HTTP/1.1 200 Uploaded\r\n"
							+ "Content-type: text/html\r\n\r\n"
							+ "<html><head></head><body><h1>POST SUCCESS: File Uploaded</h1><br>"
							+ request + " not found</body></html>\n");
					out.close();

					output.close();

				} while (in.ready());
			}

			else {
				System.out.println("GET request");

				try {
					if (request.endsWith("/"))
						request += "index.html";

					while (request.indexOf("/") == 0)
						request = request.substring(1);
					if (request.indexOf("..") >= 0 || request.indexOf(':') >= 0
							|| request.indexOf('|') >= 0)
						throw new FileNotFoundException();

					if (new File(request).isDirectory()) {
						request = request.replace('\\', '/');
						out.writeBytes("HTTP/1.1 301 Moved Permanently\r\n"
								+ "Location: /" + request + "/\r\n\r\n");
						out.close();
						return;
					}

					System.out.println("Requested : " + request);

					String mime = "text/plain";
					if (request.endsWith(".html")) {
						mime = "text/html";
					} else if (request.endsWith(".jpg")) {
						mime = "image/jpeg";
					} else if (request.endsWith(".gif")) {
						mime = "image/gif";
					}

					out.writeBytes("HTTP/1.1 200 OK\r\n" + "Content-type: "
							+ mime + "\r\n\r\n");

					if (requestMethod.equalsIgnoreCase("GET")) {
						System.out.println("GET");
						InputStream fileStream = new FileInputStream(
								"/home/shiza/html/" + request);
						byte[] a = new byte[4096];
						int n;
						while ((n = fileStream.read(a)) > 0) {
							out.write(a, 0, n);
						}
						fileStream.close();
					}
					out.close();
				} catch (FileNotFoundException x) {
					out.writeBytes(notFound);
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
