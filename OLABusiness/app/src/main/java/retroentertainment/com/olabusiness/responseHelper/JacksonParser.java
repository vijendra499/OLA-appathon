package retroentertainment.com.olabusiness.responseHelper;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import retroentertainment.com.olabusiness.httpConnection.HttpRequestConstant;

/**
 * Created by ansh on 7/2/15.
 */
public class JacksonParser<T> {

    private Object reponse;
    public JacksonParser(Object reponse) {
        this.reponse = reponse;
    }

    public T parse(int requestCode) {
        T responseObject = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            switch (requestCode){
                case HttpRequestConstant.BOOK_CAB:
                     responseObject= (T) mapper.readValue((String)reponse,BookRide.class);
                    break;
                case HttpRequestConstant.GET_COUPONS:
                     responseObject = (T) mapper.readValue((String) reponse,CouponsListResponse.class);
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  responseObject;

    }
}
