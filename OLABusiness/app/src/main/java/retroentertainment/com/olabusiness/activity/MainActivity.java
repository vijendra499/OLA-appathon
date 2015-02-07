package retroentertainment.com.olabusiness.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retroentertainment.com.olabusiness.R;
import retroentertainment.com.olabusiness.Utils.BaseData;
import retroentertainment.com.olabusiness.fragments.RidePurposeFragment;
import retroentertainment.com.olabusiness.responseHelper.BookRide;
import retroentertainment.com.olabusiness.responseHelper.JacksonParser;


public class MainActivity extends AbstractBaseActivity implements OnMapReadyCallback {
    static final LatLng BANGalore = new LatLng(12.9715987, 77.59456269);
    MapFragment mMapFragment, mapFragment;
    GoogleMap map;
    private Button ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        ride = (Button)findViewById(R.id.ride_now_btn);
        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRidePurposeFragment();
            }
        });
    }

    private void startRidePurposeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.ride_purpose_fragment,new RidePurposeFragment()).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (map == null) {
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            map.addMarker(new MarkerOptions()
                    .position(BANGalore)
                    .title("Marker"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(BANGalore, 15));
            map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }
    }


   /* @Override
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
    }*/

    @Override
    public void onRequestComplete(int request_id, BaseData baseData) {
        Log.e("ANSH","activity onRequestComplete");
    }

    @Override
    public void onCancel(int request_id) {

    }

    @Override
    public void onError(int request_id, String errorMessage) {

    }
}
