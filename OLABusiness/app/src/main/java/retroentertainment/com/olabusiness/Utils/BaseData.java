package retroentertainment.com.olabusiness.Utils;

import java.io.Serializable;

/**
 * This class is used to store the sever response data.
 * 
 */
public class BaseData<T> implements Serializable {
	private static final String TAG = BaseData.class.getSimpleName();

    private T responseData;
	/**
	 * true, if data is fetched successful
	 * 
	 */
	public boolean isSuccess;
	/**
	 * holds the server error message which  occurred while fetching the data.
	 */
	public String errorMessage;

	/**
	 * true, if data available for UI
	 * 
	 */
	public boolean hasDataForUI;
	

	/**
	 * true, if data available for UI
	 * 
	 */
	public int request_code;

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }
}