package simplers.webroulette;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;


/**
 * Created by ohad on 6/9/16.
 */
public class ContentProviders {

    public static String compileRegex(String url, String pattern){

        String resp = getURL(url);
        Pattern patt = Pattern.compile(pattern);
        Matcher matcher = patt.matcher(resp);
        matcher.find();
        //System.out.println();
        String rest = resp.substring(matcher.start(),matcher.end());
        return rest ;
    }

    private static Random rn = new Random();

    public static String getRandomUrl() {

        int numOfProviders = 13;
        int random = rn.nextInt(numOfProviders);
        String pattern;
        System.out.println(random);

//        random = 12;
        switch (random) {
            case 0:     //reddit
                return getRandomUrl();
//                return "https://m.reddit.com/r/random/";
            case 1:     //recipes
                return getRandomUrl();
//                return "http://www.whatsfordinner.net/Whats-for-dinner-recipes-Refresh.html";
            case 2:     //wiki
                return "https://en.wikipedia.org/wiki/Special:Random";

            case 3: //youtube
                Random rnx = new Random();
                random = rnx.nextInt(3998) + 1;
                System.out.println(random);
                String x = getURL("http://ytroulette.com/roulette.php?pos=" + random);
                try {
                    JSONObject s = (JSONObject) (new JSONTokener(x)).nextValue();
                    return "https://m.youtube.com/watch?v=" + (s.get("idVideo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return getRandomUrl();
                }
            case 4:
                String str= "http://kice.me/randomebay/";

                pattern = "\\b(http?|ftp|file)://www.ebay[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"; // matches <http://google.com>
                return compileRegex(str,pattern).replace("www", "m") ;
            case 5:
                return "http://9gag.com/random";
            case 6:
                String stramz= "http://thanland.com/projects/random-amazon/";

                pattern = "\\b(http?|ftp|file)://amzn[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"; // matches <http://google.com>
                return compileRegex(stramz,pattern);
            case 7:
                return "http://c.xkcd.com/random/mobile_comic/";
            case 9:
                return "http://www.uroulette.com/visit/rtnsp";
            case 10:
                return "http://www.randomwebsite.com/cgi-bin/random.pl";
            case 11:
                return "http://wordpress.com/next/";
            case 12:
                return getRandomUrl();
//                int[] ids = {1, 3, 7, 9, 10, 22, 23, 24};
//                int id = ids[rn.nextInt(ids.length)];
//                String resp = null;
//                try {
//                    resp = getURL("https://www.ted.com/surprise-me.json?minutes=5&rating_word_id="
//                            + Integer.toString(id));
//                    JSONObject tedResp = (JSONObject) (new JSONTokener(resp)).nextValue();
//                    return tedResp.getJSONArray("talks").getJSONObject(0).getString("canonical");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    System.out.println(resp);
//                    System.out.println(id);
//                    return getRandomUrl();
//                }
            default:
                System.out.println("Something weired happened: random=" + Integer.toString(random));
                return getRandomUrl();
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
