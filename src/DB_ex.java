import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;


public class DB_ex {

    public static String makeGETRequest(String urlName) {
        //Get JSON object using the API
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        try {
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
            conn.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ArrayList<JValueSet> parseJSON(String jsonString, String to_look_for) {
        //Parse JSON to extract the necessary values
        ArrayList<JValueSet> data = new ArrayList<JValueSet>();
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                float lastValue = curObject.getFloat("LastValue");
                float maxValue = curObject.getFloat("Max_Value");
                String location = curObject.getString("Location");
                data.add(new JValueSet(maxValue, location, to_look_for, lastValue));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}