package retroentertainment.com.olabusiness.httpConnection;

import android.os.Bundle;

import retroentertainment.com.olabusiness.requestData.SendRidePurposeKeyValues;

public class ConnectionUtil {

	public Object callServer(Bundle requestData){
		Object data = null;
		switch (requestData.getInt(HttpRequestConstant.REQUEST_ID)) {
		case HttpRequestConstant.SEND_RIDE_PURPOSE:
			
			SendRidePurposeKeyValues value = new SendRidePurposeKeyValues();
			//value.user_id = requestData.getString(HttpRequestConstant.USER_ID);
			//value.friends_list = requestData.getString(HttpRequestConstant.FRIENDS_LIST_ID);
			data = new HttpPost(HttpRequestConstant.SEND_RIDE_PURPOSE_URL ,value).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);
			break;
		/*case HttpRequestConstant.LOGIN_CALL:
			LoginKeyValues loginValues = new LoginKeyValues();
			loginValues.email = requestData.getString(HttpRequestConstant.LOGIN_UID);
			loginValues.password = requestData.getString(HttpRequestConstant.PASSWORD);
			data = new HttpPost(HttpRequestConstant.LOGIN_URL, loginValues).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);
			break;
		case HttpRequestConstant.FB_LOGIN_CALL :
			FBLoginKeyValues fbvalues = new FBLoginKeyValues();
			fbvalues.fb_accesstoken = requestData.getString(HttpRequestConstant.FB_TOKEN);
			data = new HttpPost(HttpRequestConstant.FB_URL, fbvalues).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);

			break;
		case HttpRequestConstant.USER_REGISTRATION_CALL :
			RegistrationKeyValues regValues = new RegistrationKeyValues();
			regValues.fname = requestData.getString(HttpRequestConstant.FIRST_NAME);
			regValues.lname = requestData.getString(HttpRequestConstant.LAST_NAME);
			regValues.email = requestData.getString(HttpRequestConstant.REG_EMAIL);
			regValues.password = requestData.getString(HttpRequestConstant.REG_PASSWORD);
			regValues.alt_email = requestData.getString(HttpRequestConstant.REG_ALT_EMAIL);
			
			data = new HttpPost(HttpRequestConstant.REGISTRATION_URL, regValues).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);
						
			break;
		case HttpRequestConstant.LANDING_PAGE_CALL:
			Log.d(TAG, "landing page call============================");
			data = new HttpGet(HttpRequestConstant.LANDING_URL, null).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);
			break;
		case HttpRequestConstant.PAST_TRIP_CALL:
			data = new HttpGet(HttpRequestConstant.PAST_TRIP_URL, null).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);
			break;
		case HttpRequestConstant.GET_NAME_CALL:
			Log.d(TAG, "get name call============================");
			data = new HttpGet(HttpRequestConstant.GET_NAME_URL, null).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);
			break;
		case HttpRequestConstant.CHANGE_PWD_CALL:
			Log.d(TAG, "cahngepassword calle===============");
			ChangePwdKeyValues chpwdValues = new ChangePwdKeyValues();
			chpwdValues.oldPassword = requestData.getString(HttpRequestConstant.OLD_PASSWORD);
			chpwdValues.newPassword = requestData.getString(HttpRequestConstant.NEW_PASSWORD);			
			data = new HttpPost(HttpRequestConstant.CHANGE_PWD_URL, chpwdValues).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);
			break;
		case HttpRequestConstant.PROFILE_DETAILS_CALL:
			Log.d(TAG, "te profile details called==============");
			data = new HttpGet(HttpRequestConstant.PROFILE_URL, null).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);
			break;
		case HttpRequestConstant.SUBSCRIBE_PREMIUM_CALL:
			Log.d(TAG, "subscriber call has made=========");
			data = new HttpGet(HttpRequestConstant.SUBSCRIBE_PRE_URL, null).sendRequest(HttpRequestConstant.TYPE_STRING_BUFFER);
			break;*/
			
		default:
			break;
		}
		return data;
	}
}
