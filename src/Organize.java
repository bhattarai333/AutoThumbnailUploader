import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Originally created by Josh Bhattarai on 6/15/2017.
 */
class Organize {
    void organizePlaylist(ArrayList<VideoInfo> videoInfoArrayList, OverlayData od, String accessToken){
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?kind=video&part=snippet&access_token=" + accessToken;
        System.out.println(accessToken);

        videoInfoArrayList.sort(Comparator.comparingInt(VideoInfo::getSortingValue).reversed().thenComparingInt(VideoInfo::getSortingTiebreaker).reversed().thenComparingInt(VideoInfo::getWinner)  );
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


        HttpURLConnection con = GetResources.createHTTPURLConnection(url);
        con.setRequestProperty("content-type","application/json");

        GetResources.sendPut(con, body.toString());
    }





}
