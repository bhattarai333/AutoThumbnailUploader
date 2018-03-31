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
import java.util.Collections;
import java.util.Comparator;

/**
 * Originally created by Josh Bhattarai on 6/15/2017.
 */
class Organize {
    void organizePlaylist(ArrayList<VideoInfo> videoInfoArrayList, String apiKey, String clientID, String clientSecret, OverlayData od, String accessToken){
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?kind=video&part=snippet";
        sortVideos(videoInfoArrayList);
        for(VideoInfo v : videoInfoArrayList){
            String videoID = v.videoID;
            int position = 0;
            sendPlaylistUpdate(url,od,position);
        }
    }

    private void sendPlaylistUpdate(String url, OverlayData od, int position) {
        JSONObject body = new JSONObject();
        body.put("id",od.playlistItemID);
        JSONObject snippet = new JSONObject();
        snippet.put("playlistId", YouTubeStuff.getIDFromURL(od.youtubePlaylistURL));
        JSONObject resourceID = new JSONObject();
        resourceID.put("kind","youtube#video");
        resourceID.put("videoId",od.videoID);
        snippet.put("resourceId",resourceID);
        body.put("snippet",snippet);
        body.put("position",position);

        System.out.println(body.toString());

        System.out.println(sendPost(url,"Smash4 Thumbnail Uploader",body.toString()));
    }

    private void sortVideos(ArrayList<VideoInfo> videoInfoArrayList) {
        videoInfoArrayList.sort(Comparator.comparingInt(p -> p.sortingValue));
    }


    static String sendPost(String url, String user, String body){
        //post without connection, only url
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            return sendPost(con, user, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL";
    }
    static String sendPost(HttpURLConnection con, String USER_AGENT, String body){
        //post with custom connection for authentication ect
        StringBuffer responseString = null;
        try {
            con.setRequestMethod("POST");
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

}
