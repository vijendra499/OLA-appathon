package retroentertainment.com.olabusiness.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import retroentertainment.com.olabusiness.R;
import retroentertainment.com.olabusiness.Utils.BaseData;
import retroentertainment.com.olabusiness.httpConnection.HttpRequestConstant;
import retroentertainment.com.olabusiness.responseHelper.BookRide;
import retroentertainment.com.olabusiness.responseHelper.JacksonParser;

/**
 * Created by ansh on 8/2/15.
 */
public class RidePurposeFragment extends AbstractHttpFragment {


    private Spinner sp;
    private Button ride_final;
    private int rideCategoryCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ride_layout, null);

        sp = (Spinner) view.findViewById(R.id.spinner);
        ride_final = (Button) view.findViewById(R.id.ride_final);

       sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               rideCategoryCode = i;
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        ride_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookCab();
            }
        });
        return view;
    }

    private void BookCab() {
        Bundle bookCab = new Bundle();
        bookCab.putInt(HttpRequestConstant.REQUEST_ID, HttpRequestConstant.BOOK_CAB);
        bookCab.putString(HttpRequestConstant.USER_ID, "1");
        bookCab.putString(HttpRequestConstant.SRC_LAT, "12.9128118");//getArguments().getString(HttpRequestConstant.SRC_LAT));
        bookCab.putString(HttpRequestConstant.SRC_LNG, "77.6092188");//getArguments().getString(HttpRequestConstant.SRC_LNG));
        bookCab.putString(HttpRequestConstant.DEST_LAT, "12.9751740");//getArguments().getString(HttpRequestConstant.DEST_LAT));
        bookCab.putString(HttpRequestConstant.DEST_LNG, "77.60799470");//getArguments().getString(HttpRequestConstant.DEST_LNG));
        bookCab.putString(HttpRequestConstant.CAT, String.valueOf(rideCategoryCode));
        onExecute(bookCab);

    }

    @Override
    public void onRequestComplete(int request_id, BaseData baseData) {
        Log.e("Ansh","onRequestComplete : "+baseData.responseData+"");
        BookRide res = new JacksonParser<BookRide>(baseData.responseData).parse(baseData.request_code);
        if(res.getStatus().equalsIgnoreCase("OK")){
            showBookingSuccessDialog();
        }
    }

    private void showBookingSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Booking Confirm");
        builder.setMessage("Your booking is confirmed.Avail your coupons from menu");
        builder.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                getActivity().getFragmentManager().beginTransaction().detach(RidePurposeFragment.this).commit();
            }
        });
       AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCancel(int request_id) {

    }

    @Override
    public void onError(int request_id, String errorMessage) {

    }
}
