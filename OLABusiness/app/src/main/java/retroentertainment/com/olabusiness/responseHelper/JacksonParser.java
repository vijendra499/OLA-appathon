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
public class JacksonParser {

    private Object reponse;
    public JacksonParser(Object reponse) {
        this.reponse = reponse;
    }

    public BaseData parse(int requestCode) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            switch (requestCode){
                case HttpRequestConstant.SEND_RIDE_PURPOSE:
                    SendRidePurposeResponse response=   mapper.readValue((byte[]) reponse,SendRidePurposeResponse.class);
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) reponse;
    }
}
