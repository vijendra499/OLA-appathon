package retroentertainment.com.olabusiness.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import retroentertainment.com.olabusiness.R;
import retroentertainment.com.olabusiness.Utils.BaseData;
import retroentertainment.com.olabusiness.Utils.CouponListAdapter;
import retroentertainment.com.olabusiness.httpConnection.HttpRequestConstant;
import retroentertainment.com.olabusiness.responseHelper.CouponsListResponse;
import retroentertainment.com.olabusiness.responseHelper.JacksonParser;

/**
 * Created by ansh on 8/2/15.
 */
public class OlaCouponsActivity extends AbstractBaseActivity{


    ListView listView = null;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ola_coupons);
        listView = (ListView) findViewById(R.id.listView);
        bar = (ProgressBar)findViewById(R.id.pbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bar.setVisibility(View.VISIBLE);
        Bundle bun = new Bundle();
        bun.putInt(HttpRequestConstant.REQUEST_ID, HttpRequestConstant.LIST_OFFERS);
        onExecute(bun);

    }

    @Override
    public void onRequestComplete(int request_id, BaseData baseData) {
        if(bar != null && bar.isShown()){
            bar.setVisibility(View.GONE);
        }
        CouponsListResponse response = new JacksonParser<CouponsListResponse>(baseData.responseData).parse(baseData.request_code);
        CouponListAdapter adapter = new CouponListAdapter(this,response);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCancel(int request_id) {

    }

    @Override
    public void onError(int request_id, String errorMessage) {

    }
}
