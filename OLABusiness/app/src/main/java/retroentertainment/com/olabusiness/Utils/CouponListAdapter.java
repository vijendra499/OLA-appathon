package retroentertainment.com.olabusiness.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import retroentertainment.com.olabusiness.R;
import retroentertainment.com.olabusiness.responseHelper.CouponModel;
import retroentertainment.com.olabusiness.responseHelper.CouponsListResponse;

/**
 * Created by anuruddh.nayak on 2/8/2015.
 */
public class CouponListAdapter extends BaseAdapter {

   private CouponsListResponse response;
    Context mContext;
    public CouponListAdapter(Context context, CouponsListResponse res){
        this.response = res;
        mContext = context;

    }
    @Override
    public int getCount() {
        return response.getCouponModels().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myViewHolder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.coupons_list_item, null);
            myViewHolder = new ViewHolder();
            convertView.setTag(myViewHolder);

        }else{
            myViewHolder = (ViewHolder) convertView.getTag();
        }

        myViewHolder.title = (TextView) convertView.findViewById(R.id.textView);
        myViewHolder.discount  =(TextView) convertView.findViewById(R.id.textView2);
        myViewHolder.avail = (TextView) convertView.findViewById(R.id.textView3);

        myViewHolder.title.setText(response.getCouponModels().get(position).getBusiness().getName());
        myViewHolder.discount.setText(response.getCouponModels().get(position).getTitle());
        myViewHolder.avail.setText("Avail");

        return (convertView);

    }

    private class ViewHolder{
        TextView title, discount,avail;
    }
}
