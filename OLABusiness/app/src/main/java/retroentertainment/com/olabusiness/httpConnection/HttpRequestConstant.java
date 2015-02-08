package retroentertainment.com.olabusiness.httpConnection;

/**
 * Created by ansh on 7/2/15.
 */
public class HttpRequestConstant {

    public static final java.lang.String REQUEST_ID = "REQUEST_ID";

    //Request type
    public static final int BOOK_CAB = 101;
    public static final int GET_COUPONS = 102;
    public static final int LIST_OFFERS = 103;

    //Urls to be hit
    public static final String BOOK_RIDE = "/booking";
    public static final String LIST_OFFERS_URL = "/offer/";

    // for what will be returned from server
    public static final int TYPE_STRING_BUFFER = 0;

    //Book cab APIS
    public static final String USER_ID = "user_id";
    public static final String SRC_LAT = "src_lat";
    public static final String SRC_LNG = "src_lng";
    public static final String DEST_LAT = "dest_lat";
    public static final String DEST_LNG = "dest_lng";
    public static final String CAT = "cat";

}
