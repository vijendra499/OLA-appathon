package retroentertainment.com.olabusiness.Utils;

import java.io.Serializable;

import retroentertainment.com.olabusiness.responseHelper.SendRidePurposeResponse;

/**
 * This class is used to store the sever response data.
 * 
 */
public class BaseData implements Serializable {
	private static final String TAG = BaseData.class.getSimpleName();

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

    public String responseData;

	/**
	 * true, if data available for UI
	 * 
	 */
	public int request_code;

}