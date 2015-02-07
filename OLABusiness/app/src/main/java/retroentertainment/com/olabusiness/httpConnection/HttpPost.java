package retroentertainment.com.olabusiness.httpConnection;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.util.Log;

import retroentertainment.com.olabusiness.Utils.OlaConstant;

public class HttpPost {
	private HttpsURLConnection httpcon;
	private OutputStream os;
	private InputStream in;

	private StringBuffer json = null;
	
	private String url = null;
	private Object postData = null;

	private static final String TAG = "trotez";

	public HttpPost(String url , Object postData) {
		this.url = url;
		this.postData = postData;
	}

	public Object sendRequest(int type) {
		Log.d(TAG, "Post send Request-----------------------"+postData+"");
		try {
			
			httpcon = (HttpsURLConnection) ((new URL(OlaConstant.BASE_URL
					+ url).openConnection()));
			Log.d(TAG, "inside httppost sendrequest. url - " + OlaConstant.BASE_URL + url);
			/*HttpsURLConnection
			.setDefaultHostnameVerifier(new MyHostnameVerifier());*/
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestMethod("POST");
			
		

			httpcon.connect();
			
			if (postData != null) {
				setPostParams();
			}
			
	
			in = httpcon.getInputStream();
			switch (type) {
			/*case HttpRequestConstant.TYPE_BYTE:
				return null;
			case HttpRequestConstant.TYPE_STREAM:
				return null;
			case HttpRequestConstant.TYPE_READER:
				reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
				return reader;*/
			case HttpRequestConstant.TYPE_STRING_BUFFER:
				json = readStream(in);
				Log.d("123", "Login response json " + json);
				return json;
			default:
				break;
			}
			
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			httpcon.disconnect();
		}

		return null;
	}

	private void setPostParams() {
		byte[] outputBytes = null;
		String output = (String) postData;
		try {
			outputBytes = output.getBytes("UTF-8");
			os = new BufferedOutputStream(httpcon.getOutputStream());
			os.write(outputBytes);
			os.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private StringBuffer readStream(InputStream is) {
		StringBuilder sb = new StringBuilder();
			try {
				if (is != null) {
					byte[] b = new byte[8192];
					String test;
				for (int i; (i = is.read(b)) != -1;) {
					test = new String(b, 0, i);
					sb.append(test);
				}
			} 
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new StringBuffer(sb);
		}
	
}
