package retroentertainment.com.olabusiness.Utils;

import java.io.Serializable;

/**
 * This class is used to store the sever response data.
 * 
 */
public class BaseData<T> implements Serializable {
	private static final String TAG = BaseData.class.getSimpleName();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 *for temporary purpose for Past trips 
	 * 
	 */
//	public ArrayList<PastTripData> pastTrips = new ArrayList<PastTripData>();
	/***
	 * for storing the profile information
	 */
//	public ProfileData profileData = new ProfileData();
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
	 * holds the server system message.
	 */
	public String sysMessage;
	
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

}