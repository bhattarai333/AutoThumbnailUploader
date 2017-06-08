import org.mortbay.io.Buffer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Draws everything to the screen in the right way and saves a screenshot
 *
 * @author Josh Bhattarai
 * @version (in progress)
 */
class SmashFrame
{
    BufferedImage createFrame(
            String char1, String char2, String secondary1, String secondary2, String game, String tourney,
            String date, String player1, String player2, String version, String custLogo, String custGrad1,
            String custGrad2,String custGrad3, String fontColor, String customFont, String tourneyImg,
            String outlineColor,int fontThickness,boolean sponsors,String customFighterOne, String customFigherTwo,
            int shadowThickness)
    {
        Canvas myCanvas = new Canvas();
        System.out.println("1: " + char1 + " 2: " + char2 + " 3: " + secondary1 + " 4: " + secondary2 + "Round: " + game);
        return myCanvas.startSim(char1,char2,secondary1,secondary2,game,
                tourney,date,player1,player2,version,custLogo,custGrad1,custGrad2, custGrad3,
                fontColor,customFont,tourneyImg,outlineColor,fontThickness,sponsors,customFighterOne,customFigherTwo,
                shadowThickness);
    }
}

class Canvas extends JPanel{
    private GetResources get = new GetResources();

    private String game;
    private String tourneyName;
    private String date;
    private String player1;
    private String player2;
    private String customLogo;
    private String fontColor;
    private String customTourneyString;
    private String outlineColor;
    private int outlineThickness;
    private boolean sponsors;

    private BufferedImage fighter1;
    private BufferedImage fighter2;
    private BufferedImage fighter3;
    private BufferedImage fighter4;
    private BufferedImage logo;
    private BufferedImage middleGradientImage;
    private BufferedImage topGradientImage;
    private BufferedImage bottomGradientImage;
    private BufferedImage gameLogo;
    private BufferedImage playerOneSponsor;
    private BufferedImage playerTwoSponsor;
    private int shadowThickness;

    private Font BBN;
    private Font kAX;
    private Font ACN;
    private Font CST;
    private int pos1 = 0;
    private int pos2 = 0;
    private int fighterX1;
    private int fighterX2;
    private int fighterX3;
    private int fighterX4;
    private int fighterY1 = 125;
    private int fighterY2 = 125;
    private int fighterY3 = 125;
    private int fighterY4 = 125;
    private boolean secondary1 = true;
    private boolean secondary2 = true;
    private boolean first = false;

    BufferedImage startSim(
            String character1, String character2, String character3, String character4, String gamePlayed,
            String tournament, String time, String playerOne, String playerTwo, String version, String customLogo,
            String customGrad1, String customGrad2, String customGrad3, String fontColor, String customFont, String tourneyImg,
            String outlineColor, int outlineThickness, boolean sponsors, String customFighterOne,String customFighterTwo,
            int shadowThickness){

        game = gamePlayed;
        tourneyName = tournament;
        date = time;
        player1 = playerOne;
        player2 = playerTwo;
        this.customLogo = customLogo;
        int versionVal1;
        int versionVal2;
        int versionVal3;
        int versionVal4;
        this.fontColor = fontColor;
        customTourneyString = tourneyImg;
        this.outlineColor = outlineColor;
        this.outlineThickness = outlineThickness;
        this.sponsors = sponsors;
        this.shadowThickness = shadowThickness;

        //Get character 1 and 2 images from files
        String[] chars1 = character1.split("-");
        String[] chars2 = character2.split("-");

        versionVal1 = getVersionVal(chars1, version);
        versionVal2 = getVersionVal(chars2, version);

        fighter1 = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/" + String.valueOf(versionVal1) + ".png");
        fighter2 = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/" + String.valueOf(versionVal2) + ".png");



        //Get position and size of character 1 and 2
        fighterX1 = getFighterX(chars1[1], version,true);
        fighterX2 = getFighterX(chars2[1], version,false);

        fighterY1 = getFighterY(chars1[1], version);
        fighterY2 = getFighterY(chars2[1], version);

        fighter1 = getResize(fighter1,chars1[1], version);
        fighter2 = getResize(fighter2,chars2[1], version);


        //load custom fighters if applicable
        if(!customFighterOne.trim().equals("")){
            fighter1 = getCustomImage(customFighterOne);
        }
        if(!customFighterTwo.trim().equals("")){
            fighter2 = getCustomImage(customFighterTwo);
        }/*
        if(!customFighterThree.trim().equals("")){
            fighter3 = getCustomImage(customFighterThree);
        }
        if(!customFighterFour.trim().equals("")){
            fighter4 = getCustomImage(customFighterFour);
        }*/

        fighter1 = determineFlip(fighter1,version,chars1);
        fighter2 = determineFlip(fighter2,version,chars2);



        //determine characters 3 and 4, needs fixing to work across all games, not only smash 4
        fighterX3 = 250;
        fighterX4 = 680;

        if(character3.equals("0Nothing")){
            fighter3 = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0blank.png");
            secondary1 = false;
        }else{
            String[] chars3 = character3.split("-");
            versionVal3 = getVersionVal(chars3, version);
            fighter3 = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/" + String.valueOf(versionVal3) + ".png");
            fighterY3 = getFighterY(chars3[1], version);
            fighter3 = determineFlip(fighter3,version,chars3);
        }

        if(character4.equals("0Nothing")){
            fighter4 = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0blank.png");
            secondary2 = false;
        }else{
            String[] chars4 = character4.split("-");
            versionVal4 = getVersionVal(chars4, version);
            fighter4 = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/" + String.valueOf(versionVal4) + ".png");
            fighterY4 = getFighterY(chars4[1], version);
            fighter4 = determineFlip(fighter4,version,chars4);
        }




        getFonts(customFont);

        //get custom gradient images if applicable
        if(customGrad1.trim().equals("")){
            topGradientImage = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/01GRADIENT.png");
        }else{
            topGradientImage = getCustomImage(customGrad1);
        }
        if(customGrad2.trim().equals("")){
            middleGradientImage = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0GRADIENT.png");
        }else{
            middleGradientImage = getCustomImage(customGrad2);
        }
        if(customGrad3.trim().equals("")){
            bottomGradientImage = get.flipImage(topGradientImage,'v');
        }else{
            bottomGradientImage = getCustomImage(customGrad3);
        }


        gameLogo = getGameLogo(version);



        BufferedImage thumbnail = new BufferedImage(1280,720,BufferedImage.TYPE_4BYTE_ABGR);
        draw(thumbnail.getGraphics());
        return thumbnail;
    }

    private void draw(Graphics g){
        doCalculations();
        drawAllImages(g);
        drawAllText(g);
        //takeScreenShot(g);
    }

    private void doCalculations(){
        if(!first){
            first = true;

            //get custom logo image if applicable
            if(customLogo.trim().equals("")){
                logo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0gooshilogo.png");
            }else{
                logo = getCustomImage(customLogo);
            }


            applyShadow(shadowThickness);

            //get sponsor images if applicable
            if(sponsors) {
                playerOneSponsor = findSponsor(player1,true);
                playerTwoSponsor = findSponsor(player2,false);
            }else{
                playerOneSponsor = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0blank.png");
                playerTwoSponsor = playerOneSponsor;
            }

            //Need to make doubles work with every version               |
            //These two if statements make doubles work with WiiU & 3DS  V
            if (secondary1) {
                pos1 = 50;
                fighter1 = get.getSizedImg(fighter1,350,350);
                fighter3 = get.getSizedImg(fighter3,350,350);
            }
            if (secondary2) {
                pos2 = 150;
                fighter2 = get.getSizedImg(fighter2,350,350);
                fighter4 = get.getSizedImg(fighter4,350,350);
            }

        }
    }

    private void drawAllImages(Graphics g){
        g.drawImage(middleGradientImage,0,200,null);//Middle gradient
        get.drawImageInCenter(playerOneSponsor,0,200,640,320,g);//Sponsor for player 1
        get.drawImageInCenter(playerTwoSponsor,640,200,640,320,g);//Sponsor for player 2
        g.drawImage(fighter3,fighterX3,fighterY3,null);//Player 1's secondary character(optional)
        g.drawImage(fighter4,fighterX4,fighterY4,null);//Player 2's secondary character(optional)
        g.drawImage(fighter1,fighterX1-pos1,fighterY1,null);//Player 1's first character
        g.drawImage(fighter2,fighterX2+pos2,fighterY2,null);//Player 2's first character
        g.drawImage(topGradientImage,0,0,null);//Top gradient
        g.drawImage(bottomGradientImage,0,520,null);//Bottom gradient(optional)
        get.drawImageSizedInCenter(logo,553,0,170,170,g);//0 for nhs 5 for gg 20 for msu
        g.drawImage(gameLogo,0,560,null);
    }

    private void drawAllText(Graphics g){
        g.setColor(Color.decode(fontColor));
        g.setFont(kAX);

        //Draw the text for the tournament name, or the image for the tournament if given
        get.drawText(date,fontColor,outlineColor,865,550,414,165,g,3,Integer.MAX_VALUE,0,kAX,'4');
        if(customTourneyString.equals("")) {
            get.drawText(tourneyName, fontColor, outlineColor, 395, 550, 470, 165, g, outlineThickness, CST);
        }else{
            BufferedImage customTourneyImage = getCustomImage(customTourneyString);
            get.drawImageSizedInCenter(customTourneyImage,395,550,470,165,g);
        }

        /*
        Make the player1 text size and player2 size match
         */

        //Declare values
        int x1 = 15;
        int y1 = 0;
        int w1 = 543;
        int h1 = 170;
        int x2 = 730;
        int y2 = 0;
        int w2 = 535;
        int h2 = 170;

        //Actually do it
        int maxFontSize1;
        int maxFontSize2;
        int maxFontSize;
        FontMetrics temp = g.getFontMetrics(CST);
        temp = get.getSizedFont(temp,w1,h1,player1,g);
        maxFontSize1 = temp.getFont().getSize();
        temp = get.getSizedFont(g.getFontMetrics(CST),w2,h2,player2,g);
        maxFontSize2 = temp.getFont().getSize();
        maxFontSize = Math.min(maxFontSize1,maxFontSize2);


        //Draw all the strings to the screen
        get.drawText(player1, fontColor, outlineColor,x1,y1,w1,h1,g,outlineThickness,maxFontSize,0,CST);
        get.drawText(player2, fontColor, outlineColor,x2,y2, w2,h2,g,outlineThickness,maxFontSize,0,CST);
        get.drawText(game, fontColor, outlineColor,515,370,250,150,g,(int) (outlineThickness * .9),CST);
        get.drawText("VS", fontColor,outlineColor,515,200,250,170,g,5,Integer.MAX_VALUE,0,BBN);

    }


    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }






    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    //                           Side Classes Below                                  //
    //                                   |                                           //
    //                                   |                                           //
    //                                   V                                           //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////



    private void applyShadow(int shadowThickness){
        fighter1 = get.dropShadow(fighter1,shadowThickness,Color.black,'4');
        fighterX1 = fighterX1 - shadowThickness;
        fighterY1 = fighterY1 - shadowThickness;
        fighter2 = get.dropShadow(fighter2,shadowThickness,Color.black,'4');
        fighter2 = get.flipImage(fighter2,'h');
        fighterX2 = fighterX2 - shadowThickness;
        fighterY2 = fighterY2 - shadowThickness;
        fighter3 = get.dropShadow(fighter3,shadowThickness,Color.black,'4');
        fighterX3 = fighterX3 - shadowThickness;
        fighterY3 = fighterY3 - shadowThickness;
        fighter4 = get.dropShadow(fighter4,shadowThickness,Color.black,'4');
        fighter4 = get.flipImage(fighter4,'h');
        fighterX4 = fighterX4 - shadowThickness;
        fighterY4 = fighterY4 - shadowThickness;
    }

    private void getFonts(String customFont) {
        BBN = get.getFont("https://bhattarai333.github.io/Websites/Resources/Fonts/BebasNeue.ttf",1000);
        kAX = get.getFont("https://bhattarai333.github.io/Websites/Resources/Fonts/KOMIKAX_.ttf",42);
        ACN = get.getFont("https://bhattarai333.github.io/Websites/Resources/Fonts/AmericanCaptain.ttf",1000);
        CST = getNewFont(customFont);
    }

    private BufferedImage determineFlip(BufferedImage image, String version, String[] chars){
        BufferedImage output = image;
        String character = chars[1];
        switch (version){
            case "WiiUFull":
                output = getWiiUFullFlip(image,character);
                break;
            case "WiiU":
                output = getWiiUFlip(image,character);
                break;
            case "Melee":
                output = getMeleeFlip(image,character);
                break;
            case "S3":
                output = getShrekFlip(image,character);
                break;
            case "Brawl":
                output = getBrawlFlip(image,character);
                break;
        }
        return output;
    }

    private BufferedImage getBrawlFlip(BufferedImage image, String character) {
        boolean flip = false;
        switch (character){
            case "Fox":
                flip = true;
                break;
            case "Lucas":
                flip = true;
                break;
            case "Mario":
                flip = true;
                break;
            case "Pikachu":
                flip = true;
                break;
            case "Peach":
                flip = true;
                break;
            case "ROB":
                flip = true;
                break;
            case "Sonic":
                flip = true;
                break;
            case "Ganondorf":
                flip = true;
                break;
        }
        if(flip){
            return get.flipImage(image,'h');
        }

        return image;
    }

    private BufferedImage getShrekFlip(BufferedImage image, String str){
        boolean flip = false;
        switch(str){
            case "Gingy":
                flip = true;
                break;
            case "Puss":
                flip = true;
                break;
            case "Anthrax":
                flip = true;
                break;
            default: break;

        }
        if(flip){
            return get.flipImage(image,'h');
        }

        return image;
    }

    private BufferedImage getMeleeFlip(BufferedImage image, String str){
        switch (str){
            case "Fox":
                break;
        }
        return image;
    }

    private BufferedImage getWiiUFullFlip(BufferedImage image, String str){
        boolean flip = false;
        switch(str){
            case "Bayonetta":
                break;
            case "Captain Falcon":
                break;
            case "Cloud": flip = true;
                break;
            case "DDD":
                break;
            case "Diddy Kong":
                break;
            case "Donkey Kong":
                break;
            case "Doctor Mario": flip = true;
                break;
            case "Duck Hunt":
                break;
            case "Falco":
                break;
            case "Fox": flip = true;
                break;
            case "Mr. Game and Watch": flip = true;
                break;
            case "Ganondorf":
                break;
            case "Greninja":
                break;
            case "Ike": flip = true;
                break;
            case "Corrin":
                break;
            case "Kirby": flip = true;
                break;
            case "Bowser":
                break;
            case "Bowser Jr.": flip = true;
                break;
            case "Link": flip = true;
                break;
            case "Charizard": flip = true;
                break;
            case "Lucario":
                break;
            case "Lucas": flip = true;
                break;
            case "Lucina":
                break;
            case "Luigi": flip = true;
                break;
            case "Mario": flip = true;
                break;
            case "Marth": flip = true;
                break;
            case "Meta Knight": flip = true;
                break;
            case "MewTwo": flip = true;
                break;
            case "Mii Brawler":
                break;
            case "Mii Gunner": flip = true;
                break;
            case "Mii Sword":
                break;
            case "Villager":
                break;
            case "Ness":
                break;
            case "Pacman": flip = true;
                break;
            case "Palutena":
                break;
            case "Peach":
                break;
            case "Pikachu": flip = true;
                break;
            case "Olimar": flip = true;
                break;
            case "Pit": flip = true;
                break;
            case "Dank Pit":
                break;
            case "Puff": flip = true;
                break;
            case "Robin": flip = true;
                break;
            case "R.O.B.":
                break;
            case "Megaman":
                break;
            case "Rosalina & Luma": flip = true;
                break;
            case "Roy": flip = true;
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
            case "Zamus": flip = true;
                break;
            case "Toon Link": flip = true;
                break;
            case "Wario":
                break;
            case "Wii Fit": flip = true;
                break;
            case "Yoshi":
                break;
            case "Zelda":
                break;
            case "Little Mac":
                break;
        }
        if(flip){
            return get.flipImage(image,'h');
        }

        return image;
    }

    private BufferedImage getWiiUFlip(BufferedImage image, String str){
        boolean flip = false;
        switch(str){
            case "Bayonetta":
                break;
            case "Captain Falcon":
                break;
            case "Cloud": flip = true;
                break;
            case "DDD":
                break;
            case "Diddy Kong":
                break;
            case "Donkey Kong":
                break;
            case "Doctor Mario": flip = true;
                break;
            case "Duck Hunt":
                break;
            case "Falco":
                break;
            case "Fox": flip = true;
                break;
            case "Mr. Game and Watch": flip = true;
                break;
            case "Ganondorf":
                break;
            case "Greninja":
                break;
            case "Ike": flip = true;
                break;
            case "Corrin":
                break;
            case "Kirby": flip = true;
                break;
            case "Bowser":
                break;
            case "Bowser Jr.":
                break;
            case "Link": flip = true;
                break;
            case "Charizard": flip = true;
                break;
            case "Lucario":
                break;
            case "Lucas": flip = true;
                break;
            case "Lucina":
                break;
            case "Luigi": flip = true;
                break;
            case "Mario": flip = true;
                break;
            case "Marth": flip = true;
                break;
            case "Meta Knight": flip = true;
                break;
            case "MewTwo": flip = true;
                break;
            case "Mii Brawler":
                break;
            case "Mii Gunner": flip = true;
                break;
            case "Mii Sword":
                break;
            case "Villager":
                break;
            case "Ness":
                break;
            case "Pacman": flip = true;
                break;
            case "Palutena":
                break;
            case "Peach":
                break;
            case "Pikachu": flip = true;
                break;
            case "Olimar": flip = true;
                break;
            case "Pit": flip = true;
                break;
            case "Dank Pit":
                break;
            case "Puff": flip = true;
                break;
            case "Robin": flip = true;
                break;
            case "R.O.B.":
                break;
            case "Megaman":
                break;
            case "Rosalina & Luma": flip = true;
                break;
            case "Roy": flip = true;
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
            case "Zamus": flip = true;
                break;
            case "Toon Link": flip = true;
                break;
            case "Wario":
                break;
            case "Wii Fit": flip = true;
                break;
            case "Yoshi":
                break;
            case "Zelda":
                break;
            case "Little Mac":
                break;
        }
        if(flip){
            return get.flipImage(image,'h');
        }

        return image;
    }

    private int getWiiUFighterX(String str, Boolean ff){
        if(str.equals("memes")){
            System.out.println("hi");//only typed this out so I wouldn't get an error, delete if necessary
        }
        int c;
        if(ff){
            c = 50;
        }else{
            c = 780;
        }
        return c;
    }

    private int getWiiUFighterY(String str){
        int c;
        switch(str){
            case "Mario": c = 180;
                break;
            case "Luigi": c = 180;
                break;
            case "Meta Knight": c = 200;
                break;



            default: c = 180;
                break;
        }
        return c;
    }

    private BufferedImage getBrawlResize(BufferedImage img, String str){
        switch(str){
            case "Bowser": img = get.getSizedImg(img,502,427);
                break;
            case "Captain Falcon": img = get.getSizedImg(img,314,576);
                break;
            case "Falco": img = get.getSizedImg(img, 250,500);
                break;
            case "Fox": img = get.getSizedImg(img,250,500);
                break;
            case "Meta Knight": img = get.getSizedImg(img,530,328);
                break;



            /*default: img = get.getSizedImg(img,502,427);
                break;*/

        }
        return img;
    }

    private int getBrawlFighterX(String str,Boolean firstFighter){
        int c;
        int defaultInt = 20;

        switch (str){
            case "Bowser": c = -20;
                break;
            case "Captain Falcon": c = 70;
                break;
            case "Diddy Kong": c = 30;
                break;
            case "Fox": c = 110;
                break;
            case "Falco": c = 110;
                break;
            case "Ike": c = 30;
                break;
            case "Link": c = 110;
                break;
            case "Luigi": c = 110;
                break;
            case "Lucas": c = 150;
                break;
            case "Mario": c = 30;
                break;
            case "Marth": c = 130;
                break;
            case "Ness": c = 90;
                break;
            case "Meta Knight": c = -10;
                break;


            default: c = defaultInt;
                break;
        }


        if(!firstFighter){
            c = c + 816;
        }
        return c;
    }

    private int getBrawlFighterY(String str){
        int c;
        switch (str){
            case "Bowser": c = 170;
                break;
            case "Donkey Kong": c = 120;
                break;
            case "Captain Falcon": c = 200;
                break;
            case "Fox": c = 180;
                break;
            case "Falco": c = 190;
                break;
            case "Ganondorf": c = 190;
                break;
            case "Diddy Kong": c = 150;
                break;
            case "Ice Climbers": c = 140;
                break;
            case "King DeDeDe": c = 190;
                break;
            case "Lucario": c = 190;
                break;
            case "Olimar": c = 190;
                break;


            default: c = 200;
                break;
        }
        return c;
    }

    private int getMeleeFighterX(String str, Boolean firstFighter){
        int c;
        int defaultInt;
        if(firstFighter){
            defaultInt = 50;
        }else{
            defaultInt = 780;
        }
        switch(str){
            case "Bowser": c = -40;
                break;
            case "Donkey Kong": c = -115;
                break;
            case "Doctor Mario": c = -100;
                break;
            case "Falco": c = -100;
                break;
            case "Captain Falcon": c = -100;
                break;
            case "Fox": c  = -100;
                break;
            case "Mr. Game & Watch": c = -100;
                break;
            case "Ganondorf": c = -100;
                break;
            case "Ice Climbers": c = -100;
                break;
            case "Kirby": c = -100;
                break;
            case "Link": c = -100;
                break;
            case "Luigi": c = -100;
                break;
            case "Mario": c = -100;
                break;
            case "Marth": c = -140;
                break;
            case "MewTwo": c = -100;
                break;
            case "Ness": c = -100;
                break;
            case "Peach": c = 0;
                break;
            case "Pichu": c = -100;
                break;
            case "Pikachu": c = -100;
                break;
            case "Jigglypuff": c = -100;
                break;
            case "Roy": c = -100;
                break;
            case "Samus": c = -100;
                break;
            case "Sheik": c = -100;
                break;
            case "Yoshi": c = -100;
                break;
            case "Young Link": c = -100;
                break;
            case "Zelda": c = 0;
                break;
            default: c = defaultInt;
                break;
        }
        if(!firstFighter){
            c = c +780;
        }
        return c;
    }

    private BufferedImage getMeleeResize(BufferedImage img, String str){
        switch(str){
            case "Bowser": img = get.getSizedImg(img,600,600);
                break;
            case "Donkey Kong":  img = get.getSizedImg(img,700,700);
                break;
            case "Doctor Mario": img = get.getSizedImg(img,700,700);
                break;
            case "Falco": img = get.getSizedImg(img,700,700);
                break;
            case "Captain Falcon": img = get.getSizedImg(img,700,700);
                break;
            case "Fox": img = get.getSizedImg(img,700,700);
                break;
            case "Mr. Game & Watch": img = get.getSizedImg(img,700,700);
                break;
            case "Ganondorf": img = get.getSizedImg(img,700,700);
                break;
            case "Ice Climbers": img = get.getSizedImg(img,700,700);
                break;
            case "Kirby": img = get.getSizedImg(img,700,700);
                break;
            case "Link": img = get.getSizedImg(img,700,700);
                break;
            case "Luigi": img = get.getSizedImg(img,700,700);
                break;
            case "Mario": img = get.getSizedImg(img,700,700);
                break;
            case "Marth": img = get.getSizedImg(img,940,940);
                break;
            case "MewTwo": img = get.getSizedImg(img,600,600);
                break;
            case "Ness": img = get.getSizedImg(img,700,700);
                break;
            case "Peach": img = get.getSizedImg(img,500,500);
                break;
            case "Pichu": img = get.getSizedImg(img,700,700);
                break;
            case "Pikachu": img = get.getSizedImg(img,700,700);
                break;
            case "Jigglypuff": img = get.getSizedImg(img,700,700);
                break;
            case "Roy": img = get.getSizedImg(img,700,700);
                break;
            case "Samus": img = get.getSizedImg(img,700,700);
                break;
            case "Sheik": img = get.getSizedImg(img,700,700);
                break;
            case "Yoshi": img = get.getSizedImg(img,700,700);
                break;
            case "Young Link": img = get.getSizedImg(img,700,700);
                break;
            case "Zelda": img = get.getSizedImg(img,500,500);
                break;
        }
        return img;
    }

    private int getMeleeFighterY(String str){
        int c;
        switch(str){
            case "Bowser": c = 80;
                break;
            case "Donkey Kong": c = 50;
                break;
            case "Doctor Mario": c = 30;
                break;
            case "Falco": c = 40;
                break;
            case "Captain Falcon": c = 40;
                break;
            case "Fox": c  = 40;
                break;
            case "Mr. Game & Watch": c = 40;
                break;
            case "Ganondorf": c = 70;
                break;
            case "Ice Climbers": c = 40;
                break;
            case "Kirby": c = 40;
                break;
            case "Link": c = 40;
                break;
            case "Luigi": c = 40;
                break;
            case "Mario": c = 40;
                break;
            case "Marth": c = -18;
                break;
            case "MewTwo": c = 150;
                break;
            case "Ness": c = 40;
                break;
            case "Peach": c = 180;
                break;
            case "Pichu": c = 40;
                break;
            case "Pikachu": c = 40;
                break;
            case "Jigglypuff": c = 60;
                break;
            case "Roy": c = 41;
                break;
            case "Samus": c = 40;
                break;
            case "Sheik": c = 80;
                break;
            case "Yoshi": c = 30;
                break;
            case "Young Link": c = 35;
                break;
            case "Zelda": c = 170;
                break;
            default: c = 40;
                break;
        }
        return c;
    }

    private int getCharVal(String str){
        int c = 0;
        switch(str){
            case "Bayonetta": c = 1;
                break;
            case "Captain Falcon": c = 2;
                break;
            case "Cloud": c = 3;
                break;
            case "DDD": c = 4;
                break;
            case "Diddy Kong": c = 5;
                break;
            case "Donkey Kong": c = 6;
                break;
            case "Doctor Mario": c = 7;
                break;
            case "Duck Hunt": c = 8;
                break;
            case "Falco": c = 9;
                break;
            case "Fox": c = 10;
                break;
            case "Mr. Game and Watch": c = 11;
                break;
            case "Ganondorf": c = 12;
                break;
            case "Greninja": c = 13;
                break;
            case "Ike": c = 14;
                break;
            case "Corrin": c = 15;
                break;
            case "Kirby": c = 16;
                break;
            case "Bowser": c = 17;
                break;
            case "Bowser Jr.": c = 18;
                break;
            case "Link": c = 19;
                break;
            case "Charizard": c = 20;
                break;
            case "Lucario": c = 21;
                break;
            case "Lucas": c = 22;
                break;
            case "Lucina": c = 23;
                break;
            case "Luigi": c = 24;
                break;
            case "Mario": c = 25;
                break;
            case "Marth": c = 26;
                break;
            case "Meta Knight": c = 27;
                break;
            case "MewTwo": c = 28;
                break;
            case "Mii Brawler": c = 29;
                break;
            case "Mii Gunner": c = 30;
                break;
            case "Mii Sword": c = 31;
                break;
            case "Villager": c = 32;
                break;
            case "Ness": c = 33;
                break;
            case "Pacman": c = 34;
                break;
            case "Palutena": c = 35;
                break;
            case "Peach": c = 36;
                break;
            case "Pikachu": c = 37;
                break;
            case "Olimar": c = 38;
                break;
            case "Pit": c = 39;
                break;
            case "Dank Pit": c = 40;
                break;
            case "Puff": c = 41;
                break;
            case "Robin": c = 42;
                break;
            case "R.O.B.": c = 43;
                break;
            case "Megaman": c = 44;
                break;
            case "Rosalina & Luma": c = 45;
                break;
            case "Roy": c = 46;
                break;
            case "Ryu": c = 47;
                break;
            case "Samus": c = 48;
                break;
            case "Sheik": c = 49;
                break;
            case "Shulk": c = 50;
                break;
            case "Sonic": c = 51;
                break;
            case "Zamus": c = 52;
                break;
            case "Toon Link": c = 53;
                break;
            case "Wario": c = 54;
                break;
            case "Wii Fit": c = 55;
                break;
            case "Yoshi": c = 56;
                break;
            case "Zelda": c = 57;
                break;
            case "Little Mac": c = 58;
                break;


        }
        return c;
    }

    private int getBrawlCharVal(String str){
        int c = 0;
        switch (str){
            case "Bowser": c = 1;
                break;
            case "Captain Falcon": c = 2;
                break;
            case "Diddy Kong": c = 3;
                break;
            case "Donkey Kong": c = 4;
                break;
            case "Falco": c = 5;
                break;
            case "Fox": c = 6;
                break;
            case "Ganondorf": c = 7;
                break;
            case "Ice Climbers": c = 8;
                break;
            case "Ike": c = 9;
                break;
            case "Jigglypuff": c = 10;
                break;
            case "King DeDeDe": c = 11;
                break;
            case "Kirby": c = 12;
                break;
            case "Link": c = 13;
                break;
            case "Lucario": c = 14;
                break;
            case "Lucas": c = 15;
                break;
            case "Luigi": c = 16;
                break;
            case "Mario": c = 17;
                break;
            case "Marth": c = 18;
                break;
            case "Meta Knight": c = 19;
                break;
            case "Ness": c = 20;
                break;
            case "Olimar": c = 21;
                break;
            case "Peach": c = 22;
                break;
            case "Pikachu": c = 23;
                break;
            case "Pit": c = 24;
                break;
            case "Pokemon Trainer": c = 25;
                break;
            case "ROB": c = 26;
                break;
            case "Samus": c = 27;
                break;
            case "Sheik": c = 28;
                break;
            case "Snake": c = 29;
                break;
            case "Sonic": c = 30;
                break;
            case "Toon Link": c = 31;
                break;
            case "Wolf": c = 32;
                break;
            case "Yoshi": c = 33;
                break;
            case "Zelda": c = 34;
                break;
            case "Wario": c = 35;
                break;
        }
        return c;
    }

    private int getMeleeCharVal(String str){
        int c = 0;
        switch(str){
            case "Bowser": c = 1;
                break;
            case "Donkey Kong": c = 2;
                break;
            case "Doctor Mario": c = 3;
                break;
            case "Falco": c = 4;
                break;
            case "Captain Falcon": c = 5;
                break;
            case "Fox": c  = 6;
                break;
            case "Mr. Game & Watch": c = 7;
                break;
            case "Ganondorf": c = 8;
                break;
            case "Ice Climbers": c = 9;
                break;
            case "Kirby": c = 10;
                break;
            case "Link": c = 11;
                break;
            case "Luigi": c = 12;
                break;
            case "Mario": c = 13;
                break;
            case "Marth": c = 14;
                break;
            case "MewTwo": c = 15;
                break;
            case "Ness": c = 16;
                break;
            case "Peach": c = 17;
                break;
            case "Pichu": c = 18;
                break;
            case "Pikachu": c = 19;
                break;
            case "Jigglypuff": c = 20;
                break;
            case "Roy": c = 21;
                break;
            case "Samus": c = 22;
                break;
            case "Sheik": c = 23;
                break;
            case "Yoshi": c = 24;
                break;
            case "Young Link": c = 25;
                break;
            case "Zelda": c = 26;
                break;
        }
        return c;
    }

    private int getRivalCharVal(String str){
        int c;
        switch(str){
            case "Zetterburn": c = 1;
                break;
            case "Orcane": c = 2;
                break;
            case "Wrastor": c = 3;
                break;
            case "Kragg": c = 4;
                break;
            case "Forsburn": c = 5;
                break;
            case "Maypul": c = 6;
                break;
            case "Absa": c = 7;
                break;
            case "Etalus": c = 8;
                break;
            default: c = 1;
                break;
        }
        return c;
    }

    private int getShrekCharVal(String str){
        int c = 0;
        switch(str){
            case "Shrek": c = 1;
                break;
            case "Donkey": c = 2;
                break;
            case "Puss": c = 3;
                break;
            case "Gingy": c = 4;
                break;
            case "Princess Fiona": c = 5;
                break;
            case "Ogre Fiona": c = 6;
                break;
            case "Pinocchio": c = 7;
                break;
            case "Prince Charming": c = 8;
                break;
            case "Red": c = 9;
                break;
            case "Black Knight": c = 10;
                break;
            case "Wolf": c = 11;
                break;
            case "Anthrax": c = 12;
                break;
            case "Cyclops": c = 13;
                break;
            case "Robin Hood": c = 14;
                break;
            case "G~Nome": c = 15;
                break;
            case "Dronkey": c = 16;
                break;
            case "Quasimodo": c = 17;
                break;
            case "Luna": c = 18;
                break;
            case "Captain Hook": c = 19;
                break;
            case "Humptey Dumptey": c = 20;
                break;
        }
        return c;
    }



    private int getVersionVal(String[] chars1, String version){
        int versionVal1 = 1;
        int charVal1;
        switch (version) {
            case "WiiU":
            case "3DS":
                charVal1 = getCharVal(chars1[1]);
                if (charVal1 == 1) {
                    versionVal1 = 1 + Integer.parseInt(chars1[0]);
                } else {
                    versionVal1 = ((charVal1 - 1) * 8) + 1 + Integer.parseInt(chars1[0]);
                }
                return versionVal1;
            case "Melee":
                charVal1 = getMeleeCharVal(chars1[1]);
                if (charVal1 == 1) {
                    versionVal1 = 1 + Integer.parseInt(chars1[0]);
                } else {
                    versionVal1 = ((charVal1 - 1) * 6) + Integer.parseInt(chars1[0]);
                }
                versionVal1 = versionVal1 + 483;
                return versionVal1;
            case "Brawl":
                charVal1 = getBrawlCharVal(chars1[1]);
                if(charVal1 == 1){
                    versionVal1 = Integer.parseInt(chars1[0]);
                }else{
                    versionVal1 = ((charVal1 - 1) * 6) + Integer.parseInt(chars1[0]);
                }
                versionVal1 = versionVal1 + 707;
                return versionVal1;
            case "PM":

                break;
            case "64":

                break;
            case "RoA":
                charVal1 = getRivalCharVal(chars1[1]);
                if (charVal1 == 1) {
                    versionVal1 = Integer.parseInt(chars1[0]);
                } else {
                    versionVal1 = ((charVal1 - 1) * 6) + Integer.parseInt(chars1[0]);
                }
                versionVal1 = versionVal1 + 639;
                return versionVal1;
            case "Pokken":

                break;
            case "S3":
                charVal1 = getShrekCharVal(chars1[1]);

                if (charVal1 == 1) {
                    versionVal1 = 1;
                } else {
                    versionVal1 = charVal1;
                }
                versionVal1 = versionVal1 + 686;
                break;

            case "WiiUFull":
                charVal1 = getCharVal(chars1[1]);
                if (charVal1 == 1) {
                    versionVal1 = 1 ;
                } else {
                    versionVal1 = charVal1;
                }
                versionVal1 = versionVal1 + 922;
                break;
        }

        return versionVal1;
    }

    private int getRivalFighterX(String str, Boolean firstFighter){
        int c;
        switch (str){

            default: c = 75;
        }
        if (firstFighter) {
            return c;
        }
        c = c + 730;
        return c;
    }



    private int getRivalFighterY(String str){
        int c;
        switch(str){

            default: c = 180;
        }
        return c;
    }

    private int getShrekFighterX(String str, Boolean firstFighter){
        int c;
        switch (str){
            case "Shrek": c = 40;
                break;
            case "Donkey": c = 40;
                break;
            case "Anthrax": c = -10;
                break;
            case "Robin Hood": c = 10;
                break;
            case "Gingy": c = 30;
                break;
            default: c = 50;
                break;
        }
        if (firstFighter) {
            return c;
        }
        c = c + 700;
        return c;
    }

    private int getShrekFighterY(String str){
        int c;
        switch (str){
            case "Shrek": c = 120;
                break;
            case "Donkey": c = 120;
                break;
            case "Anthrax": c = 70;
                break;
            case "Robin Hood": c = 70;
                break;
            case "Pinocchio": c = 60;
                break;
            case "Quasimodo": c = 80;
                break;
            case "Luna": c = 80;
                break;
            case "Wolf": c = 80;
                break;
            case "Captain Hook": c = 80;
                break;

            default: c = 120;
                break;
        }
        return c;
    }

    private BufferedImage getShrekResize(BufferedImage img, String str){
        switch(str){
            case "Shrek": img = get.getSizedImg(img,600,600);
                break;
            case "Donkey": img = get.getSizedImg(img,600,600);
                break;
            case "Anthrax": img = get.getSizedImg(img,600,600);
                break;

            default: img = get.getSizedImg(img,600,600);
                break;
        }
        return img;
    }

    private int getFighterY(String str, String version){
        str = str.trim();
        switch (version) {
            case "WiiU":
            case "3DS":
                return getWiiUFighterY(str);
            case "Melee":
                return getMeleeFighterY(str);
            case "Brawl":
                return getBrawlFighterY(str);
            case "RoA":
                return getRivalFighterY(str);
            case "S3":
                return getShrekFighterY(str);
            case "WiiUFull":
                return getWiiUFullFighterY(str);
        }
        return 1;
    }



    private int getFighterX(String str, String version, Boolean firstFighter){
        switch (version) {
            case "WiiU":
            case "3DS":
                return getWiiUFighterX(str, firstFighter);
            case "Melee":
                return getMeleeFighterX(str, firstFighter);
            case "Brawl":
                return getBrawlFighterX(str, firstFighter);
            case "RoA":
                return getRivalFighterX(str, firstFighter);
            case "S3":
                return getShrekFighterX(str, firstFighter);
            case "WiiUFull":
                return getWiiUFullFighterX(str, firstFighter);
        }
        return 1;
    }

    private BufferedImage getResize(BufferedImage img, String str, String version){
        switch (version) {
            case "WiiU":
            case "3DS":

                break;
            case "Melee":
                return getMeleeResize(img, str);
            case "Brawl":
                return getBrawlResize(img,str);
            case "S3":
                return getShrekResize(img, str);
            case "WiiUFull":
                return getWiiUFullResize(img,str);
        }
        return img;
    }

    private BufferedImage getWiiUFullResize(BufferedImage img, String str) {
        BufferedImage outputImage = img;
        switch (str){
            case "Luigi":
                outputImage = get.getScaledImg(img,120);
                break;
            case "Peach":
                outputImage = get.getScaledImg(img,200);
                break;
            case "Rosalina & Luma":
                outputImage = get.getScaledImg(img,180);
                break;
            case "Little Mac":
                outputImage = get.getScaledImg(img,180);
                break;
            case "Link":
                outputImage = get.getScaledImg(img,180);
                break;
            case "Zelda":
                outputImage = get.getScaledImg(img,180);
                break;
            case "Sheik":
                outputImage = get.getScaledImg(img,200);
                break;
            case "Ganondorf":
                outputImage = get.getScaledImg(img,250);
                break;
            case "Samus":
                outputImage = get.getScaledImg(img,200);
                break;
            case "Zamus":
                outputImage = get.getScaledImg(img,200);
                break;
        }
        return outputImage;
    }

    private int getWiiUFullFighterX(String str, Boolean firstFighter){
        int output = 0;
        switch (str){
            case "Luigi":
                output = -40;
                break;
            case "Peach":
                output = -250;
                break;
            case "Rosalina & Luma":
                output = -200;
                break;
            case "Little Mac":
                output = -200;
                break;
            case "Link":
                output = -345;
                break;
            case "Zelda":
                output = -215;
                break;
            case "Sheik":
                output = -255;
                break;
            case "Ganondorf":
                output = -330;
                break;
            case "Toon Link":
                output = -10;
                break;
            case "Samus":
                output = -200;
                break;
            case "Zamus":
                output = -200;
                break;

        }
        if(!firstFighter){
            output += 760;
            switch(str){
                case "Link":
                    output += 300;
                    break;
                case "Ganondorf":
                    output -= 100;
                    break;
                case "Samus":
                    output -= 100;
                    break;
            }
        }
        return output;
    }

    private int getWiiUFullFighterY(String str) {
        int output = 180;
        switch (str){
            case "Bowser":
                output = 120;
                break;
            case "Yoshi":
                output = 200;
                break;
            case "Donkey Kong":
                output = 120;
                break;
            case "Diddy Kong":
                output = 130;
                break;
        }
        return output;
    }

    private BufferedImage getCustomImage(String path){
        BufferedImage img = null;
        String s = get.getProgramPath();
        path = path.trim();
        String s2 = s + "\\Custom\\" + path + ".png";
        try{
            System.out.println(s2);
            img = ImageIO.read(new File(s2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    private Font getNewFont(String customFont){
        switch (customFont) {
            case "":
                return ACN;
            case "Nothing":
                return ACN;
            case "Font Name":
                return ACN;
            default:
                return (getCustomFont(customFont));
        }

    }

    private Font getCustomFont(String customFont){
        Font myFont;
        Font myFontReal = null;
        String s = get.getProgramPath();
        String path = customFont;
        path = path.trim();
        String s2 = s + "\\Custom\\" + path + ".ttf";
        try{
            File f = new File(s2);
            InputStream is = new FileInputStream(f);

            myFont = Font.createFont(Font.TRUETYPE_FONT,is);
            myFontReal = myFont.deriveFont(1000f);
        }catch(FontFormatException | IOException e){
            System.out.println(e.getMessage());
        }
        return myFontReal;
    }

    private BufferedImage findSponsor(String playerName,boolean first){
        String prefix = getPrefix(playerName);
        return getSponsor(prefix,first);
    }

    private BufferedImage getSponsor(String prefix,boolean first){
        switch (prefix){
            case "C9":
                return get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0c9.png");
            case "EG":
                BufferedImage img = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0eg.png");
                if(!first){img = get.flipImage(img,'h');}
                return img;
            case "TSM":
                return get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0tsm.png");
        }
        return get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0blank.png");
    }

    private String getPrefix(String playerName){
        playerName = playerName.toLowerCase();
        if(playerName.contains("c9 |")){
            return "C9";
        }else if(playerName.contains("eg |")){
            return "EG";
        }else if(playerName.contains("tsm |")){
            return "TSM";
        }

        return "";
    }

    private BufferedImage getGameLogo(String version){
        switch (version) {
            case "WiiUFull":
            case "WiiU":
                gameLogo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0wiiulogo.png");
                break;
            case "3DS":
                gameLogo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/03dslogo.png");
                break;
            case "Melee":
                gameLogo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0meleelogo.png");
                break;
            case "RoA":
                gameLogo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0roalogo.png");
                break;
            case "PM":
                gameLogo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0pmlogo.png");
                break;
            case "64":
                gameLogo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0s64logo.png");
                break;
            case "Brawl":
                gameLogo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0brawllogo.png");
                break;
            case "S3":
                gameLogo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0shreklogo.png");
                break;
            default:
                gameLogo = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0blank.png");
                break;
        }
        gameLogo = get.getSizedImg(gameLogo,400,150);
        return gameLogo;
    }
}
