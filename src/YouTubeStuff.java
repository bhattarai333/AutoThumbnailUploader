import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Thumbnails.Set;
import com.google.api.services.youtube.model.ThumbnailSetResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Originally created by Josh Bhattarai on 5/18/2017.
 */
class YouTubeStuff {
    private Data d;
    private GetResources get = new GetResources();
    private JProgressBar progress;
    private String apiKey;
    private JFrame window;
    void startYoutube(Data d, JFrame window){
        windowStuff(d,window);
        ArrayList<String> videoIDs = playlistStuff();
        ArrayList<VideoInfo> videoInfoArrayList = makeVideoInfo(videoIDs);
        setInfo(videoInfoArrayList);
        uploadThumbnails(videoInfoArrayList);
    }

    private void windowStuff(Data d, JFrame window){
        this.d = d;
        this.window = window;

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel progressLabel = new JLabel("Progress");
        progressLabel.setLocation(window.getWidth() / 2 - 27, 330);
        progressLabel.setSize(54,20);
        window.add(progressLabel);

        progress = new JProgressBar();
        progress.setLocation((window.getWidth() / 2) - 100, 350);
        progress.setSize(200,20);
        progress.setValue(0);
        progress.setStringPainted(true);
        window.add(progress);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        setVal(1);
    }
    private ArrayList<String> playlistStuff(){
        setVal(2);
        String playlistID = getIDFromURL(d.youtubePlaylistURL);
        setVal(3);
        apiKey = loadKey();
        setVal(4);
        return getPlayListPages(playlistID);
    }
    private ArrayList<VideoInfo> makeVideoInfo(ArrayList<String> videoIDs){
        ArrayList<VideoInfo> videoInfoArrayList = new ArrayList<>();
        String url = "https://www.googleapis.com/youtube/v3/videos?id=";
        for(String ID : videoIDs){
            VideoInfo vi = new VideoInfo();
            vi.videoID = ID;

            String temp = get.httpGet(url + ID + "&key=" + apiKey +
                            "&fields=items(snippet(channelId,title,description))&part=snippet",
                    "Test, Josh Bhattarai");

            vi.videoJSON = new JSONObject(temp);
            videoInfoArrayList.add(vi);
        }
        setVal(7);
        return videoInfoArrayList;
    }
    private void setInfo(ArrayList<VideoInfo> videoInfoArrayList){
        String channelID;
        String title;
        String description;
        SmashFrame sf = new SmashFrame();
        double len = videoInfoArrayList.size();
        double counter = 0;
        for(VideoInfo v : videoInfoArrayList){
            counter++;
            JSONArray temp = new JSONArray(v.videoJSON.get("items").toString());
            if(temp.length() == 0){
                continue;
            }
            JSONObject info = new JSONObject(temp.get(0).toString());
            info = new JSONObject(info.get("snippet").toString());
            channelID = info.get("channelId").toString();
            title = info.get("title").toString();
            description = info.get("description").toString();
            stringParse(channelID, title, description, v);
            v.thumbnail = sf.createFrame(v.getCharacter1(),v.getCharacter2(),v.getSecondary1(),v.getSecondary2(),v.round,d.tournamentName,
                    d.date,v.player1,v.player2,d.game,d.customLogo,d.customGradientTop,d.customGradientMiddle,
                    d.customGradientBottom,d.fontColor,d.customFont,d.tournamentImage,d.outlineColor,
                    d.getFontThickness(),d.useSponsors,v.customFighterOne,v.customFighterTwo,d.getShadowThickness());
            //get.saveImg(v.thumbnail);
            setVal((int) Math.round(counter/len *43) +7);
        }

    }
    private void uploadThumbnails(ArrayList<VideoInfo> videoInfoArrayList){
        double counter = 0;
        double len = videoInfoArrayList.size();
        for( VideoInfo vi : videoInfoArrayList){
            counter++;
            if(vi.success) {
                uploadThumbnail(vi.videoID, vi.thumbnail);
            }

            setVal((int) Math.round(counter/len *50) +50);
        }
    }

    private String getIDFromURL(String URL){
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
    private String loadKey(){
        String clientSecrets = get.getTextFromFile("./Resources/JSON/client_secret.json");
        JSONObject clientSecretsJSON = new JSONObject(clientSecrets);
        clientSecretsJSON = new JSONObject(clientSecretsJSON.get("installed").toString());
        return clientSecretsJSON.get("api_key").toString();
    }
    private ArrayList<String> getPlayListPages(String playlistID){
        ArrayList<String> videoIDs = new ArrayList<>();
        String nextPageToken = "";
        while(nextPageToken != null){
            JSONObject page = getPlayListJSON(playlistID,nextPageToken);
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
        setVal(5);
        return videoIDs;
    }
    private JSONObject getPlayListJSON(String ID, String pageToken){
        String userAgent = "Test, Josh Bhattarai";
        String URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=contentDetails&playlistId=";
        URL = URL + ID + "&key=" + apiKey+"&pageToken=" + pageToken;
        String playListInfo = get.httpGet(URL,userAgent);
        return new JSONObject(playListInfo);
    }
    private void uploadThumbnail(String videoId, BufferedImage thumbnail){
        YouTube youtube;
        String IMAGE_FILE_FORMAT = "image/png";
        // This OAuth 2.0 access scope allows for full read/write access to the
        // authenticated user's account.
        ArrayList<String> scopes = new ArrayList<>();
        scopes.add("https://www.googleapis.com/auth/youtube");

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "uploadthumbnail");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).setApplicationName(
                    "youtube-cmdline-uploadthumbnail-sample").build();

            get.saveImg(thumbnail,"./Resources/Thumbnails/thumbnail.png");
            File imageFile = new File("./Resources/Thumbnails/thumbnail.png");

            // Create an object that contains the thumbnail image file's
            // contents.
            InputStreamContent mediaContent = new InputStreamContent(
                    IMAGE_FILE_FORMAT, new BufferedInputStream(new FileInputStream(imageFile)));
            mediaContent.setLength(imageFile.length());

            // Create an API request that specifies that the mediaContent
            // object is the thumbnail of the specified video.
            Set thumbnailSet = youtube.thumbnails().set(videoId, mediaContent);

            // Set the upload type and add an event listener.
            MediaHttpUploader uploader = thumbnailSet.getMediaHttpUploader();

            // Indicate whether direct media upload is enabled. A value of
            // "True" indicates that direct media upload is enabled and that
            // the entire media content will be uploaded in a single request.
            // A value of "False," which is the default, indicates that the
            // request will use the resumable media upload protocol, which
            // supports the ability to resume an upload operation after a
            // network interruption or other transmission failure, saving
            // time and bandwidth in the event of network failures.
            uploader.setDirectUploadEnabled(false);

            // Set the upload state for the thumbnail image.
            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                @Override
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    switch (uploader.getUploadState()) {
                        // This value is set before the initiation request is
                        // sent.
                        case INITIATION_STARTED:
                            System.out.println("Initiation Started");
                            break;
                        // This value is set after the initiation request
                        //  completes.
                        case INITIATION_COMPLETE:
                            System.out.println("Initiation Completed");
                            break;
                        // This value is set after a media file chunk is
                        // uploaded.
                        case MEDIA_IN_PROGRESS:
                            System.out.println("Upload in progress");
                            System.out.println("Upload percentage: " + uploader.getProgress());
                            break;
                        // This value is set after the entire media file has
                        //  been successfully uploaded.
                        case MEDIA_COMPLETE:
                            System.out.println("Upload Completed!");
                            break;
                        // This value indicates that the upload process has
                        //  not started yet.
                        case NOT_STARTED:
                            System.out.println("Upload Not Started!");
                            break;
                    }
                }
            };
            uploader.setProgressListener(progressListener);

            // Upload the image and set it as the specified video's thumbnail.
            ThumbnailSetResponse setResponse = thumbnailSet.execute();

            // Print the URL for the updated video's thumbnail image.
            System.out.println("\n================== Uploaded Thumbnail ==================\n");
            System.out.println("  - Url: " + setResponse.getItems().get(0).getDefault().getUrl());

        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private void stringParse(String channel_ID, String title, String description, VideoInfo videoInfo){
        channel_ID = channel_ID.trim();
        switch (channel_ID) {
            case "UCge5YpzyFA92XZTpzZX9S1w":
                try{
                    gooshiJ3Parse(title, description, videoInfo);
                }catch (Exception e){
                    e.printStackTrace();
                    videoInfo.success = false;
                }
                break;
            case "UCvMLi4Iq6xYDRnjc6eJ4IhA":
                try{
                    spartanParse(title, description, videoInfo);
                }catch (Exception e){
                    e.printStackTrace();
                    videoInfo.success = false;
                }
                break;
            case "UCFuG8SGqC57C2YUIHgpV1hw":
                try{
                    gooshiParse(title, description, videoInfo);
                }catch (Exception e){
                    e.printStackTrace();
                    videoInfo.success = false;
                }
                break;
            case "UC7URXrucJfoJGmTAsAYhBJg":
                try{
                    spectrumParse(title, description, videoInfo);
                }catch (Exception e){
                    e.printStackTrace();
                    videoInfo.success = false;
                }
                break;
            default:
                try{
                    defaultParse(title, description, videoInfo);
                }catch (Exception e){
                    e.printStackTrace();
                    videoInfo.success = false;
                }
                break;
        }
    }
    private void setVal(int value){
        progress.setValue(value);
        window.paintComponents(window.getGraphics());
    }
    private void spartanParse(String title, String description, VideoInfo videoInfo){
        gooshiJ3Parse(title,description,videoInfo);
    }
    private void spectrumParse(String title, String description, VideoInfo videoInfo){
        gooshiJ3Parse(title,description,videoInfo);
    }
    private void gooshiJ3Parse(String title, String description, VideoInfo videoInfo) {
        String round;
        String[] parts = title.split(":");
        String leftSide = parts[0];
        String rightSide = parts[1];
        round = findParentheses(leftSide);
        videoInfo.round = parseRound(round);

        String[] rightArray = rightSide.split(" vs ");
        String firstPlayerAndChars = rightArray[0];
        String secondPlayerAndChars = rightArray[1];
        videoInfo.player1 = beforeParentheses(firstPlayerAndChars);
        videoInfo.player2 = beforeParentheses(secondPlayerAndChars);

        String firstChars = findParentheses(firstPlayerAndChars);
        String secondChars = findParentheses(secondPlayerAndChars);

        addChars(firstChars,videoInfo,true);
        addChars(secondChars,videoInfo,false);
    }
    private void gooshiParse(String title, String description, VideoInfo videoInfo){
        description = description.toLowerCase();
        String[] parts = title.split(":");
        String rightSide = parts[1];

        String[] rightArray = rightSide.split(" vs ");
        String firstPlayerAndChars = rightArray[0];
        String secondPlayerAndChars = rightArray[1];
        videoInfo.player1 = beforeParentheses(firstPlayerAndChars);
        videoInfo.player2 = beforeParentheses(secondPlayerAndChars);

        String firstChars = findParentheses(firstPlayerAndChars);
        String secondChars = findParentheses(secondPlayerAndChars);

        addChars(firstChars,videoInfo,true);
        addChars(secondChars,videoInfo,false);

        String round;
        if(description.contains("set 2") || description.contains("set two") || description.contains("set2")){
            round = "Grand Finals  Set Two";
        }else if(description.contains("grand") || description.contains("gf")){
            round = "Grand  Finals";
        }else if(description.contains("pool")){
            round = "Pools";
        }else if((description.contains("final") && description.contains("loser")) || description.contains("lfs")){
            round = "Loser's  Finals";
        }else if((description.contains("final") && description.contains("winner")) || description.contains("wfs")){
            round = "Winner's  Finals";
        }else if(description.contains("losers")){
            round = "Loser's  Side";
        }else if (description.contains("winners")){
            round = "Winner's  Side";
        }else{
            round = "";
        }
        videoInfo.round = parseRound(round);
    }
    private void defaultParse(String title, String description, VideoInfo videoInfo){

    }
    private String findParentheses(String content){
        String[] temp = content.split("\\(");
        temp = temp[1].split("\\)");
        String output = temp[0];
        output = output.trim();
        return output;
    }
    private String beforeParentheses(String content){
        String[] temp = content.split("\\(");
        String output = temp[0];
        output = output.trim();
        return output;
    }
    private void addChars(String content, VideoInfo videoInfo, boolean isFirst) {
        content = content.trim();
        String firstChar = "0-" + content;
        String secondChar = "0Nothing";
        if(content.contains("/")){
            String[] temp = content.split("/");
            firstChar = "0-" + temp[0];
            secondChar = "0-" + temp[1];
        }
        if(isFirst){
            videoInfo.setChar1(firstChar);
            videoInfo.setSecondary1(secondChar);
        }else{
            videoInfo.setChar2(firstChar);
            videoInfo.setSecondary2(secondChar);
        }
    }
    private String parseRound(String originalContent){
        String content = originalContent.trim();
        content = content.toLowerCase();
        if(content.contains("pool")){
            return "Pools";
        }

        if(content.contains("grand") || content.contains("gf")){
            if(content.contains("two") || content.contains("2") || content.contains("second") || content.contains("reset")){
                return "Grand Finals  Set Two";
            }else{
                return "Grand  Finals";
            }
        }

        if(content.contains("final") || content.contains("wf") || content.contains("lf")){
            if(content.contains("loser") || content.contains("lf")){
                return "Loser's  Finals";
            }else if(content.contains("winner") || content.contains("wf")){
                return "Winner's  Finals";
            }
        }

        if(content.contains("w")){
            return "Winner's  Side";
        }
        if(content.contains("l")){
            return "Loser's  Side";
        }
        return originalContent;
    }

}
