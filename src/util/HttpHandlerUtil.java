package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHandlerUtil {
	
	public static String getHTML(String urlToRead) {
	      URL url;
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      try {
	         url = new URL(urlToRead);
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return result;
	   }

	public static final String URL_PRE_QUERY = "https://dictionary.cambridge.org/api/v1/dictionaries/american-english"
			+ "/search/first/?q=";
	public static final String URL_POST_QUERY = "&format=xml&_dc=1352703470685&page=1&start=0&limit=25";

	public static void main(String args[]) {
		getResponse("large");
	}

	public static String getResponse(String word) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL_PRE_QUERY + word + URL_POST_QUERY);

		request.setHeader("accessKey",
				""); //Its a key, which we can get when registering with cambridge apis
		request.setHeader("accept", "application/json");

		HttpResponse response;
		// Get the response
		BufferedReader rd;
		try {
			response = client.execute(request);
			rd = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			String line = new String();
			StringBuffer responseStringBuf = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				responseStringBuf.append(line);
			}
			return responseStringBuf.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getEntryUrlUsingApi(String word) {
		String response = getResponse(word);
		return null;
		//StringHandlerUtil.getStringBetweenPattern(response, <, endPattern)
	}
}
