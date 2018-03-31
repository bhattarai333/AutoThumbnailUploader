import Enums.Overlay;

import java.awt.image.BufferedImage;

public class OverlayData {
    String firstCharacter;
    String secondCharacter;
    String thirdCharacter;
    String fourthCharacter;
    String round;
    String tournamentName;
    String date;
    String playerOneName;
    String playerTwoName;
    String gamePlayed;
    String customLogoPath;
    String customGradientTopBottomPath;
    String customGradientMiddlePath;
    String customGradientBottomPath;
    String fontColor;
    String customFontPath;
    String tourneyImagePath;
    String fontOutlineColor;
    private int fontOutlineThickness;
    boolean useSponsors;
    String customFighterOnePath;
    String customFighterTwoPath;
    private int shadowThickness;
    boolean bindNameSizes;
    String youtubePlaylistURL;
    String videoID;
    String playlistItemID;
    Overlay overlay;
    private boolean doubles;

    public void addInfo(VideoInfo v){
        this.playerOneName = v.player1;
        this.playerTwoName = v.player2;
        this.round = v.getRound();
        this.firstCharacter = v.getCharacter1();
        this.secondCharacter = v.getCharacter2();
        this.thirdCharacter = v.getSecondary1();
        this.fourthCharacter = v.getSecondary2();
        this.videoID = v.videoID;
        this.playlistItemID = v.playlistItemID;
    }

    public OverlayData(){
        youtubePlaylistURL = "";
        videoID = "";
        playlistItemID = "";
        gamePlayed= "WiiUFull";
        tournamentName = "";
        date = "";
        customLogoPath = "";
        customGradientTopBottomPath = "";
        customGradientMiddlePath = "";
        customGradientBottomPath = "";
        tourneyImagePath = "";
        customFontPath = "";
        fontOutlineColor = "";
        fontOutlineThickness = 5;
        useSponsors = true;
        shadowThickness = 8;
        fontColor = "";

        playerOneName = "Player 1";
        playerTwoName = "Player 2";
        firstCharacter = "0-Mario";
        secondCharacter = "0-Mario";
        thirdCharacter = "0Nothing";
        fourthCharacter = "0Nothing";
        customFighterOnePath = "";
        customFighterTwoPath = "";
        round = "Round";
        bindNameSizes = false;
        overlay = Overlay.DEFAULT_OVERLAY;
        doubles = false;
    }

    void setFontOutlineThickness(String input) {
        input = input.trim();
        try{
            fontOutlineThickness = Integer.parseInt(input);
        }catch(Exception e){
            fontOutlineThickness = 8;
        }
    }

    void setShadowThickness(String input){
        input = input.trim();
        try{
            shadowThickness = Integer.parseInt(input);
        }catch(Exception e){
            shadowThickness = 8;
        }
    }

    BufferedImage createFrame(){
        switch(overlay){
            case GOOSHI_OVERLAY:
                GooshiOverlay gf = new GooshiOverlay();
                return gf.createFrame(this);
            default:
                SmashOverlay sf = new SmashOverlay();
                return sf.createFrame(this);
        }
    }

    int getFontOutlineThickness(){
        return fontOutlineThickness;
    }
    int getShadowThickness(){
        return shadowThickness;
    }

    boolean isDoubles() {
        return doubles;
    }

    void setDoubles(boolean doubles) {
        if(doubles) {
            useSponsors = false;
            String[] firstTeamNames = playerOneName.split("/");
            playerOneName = firstTeamNames[0].trim() + "  \t" + firstTeamNames[1].trim();
            String[] secondTeamNames = playerTwoName.split("/");
            playerTwoName = secondTeamNames[0].trim() + "  \t" + secondTeamNames[1].trim();
        }else{
            useSponsors = true;
        }
    }
}
