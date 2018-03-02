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
    Overlay overlay;

    public OverlayData(){
        youtubePlaylistURL = "";
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

    int getFontOutlineThickness(){
        return fontOutlineThickness;
    }
    int getShadowThickness(){
        return shadowThickness;
    }
}
