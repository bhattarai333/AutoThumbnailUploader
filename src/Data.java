
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
    private int shadowThickness;

    Data(){
        youtubePlaylistURL = "";
        game= "";
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
