import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Thumbnails.Set;
import com.google.api.services.youtube.model.ThumbnailSetResponse;
import com.sun.corba.se.spi.orb.StringPair;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;


/**
 * Originally created by Josh Bhattarai on 5/18/2017.
 */
class YouTubeStuff{
    private OverlayData od;
    private GetResources get = new GetResources();
    private JProgressBar progress;
    private String apiKey;
    private JFrame window;
    private String accessToken;
    private String clientID;
    private String clientSecret;
    void startYoutube(OverlayData od, JFrame window) {
        windowStuff(od, window);
        ArrayList<StringPair> videoIDs = playlistStuff();
        ArrayList<VideoInfo> videoInfoArrayList = makeVideoInfo(videoIDs);
        setInfo(videoInfoArrayList);
        getAuth(videoInfoArrayList);
    }
    private void windowStuff(OverlayData od, JFrame window){
        this.od = od;
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
    private ArrayList<StringPair> playlistStuff(){
        setVal(2);
        String playlistID = getIDFromURL(od.youtubePlaylistURL);
        setVal(3);
        apiKey = loadKey();
        setVal(4);
        return getPlayListPages(playlistID);
    }
    private ArrayList<VideoInfo> makeVideoInfo(ArrayList<StringPair> videoIDs){
        ArrayList<VideoInfo> videoInfoArrayList = new ArrayList<>();
        String url = "https://www.googleapis.com/youtube/v3/videos?id=";
        for(StringPair SP : videoIDs){
            VideoInfo vi = new VideoInfo();
            vi.videoID = SP.getFirst();
            vi.playlistItemID = SP.getSecond();

            String temp = GetResources.httpGet(url + SP.getFirst() + "&key=" + apiKey +
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
            od.addInfo(v);
            v.thumbnail = od.createFrame();
            setVal((int) Math.round(counter/len *43) +7);
        }

    }
    private void getAuth(ArrayList<VideoInfo> videoInfoArrayList){
        boolean newTokenNeeded = false;
        String atoken = "";
        try{
            JSONObject refreshJSON = new JSONObject(get.getTextFromFile("./Custom/LOGIN.INFO"));
            atoken = refreshJSON.get("access_token").toString();
            String rtoken = refreshJSON.get("access_token").toString();
            atoken = refreshToken(rtoken);
            if(atoken.equalsIgnoreCase("Fail")){
                newTokenNeeded = true;
            }
        }catch (Exception e){
            newTokenNeeded = true;
        }
        if(newTokenNeeded) {
            GetResources.openWebpage("https://accounts.google.com/o/oauth2/v2/auth?client_id=58309125818-ispm6k8566hgvasijel18l3808f66vvi.apps.googleusercontent.com&response_type=code&scope=https://www.googleapis.com/auth/youtube&redirect_uri=urn:ietf:wg:oauth:2.0:oob");
            JLabel pasteLabel = new JLabel("Paste code here:");
            pasteLabel.setLocation(200, 300);
            pasteLabel.setSize(100, 20);
            window.add(pasteLabel);
            JTextField pasteField = new JTextField();
            pasteField.setLocation(300, 300);
            pasteField.setSize(100, 20);
            window.add(pasteField);
            JButton enterButton = new JButton("Enter");
            enterButton.setLocation(400, 300);
            enterButton.setSize(100, 20);
            window.add(enterButton);
            window.paintComponents(window.getGraphics());
            enterButton.addActionListener(l -> enterButtonAction(pasteField, videoInfoArrayList));
        }else{
            startLoop(atoken,videoInfoArrayList);
        }
    }

    private void enterButtonAction(JTextField pasteField, ArrayList<VideoInfo> videoInfoArrayList) {

        String code = pasteField.getText().trim();
        startLoop(code,videoInfoArrayList);
    }

    private void startLoop(String code, ArrayList<VideoInfo> videoInfoArrayList){

        String url = "https://www.googleapis.com/oauth2/v4/token?";
        String urlParams = "code="
                +code+"&client_id=" + clientID + "&client_secret="+ clientSecret+
                "&redirect_uri=urn:ietf:wg:oauth:2.0:oob&grant_type=authorization_code";
        String temp = GetResources.httpPost(url,urlParams,"Test, Josh Bhattarai");
        JSONObject returnToken = new JSONObject(temp);
        accessToken = returnToken.get("access_token").toString();
        String refreshToken = returnToken.get("refresh_token").toString();

        JSONObject refreshJSON = new JSONObject();
        refreshJSON.put("access_token", accessToken);
        refreshJSON.put("refresh_token", refreshToken);

        try{
            get.writeText(refreshJSON.toString(),"./Custom/LOGIN.INFO");
        }catch (Exception e){
            e.printStackTrace();
        }


        uploadThumbnails(videoInfoArrayList);
        endThumbnails(videoInfoArrayList);
    }

    private void endThumbnails(ArrayList<VideoInfo> videoInfoArrayList) {
        Organize o = new Organize();
        o.organizePlaylist(videoInfoArrayList, od,accessToken);
        setVal(100);
        setString("Complete");
    }


    private void uploadThumbnails(ArrayList<VideoInfo> videoInfoArrayList){
        double counter = 0;
        double len = videoInfoArrayList.size();
        for( VideoInfo vi : videoInfoArrayList){
            counter++;
            if(vi.success) {
                try {
                    uploadThumbnail(vi.videoID, vi.thumbnail);
                }catch (Exception e){
                    setString("Error... please wait");
                    e.printStackTrace();
                    continue;
                }
            }

            setVal((int) Math.round(counter/len * 40) +50);
        }
        setVal(90);
    }
    static String getIDFromURL(String URL){
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
        JSONObject clientSecretsJSON = getClientSecretJSON();
        clientSecretsJSON = new JSONObject(clientSecretsJSON.get("installed").toString());
        clientID = clientSecretsJSON.get("client_id").toString();
        clientSecret = clientSecretsJSON.get("client_secret").toString();
        return clientSecretsJSON.get("api_key").toString();
    }
    private ArrayList<StringPair> getPlayListPages(String playlistID){
        ArrayList<StringPair> videoIDs = new ArrayList<>();
        String nextPageToken = "";
        while(nextPageToken != null){
            JSONObject page = getPlayListJSON(playlistID,nextPageToken);
            try {
                nextPageToken = page.getString("nextPageToken");
            }catch(Exception e){
                nextPageToken = null;
            }
            JSONArray items = new JSONArray(page.get("items").toString());
            for(int i = 0; i < items.length(); ++i){
                Object item = items.get(i);
                String itemText = item.toString();
                JSONObject itemJSON = new JSONObject(itemText);
                String PLID = itemJSON.get("id").toString();
                itemJSON = new JSONObject(itemJSON.get("contentDetails").toString());
                String VID = itemJSON.get("videoId").toString();
                StringPair sp = new StringPair(VID,PLID);
                videoIDs.add(sp);
            }
        }
        setVal(5);
        return videoIDs;
    }
    private JSONObject getPlayListJSON(String ID, String pageToken){
        String userAgent = "Test, Josh Bhattarai";
        String URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=contentDetails&playlistId=";
        URL = URL + ID + "&key=" + apiKey+"&pageToken=" + pageToken;
        String playListInfo = GetResources.httpGet(URL,userAgent);
        return new JSONObject(playListInfo);
    }
    private void uploadThumbnail(String videoId, BufferedImage thumbnail){
        YouTube youtube;
        String IMAGE_FILE_FORMAT = "image/png";
        // This OAuth 2.0 access scope allows for full read/write access to the
        // authenticated user's account.
        ArrayList<String> scopes = new ArrayList<>();
        scopes.add("https://www.googleapis.com/auth/youtube");
        //make thumbnail file
        String path = get.saveImg(thumbnail,"./Screens/screenshot0t.png");
        File imageFile = new File(path);
        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "uploadthumbnail");
            credential.setAccessToken(accessToken);


            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).setApplicationName(
                    "youtube-cmdline-uploadthumbnail-sample").build();


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
            MediaHttpUploaderProgressListener progressListener = uploader1 -> {
                switch (uploader1.getUploadState()) {
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
                        System.out.println("Upload percentage: " + uploader1.getProgress());
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
            setString("Error");
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            setString("Error");
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            //delete old thumbnail
            get.deleteFile(imageFile.getPath());
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
            case "UCkSk688sgrxRJifpfeD79yQ":
                try{
                    glaceyParse(title,description,videoInfo);
                }catch(Exception e){
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
    private void setString(String val){
        progress.setString(val);
        window.paintComponents(window.getGraphics());
    }
    private void spartanParse(String title, String description, VideoInfo videoInfo){
        gooshiJ3Parse(title,description,videoInfo);
    }
    private void spectrumParse(String title, String description, VideoInfo videoInfo){
        gooshiJ3Parse(title,description,videoInfo);
    }
    private void glaceyParse(String title, String description, VideoInfo videoInfo){
        String[] parts = title.split(" vs ");
        String leftSide = parts[0];
        String rightSide = parts[1];
        parts = leftSide.split("-");
        String firstPlayer = parts[1];
        parts = rightSide.split("\\[");
        String secondPlayer = parts[0];
        parts = parts[1].split("\\]");
        String round = parts[0];

        videoInfo.player1 = outsideParentheses(firstPlayer);
        addChars(insideParentheses(firstPlayer),videoInfo,true);

        videoInfo.player2 = outsideParentheses(secondPlayer);
        addChars(insideParentheses(secondPlayer),videoInfo,false);

        videoInfo.setRound(round);
    }
    private void gooshiJ3Parse(String title, String description, VideoInfo videoInfo) {
        String round;
        String[] parts = title.split(":");
        String leftSide = parts[0];
        String rightSide = parts[1];
        parts = leftSide.split(",");
        round = parts[1].trim();
        videoInfo.setRound(round);

        String[] rightArray = rightSide.split(" vs ");
        String firstPlayerAndChars = rightArray[0];
        String secondPlayerAndChars = rightArray[1];
        videoInfo.player1 = outsideParentheses(firstPlayerAndChars);
        videoInfo.player2 = outsideParentheses(secondPlayerAndChars);

        String firstChars = insideParentheses(firstPlayerAndChars);
        String secondChars = insideParentheses(secondPlayerAndChars);

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
        videoInfo.player1 = outsideParentheses(firstPlayerAndChars);
        videoInfo.player2 = outsideParentheses(secondPlayerAndChars);

        String firstChars = insideParentheses(firstPlayerAndChars);
        String secondChars = insideParentheses(secondPlayerAndChars);

        addChars(firstChars,videoInfo,true);
        addChars(secondChars,videoInfo,false);


        videoInfo.setRound(description);
    }
    private void defaultParse(String title, String description, VideoInfo videoInfo){
        videoInfo.player1 = "Placeholder 1";
        videoInfo.player2 = "Placeholder 2";
    }
    private String insideParentheses(String content){
        String[] temp = content.split("\\(");
        temp = temp[1].split("\\)");
        String output = temp[0];
        output = output.trim();
        return output;
    }
    private String outsideParentheses(String content){
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
            String temp0 = temp[0].trim();
            String temp1 = temp[1].trim();
            firstChar = "0-" + temp0;
            secondChar = "0-" + temp1;
        }else if(content.contains(",")){
            String[] temp = content.split(",");
            String temp0 = temp[0].trim();
            String temp1 = temp[1].trim();
            firstChar = "0-" + temp0;
            secondChar = "0-" + temp1;
        }else if(content.contains("+")){
            String[] temp = content.split("\\+");
            String temp0 = temp[0].trim();
            String temp1 = temp[1].trim();
            firstChar = "0-" + temp0;
            secondChar = "0-" + temp1;
        }
        if(isFirst){
            videoInfo.setChar1(firstChar);
            videoInfo.setSecondary1(secondChar);
        }else{
            videoInfo.setChar2(firstChar);
            videoInfo.setSecondary2(secondChar);
        }
    }


    private JSONObject getClientSecretJSON() {
        String clientSecrets;
        InputStream in = this.getClass().getResourceAsStream("client_secret.json");
        BufferedReader br = get.inputStreamToBufferedReader(in);
        clientSecrets = get.bufferedReaderToString(br);
        return new JSONObject(clientSecrets);
    }

    private String refreshToken(String rToken) {
        String url = "https://www.googleapis.com/oauth2/v4/token";
        try{
            HttpURLConnection con = GetResources.createHTTPURLConnection(url);
            con.addRequestProperty("refresh_token",rToken);
            con.addRequestProperty("client_id", clientID);
            con.addRequestProperty("client_secret", clientSecret);
            con.addRequestProperty("grant_type", "refresh_token");
            String s = GetResources.sendGet(con,"Smash4 thumbnail uploader");
            System.out.println("Token Response: " + s);
            JSONObject responseJSON = new JSONObject(s);
            return responseJSON.get("access_token").toString();
        }catch(Exception e){
            return "Fail";
        }
    }
}
