package simplers.webroulette;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;


/**
 * Created by ohad on 6/9/16.
 */
public class ContentProviders {
    public static String getRandomUrl() {

        int numOfProviders = 5;
        Random rn = new Random();
        Integer random = rn.nextInt(numOfProviders);
        System.out.println(random);

        switch (random) {
            case 0:     //reddit
                return "https://m.reddit.com/r/random/";
            case 1:     //ebay
                return "http://kice.me/randomebay/";
            case 2:     //recipes
                return "http://www.whatsfordinner.net/Whats-for-dinner-recipes-Refresh.html";
            case 3:     //wiki
                return "https://en.wikipedia.org/wiki/Special:Random";

            case 4: //youtube
                Random rnx = new Random();
                random = rnx.nextInt(3999) + 1;
                System.out.println(random);
                String x = getURL("http://ytroulette.com/roulette.php?pos=" + random);
                try {
                    JSONObject s = (JSONObject) (new JSONTokener(x)).nextValue();
                    return "https://m.youtube.com/watch?v=" + (s.get("idVideo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            default:
                return "http://lmgtfy.com/agsdgasd";

        }
    }

    public static String getURL(String address) {
        try {

            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//            conn.setRequestProperty("User-Agent", "BitSafe App");
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String resp = "";
            while ((line = rd.readLine()) != null)
                resp += line;
            rd.close();
            conn.disconnect();
            return resp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
