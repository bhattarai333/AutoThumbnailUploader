import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Originally created by Josh Bhattarai on 6/15/2017.
 */
class Organize {
    void organizePlaylist(ArrayList<VideoInfo> videoInfoArrayList, String apiKey, String clientID, String clientSecret, Data data){
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?kind=video&part=snippet";
    }

    private static ArrayList<String> getPlayListPages(String playlistID, String apiKey){
        ArrayList<String> videoIDs = new ArrayList<>();
        String nextPageToken = "";
        while(nextPageToken != null){
            JSONObject page = getPlayListJSON(playlistID,nextPageToken,apiKey);
            try {
                nextPageToken = page.getString("nextPageToken");
            }catch(Exception e){
                nextPageToken = null;
            }
            JSONArray items = new JSONArray(page.get("items").toString());
            for(Object item : items){
                String itemText = item.toString();
                JSONObject itemJSON = new JSONObject(itemText);
                itemJSON = new JSONObject(itemJSON.get("contentDetails").toString());
                String id = itemJSON.get("videoId").toString();
                videoIDs.add(id);
            }
        }
        return videoIDs;
    }
    private static JSONObject getPlayListJSON(String ID, String pageToken,String apiKey){
        String userAgent = "Test, Josh Bhattarai";
        String URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=contentDetails&playlistId=";
        URL = URL + ID + "&key=" + apiKey+"&pageToken=" + pageToken;
        String playListInfo = GetResources.httpGet(URL,userAgent);
        return new JSONObject(playListInfo);
    }

    private static String getIDFromURL(String URL){
        if(URL.contains("list=")){
            String[] split = URL.split("list=");
            URL = split[1];
        }
        if(URL.contains("&")){
            String[] split = URL.split("&");
            URL = split[0];
        }
        return URL;
    }
}
