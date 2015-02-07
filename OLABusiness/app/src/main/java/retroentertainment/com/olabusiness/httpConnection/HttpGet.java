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

import com.application.trotez.util.TrotezConstants;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class HttpGet {
	private static final String TAG = "trotez";

	private HttpsURLConnection httpcon;
	private OutputStream os;
	private InputStream in;

	private StringBuffer json = null;
	
	private String url = null;
	private Object getData = null;
	private JsonReader reader = null;
	
	public HttpGet(String url , Object getData) {
		this.url = url;
		this.getData = getData;
	}

	public Object sendRequest(int type) {
		Log.d(TAG, "Get send Request-----------------------");
		try {

			httpcon = (HttpsURLConnection) ((new URL(TrotezConstants.BASE_URL
					+ url).openConnection()));
			Log.d(TAG, "inside httpget sendrequest. url - " + TrotezConstants.BASE_URL + url);
			//httpcon.setDoOutput(true);
			HttpsURLConnection
			.setDefaultHostnameVerifier(new MyHostnameVerifier());
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestMethod("GET");
			
			httpcon.connect();
		    
			Log.d(TAG, "CODE :"+httpcon.getResponseCode());
			if (getData != null) {
				setGetParams();
			}
			in = httpcon.getInputStream();
			switch (type) {
			case HttpRequestConstant.TYPE_BYTE:
				return null;
			case HttpRequestConstant.TYPE_STREAM:
				return null;
			case HttpRequestConstant.TYPE_READER:
				reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
				return reader;
			case HttpRequestConstant.TYPE_STRING_BUFFER:
				json = readStream(in);
				Log.d("123", "Login response json " + json);
				return json;
			default:
				break;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			httpcon.disconnect();
		}

		/*BaseData response = new BaseData();
		response.isSuccess = true;
		response.sysMessage = json;
		response.hasDataForUI = true;*/
		// json = new Gson().fromJson(json, String.class);
		return null;
	}

	private void setGetParams() {
		byte[] outputBytes = null;
		String output = new Gson().toJson(getData);
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