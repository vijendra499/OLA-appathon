package retroentertainment.com.olabusiness.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import retroentertainment.com.olabusiness.httpConnection.HttpListener;

/**
 * This activity is used for, checking availability of the network. Showing
 * progress dialog indicating operation is in process.
 * 
 * Any activity dealing with Http requests and responses can use this class as
 * parent.
 * 
 * This class implements HttpListener to get callback to the place from where it
 * is called.
 * 
 */
public abstract class AbstractBaseActivity extends Activity implements
        HttpListener {

	// constants used as messaging tags
	public final static int CODE_PRE_EXECUTE = 1001;
	public final static int CODE_POST_EXECUTE = 1002;
	public final static int CODE_REQUEST_COMPLETE = 1003;
	public final static int CODE_REQUEST_CANCEL = 1004;
	public final static int CODE_REQUEST_ERROR = 1005;
	
	public final static String MESSENGER = "messenger";
	public static final String RESPONSE_DATA = "response";
	private static final String TAG = TrotezConstants.LogTag;

	/**
	 * ProgressDialog Object.AbstractBaseActivity.this
	 */
	private ProgressDialog mProgress = null;
	/**
	 * Title of the progress dialog.
	 */
	private String mProgressTitle = null;

	/**
	 * if true show progress bar else hide
	 */
	private boolean mShowProgressBar = true;

	/**
	 * Executes the user request for given requested data
	 * 
	 * @param requestData
	 */
	protected void onExecute(final Bundle requestData) {
		Log.d(TAG, "called onExecute-------------------");
		final Messenger messenger = new Messenger(mRequestCallbackHandler);
		requestData.putParcelable(MESSENGER, messenger);
		//new ServerHandlerTask().execute(requestData);
		new ServerHandlerTask(AbstractBaseActivity.this).execute(requestData);
	}
	/**
	 * Executed once the request is completed. Dismissing the dialog once
	 * process is completed. Resource deallocation can be done here.
	 */
	public void onPostExecute() {
		if (mProgress != null && mProgress.isShowing()) {
			try {
				mProgress.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Can be done all primary initialisation. Launching the progress dialog to
	 * indicate operation is in progress. Resource allocation can be done here
	 */
	public void onPreExecute() {
	}

	/**
	 * Sets the title for the progress dialog.
	 * 
	 * @param title
	 */
	protected void setProgressTitle(final String title) {
		mProgressTitle = title;
	}

	protected void showProgressBar(final boolean flag) {
		this.mShowProgressBar = flag;
	}

	/**
	 * Handler to communicate from Main Thread.
	 */
	final private Handler mRequestCallbackHandler = new Handler() {

		@Override
		public void handleMessage(final Message msg) {
			Log.d(TAG, "inside handle message--------------");
			int requestId = -1;
			final Bundle extras = msg.getData();
			if (extras != null) {
				requestId = extras.getInt(HttpRequestConstant.REQUEST_ID);
			}

			switch (msg.what) {
			case CODE_PRE_EXECUTE:
				onPreExecute();
				break;

			case CODE_POST_EXECUTE:
				onPostExecute();
				break;

			case CODE_REQUEST_CANCEL:
				onCancel(requestId);
				break;

			case CODE_REQUEST_ERROR:
				final String errorMsg = extras
						.getString(RESPONSE_DATA);
				onError(requestId, errorMsg);

				break;

			case CODE_REQUEST_COMPLETE:
				Log.d(TAG, "Inside Request complete-----------------");
				final BaseData data = (BaseData) extras
						.getSerializable(RESPONSE_DATA);
				onRequestComplete(requestId, data);
				break;

			default:
				break;
			}

		}
	};

}
