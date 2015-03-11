import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;


public class fourOfourTest {

	@Test
	public void test() {
		
		try {
			URL url = new URL("http://127.0.0.1:9000/blahhh");
			HttpURLConnection conn =     (HttpURLConnection) url.openConnection();

			if (conn.getResponseCode() != 404) {
				fail(conn.getResponseMessage());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

}
