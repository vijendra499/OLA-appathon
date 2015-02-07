package retroentertainment.com.olabusiness.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import retroentertainment.com.olabusiness.R;
import retroentertainment.com.olabusiness.Utils.BaseData;
import retroentertainment.com.olabusiness.responseHelper.JacksonParser;


public class MainActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestComplete(int request_id, BaseData baseData) {
            JacksonParser res = (JacksonParser) baseData.getData();
    }

    @Override
    public void onCancel(int request_id) {

    }

    @Override
    public void onError(int request_id, String errorMessage) {

    }
}
