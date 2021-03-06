import com.google.api.services.youtube.model.Video;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.util.Comparator;

/**
 * Originally created by Josh Bhattarai on 5/18/2017.
 */
class VideoInfo{
    BufferedImage thumbnail;
    JSONObject videoJSON;
    String videoID;
    String playlistItemID;
    boolean success;

    String player1;
    String player2;
    private String round;

    private String character1;
    private String character2;
    private String secondary1;
    private String secondary2;
    String customFighterOne;
    String customFighterTwo;

    private int sortingValue;
    private int sortingTiebreaker;
    private boolean winner;
    int playlistIndex;

    VideoInfo(){
        player1 = "";
        player2 = "";
        round = "Winner's  Side";
        character1 = "0-Mario";
        character2 = "0-Mario";
        secondary1 = "0Nothing";
        secondary2 = "0Nothing";
        customFighterOne = "";
        customFighterTwo = "";
        success = true;
        sortingValue = 0;
        sortingTiebreaker = 0;
        winner = false;
        playlistIndex = 0;
    }

    void setChar1(String input){
        character1 = determineChar(input);
    }
    void setChar2(String input){
        character2 = determineChar(input);
    }
    void setSecondary1(String input){
        secondary1 = determineChar(input);
    }
    void setSecondary2(String input){
        secondary2 = determineChar(input);
    }

    String getCharacter1(){
        return character1;
    }
    String getCharacter2(){
        return character2;
    }
    String getSecondary1(){
        return secondary1;
    }
    String getSecondary2(){
        return secondary2;
    }

    private String determineChar(String input){
        input = input.trim();
        if(input.equals("0Nothing")){
            return "0Nothing";
        }
        String[] temp = input.split("-");
        input = temp[1];
        String inputLower = input.toLowerCase();
        if(!foundChar(input)) {
            input = parseCharacter(inputLower);
        }
        input = "0-" + input;
        return input;
    }

    private boolean foundChar(String str) {
        switch (str) {
            case "Bayonetta":
                break;
            case "Captain Falcon":
                break;
            case "Cloud":
                break;
            case "DDD":
                break;
            case "Diddy Kong":
                break;
            case "Donkey Kong":
                break;
            case "Doctor Mario":
                break;
            case "Duck Hunt":
                break;
            case "Falco":
                break;
            case "Fox":
                break;
            case "Mr. Game and Watch":
                break;
            case "Ganondorf":
                break;
            case "Greninja":
                break;
            case "Ike":
                break;
            case "Corrin":
                break;
            case "Kirby":
                break;
            case "Bowser":
                break;
            case "Bowser Jr.":
                break;
            case "Link":
                break;
            case "Charizard":
                break;
            case "Lucario":
                break;
            case "Lucas":
                break;
            case "Lucina":
                break;
            case "Luigi":
                break;
            case "Mario":
                break;
            case "Marth":
                break;
            case "Meta Knight":
                break;
            case "MewTwo":
                break;
            case "Mii Brawler":
                break;
            case "Mii Gunner":
                break;
            case "Mii Sword":
                break;
            case "Villager":
                break;
            case "Ness":
                break;
            case "Pacman":
                break;
            case "Palutena":
                break;
            case "Peach":
                break;
            case "Pikachu":
                break;
            case "Olimar":
                break;
            case "Pit":
                break;
            case "Dank Pit":
                break;
            case "Puff":
                break;
            case "Robin":
                break;
            case "R.O.B.":
                break;
            case "Megaman":
                break;
            case "Rosalina & Luma":
                break;
            case "Roy":
                break;
            case "Ryu":
                break;
            case "Samus":
                break;
            case "Sheik":
                break;
            case "Shulk":
                break;
            case "Sonic":
                break;
            case "Zamus":
                break;
            case "Toon Link":
                break;
            case "Wario":
                break;
            case "Wii Fit":
                break;
            case "Yoshi":
                break;
            case "Zelda":
                break;
            case "Little Mac":
                break;
            case "Ice Climbers":
                break;
            case "Pichu":
                break;
            case "Wolf":
                break;
            case "Snake":
                break;
            case "Young Link":
                break;
            default:
                return false;

        }
        return true;
    }

    private String parseCharacter(String input){
        if(input.contains("jr") || input.contains("junior")){
            return "Bowser Jr.";
        }
        if(input.contains("falcon") || input.contains("captain") || input.contains("cap'n")){
            return "Captain Falcon";
        }
        if(input.contains("diddy") || input.contains("didy")){
            return "Diddy Kong";
        }
        if(input.contains("dk") || input.contains("donkey")){
            return "Donkey Kong";
        }
        if(input.contains("duck") || input.contains("hunt") || input.contains("dh") ||input.contains("dog")){
            return "Duck Hunt";
        }
        if(input.contains("bayo")){
            return "Bayonetta";
        }
        if(input.contains("pac")){
            return "Pacman";
        }
        if(input.contains("mac") || input.contains("little")){
            return "Little Mac";
        }
        if(input.contains("poke") || input.contains("poké")){
            return "Pokemon Trainer";
        }
        if(input.contains("wii") || input.contains("fit") || input.contains("trainer")){
            return "Wii Fit";
        }
        if(input.contains("zero") || input.contains("suit") ||input.contains("zamus") || input.contains("zss")){
            return "Zamus";
        }
        if(input.contains("rosa")||input.contains("luma") || input.contains("r&l")){
            return "Rosalina & Luma";
        }
        if(input.contains("young") || input.contains("ylink") || input.contains("y.link")){
            return "Young Link";
        }
        if
                (input.contains("mr") || input.contains("game") || input.contains("watch") ||input.contains("g&w")
                || input.contains("gandw")){
            return "Mr. Game and Watch";
        }
        if(input.contains("ganon")){
            return "Ganondorf";
        }
        if(input.contains("jigg") || input.contains("puff")){
            return "Puff";
        }
        if(input.contains("dr") || input.contains("doctor")){
            return "Doctor Mario";
        }
        if
                (input.contains("king") || input.contains("ddd") || input.contains("dedede") || input.contains("daddle")
                || input.contains("dadle") || input.contains("dingo")){
            return "DDD";
        }
        if(input.contains("ninja")){
            return "Greninja";
        }
        if(input.contains("meta") || input.contains("knight") || input.contains("mk")){
            return "Meta Knight";
        }
        if(input.contains("mew")){
            return "MewTwo";
        }
        if(input.contains("dark") || input.contains("dank") || input.contains("edge") || input.contains("pitoo")){
            return "Dank Pit";
        }
        if(input.contains("robin")){
            return "Robin";
        }
        if(input.contains("rob") || input.contains("r.o.b")){
            return "R.O.B.";
        }
        if(input.contains("peach") || input.contains("daisy")){
            return "Peach";
        }
        if(input.contains("mega")){
            return "Megaman";
        }
        if(input.contains("shiek") || input.contains("sheik")){
            return "Sheik";
        }
        if(input.contains("zard")){
            return "Charizard";
        }
        if(input.contains("olimar") || input.contains("alph")){
            return "Olimar";
        }
        if(input.contains("pika")){
            return "Pikachu";
        }
        if(input.contains("toon") || input.contains("t. link") || input.contains("tlink") ||input.contains("t.link")){
            return "Toon Link";
        }
        if(input.contains("ice") || input.contains("climbers") || input.contains("icies") || input.contains("icys")){
            return "Ice Climbers";
        }
        if(input.contains("luigi") || input.contains("weegee")){
            return "Luigi";
        }
        if(input.contains("cloud")){
            return "Cloud";
        }
        if(input.contains("falco")){
            return "Falco";
        }
        if(input.contains("fox")){
            return "Fox";
        }
        if(input.contains("ike")){
            return "Ike";
        }
        if(input.contains("kirby")){
            return "Kirby";
        }
        if(input.contains("corrin")){
            return "Corrin";
        }
        if(input.contains("bowser")){
            return "Bowser";
        }
        if(input.contains("link")){
            return "Link";
        }
        if(input.contains("lucario")){
            return "Lucario";
        }
        if(input.contains("lucina")){
            return "lucina";
        }
        if(input.contains("marth")){
            return "Marth";
        }
        if(input.contains("villager")){
            return "Villager";
        }
        if(input.contains("ness") || input.contains("sans")){
            return "Ness";
        }
        if(input.contains("palutena")){
            return "Palutena";
        }
        if(input.contains("pit")){
            return "Pit";
        }
        if(input.contains("roy") || input.contains("our boy") || input.contains("our boi")){
            return "Roy";
        }
        if(input.contains("ryu")){
            return "Ryu";
        }
        if(input.contains("samus")){
            return "Samus";
        }
        if(input.contains("shulk") || input.contains("monado") || input.contains("monando")){
            return "Shulk";
        }
        if(input.contains("wario")){
            return "Wario";
        }
        if(input.contains("zelda")||input.contains("zorldo")){
            return "Zelda";
        }
        return "Mario";
    }

    public void setRound(String input){
        round = parseRound(input);
        String s = input.replaceAll("[^0-9]", "");
        try {
            sortingTiebreaker = Integer.parseInt(s);
        }catch(Exception e){
            sortingTiebreaker = 0;
        }
        setSortingValue();
    }
    public String getRound(){
        return round;
    }
    private String parseRound(String originalContent){
        String content = originalContent.trim();
        content = content.toLowerCase();
        if(content.contains("amateur") || content.contains("am bracket")|| content.contains(" ab ")
                || content.contains("[ab]") || content.contains("(ab)") || content.contains("{ab}")
                || content.contains("<ab>")){

            return "Amateur  Bracket";
        }

        if(content.contains("pool")){
            return "Pools";
        }

        if(content.contains("thug")){
            return "Thug Finals";
        }

        if(content.contains("grand") || content.contains("gf")){
            if(content.contains("two") || content.contains("2") || content.contains("second") || content.contains("reset")){
                return "Grand Finals  Set Two";
            }else{
                return "Grand Finals";
            }
        }

        if((content.contains("final") && !content.contains("semi") && !content.contains("quart") ) || (content.contains("wf") || content.contains("lf"))){


            if(content.contains("loser") || content.contains("lf")){
                return "Loser's Finals";
            }else if(content.contains("winner") || content.contains("wf")){
                return "Winner's Finals";
            }
        }

        if(content.contains("semi") || content.contains("lsf") || content.contains("wsf")){
            if(content.contains("loser") || content.contains("lsf")){
                return "Loser's  Semifinals";
            }else if(content.contains("winner") || content.contains("wsf")){
                return "Winner's  Semifinals";
            }
        }

        if(content.contains("quarter") || content.contains("lq") || content.contains("wq")){
            if(content.contains("loser") || content.contains("lq")){
                return "Loser's  Quarters";
            }else if(content.contains("winner") || content.contains("wq")){
                return "Winner's  Quarters";
            }
        }

        if(content.contains("loser")){
            return "Loser's Side";
        }else if(content.contains("winner")){
            return "Winner's Side";
        }

        if(content.contains("money") || content.contains("mm") || content.contains("$")){
            return "Money Match";
        }

        if(content.contains("w")){
            return "Winner's Side";
        }
        if(content.contains("l")){
            return "Loser's Side";
        }
        return originalContent;
    }

    public void setSortingValue(){
        switch(round){
            case "Thug Finals":
                winner = true;
                sortingValue = -1;
                break;
            case "Grand Finals  Set Two":
                winner = false;
                sortingValue = 0;
                break;
            case "Grand Finals":
                winner = true;
                sortingValue = 1;
                break;
            case "Loser's Finals":
                winner = false;
                sortingValue = 2;
                break;
            case "Winner's Finals":
                winner = true;
                sortingValue = 4;
                break;
            case "Winner's  Semifinals":
                winner = true;
                sortingValue = 6;
                break;
            case "Loser's  Semifinals":
                winner = false;
                sortingValue = 3;
                break;
            case "Winner's  Quarters":
                winner = true;
                sortingValue = 7;
                break;
            case "Loser's  Quarters":
                winner = false;
                sortingValue = 5;
                break;
            case "Winner's Side":
                winner = true;
                sortingValue = 8;
                break;
            case "Loser's Side":
                winner = false;
                sortingValue = 8;
                break;
            case "Money Match":
                sortingValue = 10;
                break;
            case "Pools":
                sortingValue = 11;
                break;
            case "Amateur  Bracket":
                sortingValue = 12;
                break;
            default:
                sortingValue = 13;
                break;
        }
    }

    public int getSortingValue() {
        return sortingValue;
    }

    public int getSortingTiebreaker() {
        return sortingTiebreaker;
    }

    public int getWinner(){
        if(winner){
            return 1;
        }else{
            return 0;
        }
    }

    /*@Override
    public int compareTo(VideoInfo o) {
        if(o.getSortingValue() > this.getSortingValue()){
            return 1;
        }else if(o.getSortingValue() < this.getSortingValue()){
            return 0;
        }else if(o.getSortingValue() == this.getSortingValue()){
            if(o.getSortingTiebreaker() > this.getSortingTiebreaker()){
                return 0;
            }else if(o.getSortingTiebreaker() < this.getSortingTiebreaker()){
                return 1;
            }else if(o.getSortingTiebreaker() == this.getSortingTiebreaker()){
                if(o.winner && !this.winner){
                    return 1;
                }else{
                    return 0;
                }
            }
        }
        return 0;
    }*/


    @Override
    public String toString() {
        String output = "";
        output = "P1: " + this.player1 + " vs P2: " + this.player2 + " " + this.round;
        return output;
    }
}
