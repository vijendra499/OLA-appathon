package retroentertainment.com.olabusiness.responseHelper;

import java.util.ArrayList;

/**
 * Created by ansh on 7/2/15.
 */
public class CouponsListResponse {

    private String status;

    private ArrayList<CouponModel> couponModels;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<CouponModel> getCouponModels() {
        return couponModels;
    }

    public void setCouponModels(ArrayList<CouponModel> couponModels) {
        this.couponModels = couponModels;
    }
}
