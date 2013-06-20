package efficom.runningjoe.ws.userapi;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserApiPortType2 {
    public int register(String name, String email, String password) throws IOException, XmlPullParserException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);

        Object ret = UserApiService2.soap (
                "register",
                "register",
                params
            );

        try{
            return Integer.parseInt(ret.toString());
        }catch(Exception ex){
            return 0;
        }
    }

    public String errordescription(String lang,  int number) throws IOException, XmlPullParserException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("lang", lang);
        params.put("number", String.valueOf(number));

        Object ret = UserApiService2.soap (
                "errordescription",
                "errordescription",
                params
        );

        return ret.toString();
    }

    public int login(String name, String password) throws IOException, XmlPullParserException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("password", password);

        Object ret = UserApiService2.soap (
                "login",
                "login",
                params
        );

        return Integer.parseInt(ret.toString());
    }

    public int recordscore(String name, int scorevalue) throws IOException, XmlPullParserException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("scorevalue", String.valueOf(scorevalue));

        Object ret = UserApiService2.soap (
                "recordscore",
                "recordscore",
                params
        );

        try{
            return Integer.parseInt(ret.toString());
        }catch(Exception ex){
            return 0;
        }
    }

    public ArrayList<String> highscoresstring(int limit, int offset) throws IOException, XmlPullParserException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("limit", String.valueOf(limit));
        params.put("offset", String.valueOf(offset));

        SoapObject ret = (SoapObject)UserApiService2.soap (
                "highscoresstring",
                "highscoresstring",
                params
        );

        ArrayList<String> array = new ArrayList<String>();

        int i = 0;
        while (i < ret.getPropertyCount()){
            String item =  ret.getProperty(i).toString();
            array.add(item);
            i++;
        }

        return array;
    }
}
