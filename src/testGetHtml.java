import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.URL;


import org.junit.Test;

public class testGetHtml {

	@Test
	public void test() {
		try {
			URL url = new URL("http://127.0.0.1:9000");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() != 200) {
				fail(conn.getResponseMessage());
			}
					} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
