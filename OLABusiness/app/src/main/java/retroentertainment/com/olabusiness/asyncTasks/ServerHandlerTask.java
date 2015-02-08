package retroentertainment.com.olabusiness.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import retroentertainment.com.olabusiness.Utils.BaseData;
import retroentertainment.com.olabusiness.activity.AbstractBaseActivity;
import retroentertainment.com.olabusiness.httpConnection.ConnectionUtil;
import retroentertainment.com.olabusiness.httpConnection.HttpRequestConstant;


public class ServerHandlerTask extends AsyncTask<Bundle, Void, BaseData> {

    private static final String TAG = ServerHandlerTask.class.getSimpleName();
    private Messenger mRequestMessenger = null;
    private int request_code = -1;
    private Context context = null;

    public ServerHandlerTask(Context con) {
        this.context = con;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(BaseData result) {
        super.onPostExecute(result);

        Log.d(TAG, "OnPost EXE :" + result);
        if (result != null) {
            successCallback(result);
        } else {
            errorCallBack();
        }
    }

    @Override
    protected BaseData doInBackground(Bundle... params) {
        if (params[0] != null) {
            Bundle bundle = params[0];
            request_code = bundle.getInt(HttpRequestConstant.REQUEST_ID);
            mRequestMessenger = bundle
                    .getParcelable(AbstractBaseActivity.MESSENGER);
            Object responseReader = new ConnectionUtil()
                    .callServer(params[0]);
            if (responseReader != null) {
                return parseData(request_code, responseReader);
            }
        }
        return null;
    }

    private BaseData parseData(int code, Object responseReader) {
        BaseData data = null;
        switch (code) {
            case HttpRequestConstant.BOOK_CAB:
                data = new BaseData();
                data.hasDataForUI = false;
                data.isSuccess = true;
                data.responseData = ( String )responseReader.toString();
                data.request_code = request_code;
                break;
            case HttpRequestConstant.GET_COUPONS:
                break;
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
            case HttpRequestConstant.BOOK_CAB:
                errMsg = "Error !Please Try again";
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

    private void successCallback(BaseData result) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        switch (request_code) {
            case -1:
                break;
            case HttpRequestConstant.BOOK_CAB:
                if (result.isSuccess) {
                    bundle.putSerializable(AbstractBaseActivity.RESPONSE_DATA, result);
                    msg.what = AbstractBaseActivity.CODE_REQUEST_COMPLETE;
                    msg.setData(bundle);
                } else {
                    msg.what = AbstractBaseActivity.CODE_REQUEST_ERROR;
                    msg.setData(bundle);
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

