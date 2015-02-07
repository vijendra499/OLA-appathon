package retroentertainment.com.olabusiness.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.application.trotez.data.BaseData;
import com.application.trotez.httpConnection.ConnectionUtil;
import com.application.trotez.httpConnection.HttpRequestConstant;
import com.application.trotez.parser.PastItinaryParser;
import com.application.trotez.parser.ProfileDetailsParser;
import com.application.trotez.parser.ShareParser;
import com.application.trotez.util.AbstractBaseActivity;
import com.application.trotez.util.AbstractHttpFragment;
import com.application.trotez.util.TrotezConstants;
import com.application.trotez.util.TrotezUtils;

public class ServerHandlerTask extends AsyncTask<Bundle, Void, BaseData<T>> {

    private static final String TAG = ServerHandlerTask.class.getSimpleName();
    private Messenger mRequestMessenger = null;
	private int request_code = -1;
	private Context context = null;
	public ServerHandlerTask(Context con){
		this.context = con;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(BaseData<T> result) {
		super.onPostExecute(result);
		
		Log.d(TAG, "OnPost EXE :"+result);
		if (result != null) {
			successCallback(result);
		}else{
			errorCallBack();
		}
	}

	@Override
	protected BaseData<T> doInBackground(Bundle... params) {
		if (params[0] != null) {
			Bundle bundle = params[0];
			request_code = bundle.getInt(HttpRequestConstant.REQUEST_ID);
			mRequestMessenger = bundle
					.getParcelable(AbstractBaseActivity.MESSENGER);
			if (TrotezUtils.isInternetAvailable()) {
				Object responseReader = new ConnectionUtil()
						.callServer(params[0]);
				if (responseReader != null) {
					return parseData(request_code, responseReader);
				}
			} else {
				return parseDataFromFiles(request_code);
			}
		}
		return null;
	}

	private BaseData parseData(int code, Object responseReader) {
		BaseData data = null;
		switch (code) {
		case HttpRequestConstant.SHARE_CALL:
			data = new ShareParser(responseReader).parse();
			break;
		case HttpRequestConstant.FB_LOGIN_CALL:
			data = new BaseData();	
			data.request_code = HttpRequestConstant.FB_LOGIN_CALL;
			data.isSuccess = true;
			break;
		case HttpRequestConstant.LOGIN_CALL :
			Log.d(TAG, "the login is happened successfully");
			data = new BaseData();
			data.request_code = HttpRequestConstant.LOGIN_CALL;
			data.isSuccess = true;			
			data.sysMessage = responseReader.toString();
			break;
		case HttpRequestConstant.USER_REGISTRATION_CALL:
			Log.d(TAG, "the registraion is happened successfully");
			data = new BaseData();
			data.request_code = HttpRequestConstant.USER_REGISTRATION_CALL;
			data.isSuccess = true;			
			data.sysMessage = responseReader.toString();
			break;	
		case HttpRequestConstant.LANDING_PAGE_CALL:
			data = new BaseData();
			data.isSuccess = true;
			data.sysMessage = (String)responseReader.toString();
			TrotezUtils.writeToFile(data.sysMessage,context.getFilesDir().getPath().toString()+TrotezConstants.LANDING_PAGE_DB);
			break;
		case HttpRequestConstant.GET_NAME_CALL:
			data = new BaseData();
			data.isSuccess = true;
			data.sysMessage = (String)responseReader.toString();
			break;

		case HttpRequestConstant.PAST_TRIP_CALL:
			Log.d(TAG, "past data :"+responseReader.toString());
			/*try {
				//pastTripJsonObj = new JSONObject(responseReader.toString());
				TrotezUtils.writeToFile(responseReader.toString(),context.getFilesDir().getPath().toString()+TrotezConstants.PAST_TRIP_DB);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			TrotezUtils.writeToFile(responseReader.toString(),context.getFilesDir().getPath().toString()+TrotezConstants.PAST_TRIP_DB);
			data = new PastItinaryParser(responseReader).parse();
			break;
		case HttpRequestConstant.CHANGE_PWD_CALL:
			Log.d(TAG, "change pwd call====="+responseReader.toString());
			data = new BaseData();
			data.request_code = HttpRequestConstant.CHANGE_PWD_CALL;
			data.isSuccess = true;			
			data.sysMessage = responseReader.toString();
			break;
		case HttpRequestConstant.PROFILE_DETAILS_CALL:
			Log.d(TAG, "profile details calls==================="+responseReader.toString());
			TrotezUtils.writeToFile(responseReader.toString(), context.getFilesDir().getPath().toString()+TrotezConstants.PROFILE_DB);
			if(responseReader.toString().equals("NOT_LOGGED_IN")){
				data = new BaseData();
				data.request_code = HttpRequestConstant.PROFILE_DETAILS_CALL;
				data.isSuccess = true;			
				data.sysMessage = responseReader.toString();
			}else{
				data = new ProfileDetailsParser(responseReader).parse();
			}
			break;
		case HttpRequestConstant.DELETE_ITINERARY_CALL:
			Log.d(TAG, "dlete itenaray details calls==================="+responseReader.toString());
			
			/*if(responseReader.toString().equals("NOT_LOGGED_IN")){
				data = new BaseData();
				data.request_code = HttpRequestConstant.DELETE_ITINERARY_CALL;
				data.isSuccess = true;			
				data.sysMessage = responseReader.toString();
			}else{
				data = new ProfileDetailsParser(responseReader).parse();
			}*/
			break;
		default:
			break;
		}
		return data;
	}

	private BaseData parseDataFromFiles(int code) {
		StringBuffer buff = null;
		BaseData data = null;
		switch (code) {
		case HttpRequestConstant.LANDING_PAGE_CALL:
			data = new BaseData();
			data.isSuccess = true;
			data.sysMessage = TrotezUtils.readFromFile(context.getFilesDir().getPath().toString()+TrotezConstants.LANDING_PAGE_DB);
			break;
		case HttpRequestConstant.PAST_TRIP_CALL:
			 buff = new StringBuffer(TrotezUtils.readFromFile(context
					.getFilesDir().getPath().toString()
					+ TrotezConstants.PAST_TRIP_DB));
			data = new PastItinaryParser(buff).parse();
			break;
		case HttpRequestConstant.PROFILE_DETAILS_CALL:
			 buff = new StringBuffer(
					TrotezUtils.readFromFile(context.getFilesDir().getPath()
							.toString()
							+ TrotezConstants.PROFILE_DB));
				data = new ProfileDetailsParser(buff).parse();
		default:
			break;
		}
		return data;
	}

		
	
	private void errorCallBack() {
		Message msg = new Message();
		Bundle bundle = new Bundle();
		String errMsg = null;
		bundle.putInt(HttpRequestConstant.REQUEST_ID, request_code);
		switch (request_code) {
		case -1:
			break;
		case HttpRequestConstant.SHARE_CALL:
			errMsg = "Sharing failed";
			bundle.putString(AbstractBaseActivity.RESPONSE_DATA, errMsg);
			msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			msg.setData(bundle);
			break;
		case HttpRequestConstant.FB_LOGIN_CALL:
			errMsg = "Login failed";
			bundle.putString(AbstractBaseActivity.RESPONSE_DATA, errMsg);
			msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			msg.setData(bundle);
			break;
		case HttpRequestConstant.USER_REGISTRATION_CALL :
			errMsg = "Registration Failed";
			bundle.putString(AbstractBaseActivity.RESPONSE_DATA, errMsg);
			msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			msg.setData(bundle);
			break;
		case HttpRequestConstant.LOGIN_CALL:
			errMsg = "Login Failed";
			bundle.putString(AbstractBaseActivity.RESPONSE_DATA, errMsg);
			msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			msg.setData(bundle);
			break;
		case HttpRequestConstant.CHANGE_PWD_CALL:
			errMsg = "changepassword failed";
			bundle.putString(AbstractBaseActivity.RESPONSE_DATA, errMsg);
			msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			msg.setData(bundle);
			break;
		case HttpRequestConstant.DELETE_ITINERARY_CALL:
			errMsg = "delte itinerary failed";
			bundle.putString(AbstractBaseActivity.RESPONSE_DATA, errMsg);
			msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			msg.setData(bundle);
			break;
		default:
			return;
		}
		try {
			mRequestMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void successCallback(BaseData<T> result) {
		Message msg = new Message();
		Bundle bundle = new Bundle();
		switch (result.request_code) {
		case -1:
			break;
		case HttpRequestConstant.SHARE_CALL:
			
			if (result.isSuccess) {
				bundle.putSerializable(AbstractBaseActivity.RESPONSE_DATA, result);
				msg.what = AbstractBaseActivity.CODE_REQUEST_COMPLETE;
				msg.setData(bundle);
			}else{
				msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			}
			break;
		case HttpRequestConstant.FB_LOGIN_CALL:
			if (result.isSuccess) {
			
				result.request_code = request_code;
				bundle.putSerializable(AbstractBaseActivity.RESPONSE_DATA, result);
				msg.what = AbstractBaseActivity.CODE_REQUEST_COMPLETE;
				msg.setData(bundle);
			}else{
				msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			}
			break;
		case HttpRequestConstant.LOGIN_CALL :
			if (result.isSuccess) {		
				result.request_code = request_code;
				bundle.putSerializable(AbstractHttpFragment.RESPONSE_DATA, result);
				msg.what = AbstractHttpFragment.CODE_REQUEST_COMPLETE;
				msg.setData(bundle);
			}else{
				msg.what = AbstractHttpFragment.CODE_REQUEST_ERROR;
			}
			break;
		case HttpRequestConstant.USER_REGISTRATION_CALL : 
			if (result.isSuccess) {	
				
				result.request_code = request_code;
				bundle.putSerializable(AbstractHttpFragment.RESPONSE_DATA, result);
				msg.what = AbstractHttpFragment.CODE_REQUEST_COMPLETE;
				msg.setData(bundle);
			}else{
				msg.what = AbstractHttpFragment.CODE_REQUEST_ERROR;
			}
			break;
		case HttpRequestConstant.LANDING_PAGE_CALL:
			if(result.isSuccess){
				result.request_code = request_code;
				bundle.putSerializable(AbstractBaseActivity.RESPONSE_DATA, result);
				msg.what = AbstractBaseActivity.CODE_REQUEST_COMPLETE;
				msg.setData(bundle);
			}else{
				msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			}
			break;
		case HttpRequestConstant.PAST_TRIP_CALL:
			if(result.isSuccess){
				result.request_code = request_code;
				bundle.putSerializable(AbstractBaseActivity.RESPONSE_DATA, result);
				msg.what = AbstractBaseActivity.CODE_REQUEST_COMPLETE;
				msg.setData(bundle);
			}else{
				msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
			}
			break;
		case HttpRequestConstant.CHANGE_PWD_CALL:
			if (result.isSuccess) {	
				
				result.request_code = request_code;
				bundle.putSerializable(AbstractHttpFragment.RESPONSE_DATA, result);
				msg.what = AbstractHttpFragment.CODE_REQUEST_COMPLETE;
				msg.setData(bundle);
			}else{
				msg.what = AbstractHttpFragment.CODE_REQUEST_ERROR;
			}
			break;
		case HttpRequestConstant.PROFILE_DETAILS_CALL:
			if (result.isSuccess) {				
				result.request_code = request_code;
				bundle.putSerializable(AbstractHttpFragment.RESPONSE_DATA, result);
				msg.what = AbstractHttpFragment.CODE_REQUEST_COMPLETE;
				msg.setData(bundle);
			}else{
				msg.what = AbstractHttpFragment.CODE_REQUEST_ERROR;
			}
			break;
		case HttpRequestConstant.DELETE_ITINERARY_CALL:
			if (result.isSuccess) {				
				result.request_code = request_code;
				bundle.putSerializable(AbstractHttpFragment.RESPONSE_DATA, result);
				msg.what = AbstractHttpFragment.CODE_REQUEST_COMPLETE;
				msg.setData(bundle);
			}else{
				msg.what = AbstractHttpFragment.CODE_REQUEST_ERROR;
			}
			break;
		default:
			Log.d(TAG, "This should not happen ");
			return;
		}
		try {
			mRequestMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
