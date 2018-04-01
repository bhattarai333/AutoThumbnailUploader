import com.google.api.services.youtube.YouTube;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;

import static java.util.Comparator.reverseOrder;


/**
 * Originally created by Josh Bhattarai on 6/15/2017.
 */
class Organize {
    void organizePlaylist(ArrayList<VideoInfo> videoInfoArrayList, String apiKey, String clientID, String clientSecret, OverlayData od, String accessToken){
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?kind=video&part=snippet&access_token=" + accessToken;
        System.out.println(accessToken);

        videoInfoArrayList.sort(Comparator.comparingInt(VideoInfo::getSortingValue).reversed().thenComparingInt(VideoInfo::getSortingTiebreaker).reversed());
        int position = 0;
        for(VideoInfo v : videoInfoArrayList){
            v.playlistIndex = position;
            System.out.println(position + ": " + v);
            sendPlaylistUpdate(url,od.youtubePlaylistURL,v,position);
            position++;
        }
    }

    private void sendPlaylistUpdate(String url, String playlistURL, VideoInfo v, int position) {
        JSONObject body = new JSONObject();
        body.put("id",v.playlistItemID);
        JSONObject snippet = new JSONObject();
        snippet.put("playlistId", YouTubeStuff.getIDFromURL(playlistURL));
        JSONObject resourceID = new JSONObject();
        resourceID.put("kind","youtube#video");
        resourceID.put("videoId",v.videoID);
        snippet.put("resourceId",resourceID);
        body.put("snippet",snippet);
        body.put("position",position);


        HttpURLConnection con = createHTTPURLConnection(url);
        con.setRequestProperty("content-type","application/json");

        sendPut(con,"Smash4 Thumbnail Uploader",body.toString());
    }

    static String sendPut(HttpURLConnection con, String USER_AGENT, String body){
        //post with custom connection for authentication ect
        StringBuffer responseString = null;
        try {
            con.setRequestMethod("PUT");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(body.getBytes(StandardCharsets.UTF_8));
            int responseCode = con.getResponseCode();
            //System.out.println("POST Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                responseString = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                in.close();
                return responseString.toString();
            } else {
                return Integer.toString(responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL";
    }

    static HttpURLConnection createHTTPURLConnection(String url) {
        HttpURLConnection h = null;
        try {
            h = (HttpURLConnection) new URL(url).openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return h;
    }

}
