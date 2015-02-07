package retroentertainment.com.olabusiness.responseHelper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.Deserializers;

import java.io.IOException;

import retroentertainment.com.olabusiness.Utils.BaseData;
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

        ObjectMapper mapper = new ObjectMapper();
        T response;
        try {
            switch (requestCode){
                case HttpRequestConstant.SEND_RIDE_PURPOSE:
                     response= (T) mapper.readValue((byte[]) reponse,SendRidePurposeResponse.class);
                    break;
                case HttpRequestConstant.GET_COUPONS:
                     response = (T) mapper.readValue((byte[]) reponse,Coupons.class);
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) reponse;
    }
}
