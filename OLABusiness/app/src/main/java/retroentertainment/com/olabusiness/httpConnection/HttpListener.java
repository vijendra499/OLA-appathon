package retroentertainment.com.olabusiness.httpConnection;

import com.application.trotez.data.BaseData;


/**
 * Listener used in handling the HTTP request and responses. All the Method will
 * be running on the UI Thread
 * 
 */
public interface HttpListener {

	/**
	 * Called when request execution is completed successfully.
	 * 
	 * @param request_id
	 * @param data
	 */
	void onRequestComplete(int request_id, BaseData baseData);

	/**
	 * Called when the request is cancelled.
	 * 
	 * @param request_id
	 */
	void onCancel(int request_id);

	/**
	 * Called when the error is incurred on executing the request.
	 * 
	 * @param request_id
	 * @param errorMessage
	 */
	void onError(int request_id, String errorMessage);

	/**
	 * Called when the HttpRequest is completed
	 */
	void onPostExecute();

	/**
	 * this method will called before httprequest start
	 */
	void onPreExecute();

}
