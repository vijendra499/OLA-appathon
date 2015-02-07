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


        ObjectMapper mapper = new ObjectMapper();

        T response;
        try {
            switch (requestCode){
                case HttpRequestConstant.BOOK_CAB:
                     response= (T) mapper.readValue((byte[]) reponse,BookRide.class);
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
