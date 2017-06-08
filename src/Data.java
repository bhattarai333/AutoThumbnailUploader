
/**
 * Originally created by Josh Bhattarai for on 5/11/2017.
 */
class Data {
    String youtubePlaylistURL;


    String game;
    String tournamentName;
    String date;


    String customLogo;
    String customGradientTop;
    String customGradientMiddle;
    String customGradientBottom;
    String tournamentImage;

    String fontColor;
    String customFont;
    String outlineColor;
    private int fontThickness;

    Boolean useSponsors;
    Boolean bindText;
    private int shadowThickness;

    String tempPlayer1;
    String tempPlayer2;
    String tempChar1;
    String tempChar2;
    String tempSecondary1;
    String tempSecondary2;
    String tempRound;
    String tempCustomFighter1;
    String tempCustomFighter2;


    Data(){
        youtubePlaylistURL = "";
        game= "WiiUFull";
        tournamentName = "";
        date = "";
        customLogo = "";
        customGradientTop = "";
        customGradientMiddle = "";
        customGradientBottom = "";
        tournamentImage = "";
        fontColor = "";
        customFont = "";
        outlineColor = "";
        fontThickness = 5;
        useSponsors = true;
        shadowThickness = 8;

        tempPlayer1 = "Player 1";
        tempPlayer2 = "Player 2";
        tempChar1 = "0-Mario";
        tempChar2 = "0-Mario";
        tempSecondary1 = "0Nothing";
        tempSecondary2 = "0Nothing";
        tempCustomFighter1 = "";
        tempCustomFighter2 = "";
        tempRound = "Round";
        bindText = false;
    }

    void setFontThickness(String input) {
        input = input.trim();
        try{
            fontThickness = Integer.parseInt(input);
        }catch(Exception e){
            fontThickness = 8;
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
    int getFontThickness(){
        return fontThickness;
    }
    int getShadowThickness(){
        return shadowThickness;
    }
}
