import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GooshiOverlay extends JPanel {
    OverlayData od;
    GetResources get = new GetResources();
    Canvas c = new Canvas();
    BufferedImage createFrame(OverlayData o){
        BufferedImage thumbnail = new BufferedImage(1280,720,BufferedImage.TYPE_4BYTE_ABGR);
        od = o;
        draw(thumbnail.getGraphics());
        return thumbnail;
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g){
        drawGradients(g);
        drawText(g);
    }

    private void drawCharacters(Graphics g){
        String[] chars1 = od.firstCharacter.split("-");
        String[] chars2 = od.secondCharacter.split("-");

        BufferedImage fighter1 = getCharacterImage(od.firstCharacter);
        int fighterX1;
        int fighterY1;
        int offset1 = 0;
        int verticalOffset1 = 0;

        BufferedImage fighter2 = getCharacterImage(od.secondCharacter);
        int fighterX2;
        int fighterY2;
        int offset2 = 0;
        int verticalOffset2 = 0;

        BufferedImage fighter3 = getCharacterImage(od.thirdCharacter);
        int fighterX3;
        int fighterY3 = 0;
        int offset3 = 0;
        int verticalOffest3 = 0;

        BufferedImage fighter4 = getCharacterImage(od.fourthCharacter);
        int fighterX4;
        int fighterY4 = 0;
        int offset4 = 0;
        int verticalOffset4 = 0;

        fighterX1 = c.getFighterX(chars1[1], od.gamePlayed,true);
        fighterX2 = c.getFighterX(chars2[1], od.gamePlayed,false);

        fighterY1 = c.getFighterY(chars1[1], od.gamePlayed);
        fighterY2 = c.getFighterY(chars2[1], od.gamePlayed);

        fighter1 = c.getResize(fighter1,chars1[1], od.gamePlayed);
        fighter2 = c.getResize(fighter2,chars2[1], od.gamePlayed);

        boolean secondary1 = true;
        boolean secondary2 = true;

        //load custom fighters if applicable
        if(!od.customFighterOnePath.trim().equals("")){
            fighter1 = c.getCustomImage(od.customFighterOnePath);
        }
        if(!od.customFighterTwoPath.trim().equals("")){
            fighter2 = c.getCustomImage(od.customFighterTwoPath);
        }

        //flip characters so that they're facing the center
        fighter1 = c.determineFlip(fighter1,od.gamePlayed,chars1);
        fighter2 = c.determineFlip(fighter2,od.gamePlayed,chars2);



        //determine characters 3 and 4
        fighterX3 = 250;
        fighterX4 = 680;

        if(od.thirdCharacter.equals("0Nothing")){
            fighter3 = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0blank.png");
            secondary1 = false;
        }else{
            String[] chars3 = od.thirdCharacter.split("-");
            fighterY3 = c.getFighterY(chars3[1], od.gamePlayed);
            fighter3 = c.determineFlip(fighter3,od.gamePlayed,chars3);
        }

        if(od.fourthCharacter.equals("0Nothing")){
            fighter4 = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0blank.png");
            secondary2 = false;
        }else{
            String[] chars4 = od.fourthCharacter.split("-");
            fighterY4 = c.getFighterY(chars4[1], od.gamePlayed);
            fighter4 = c.determineFlip(fighter4,od.gamePlayed,chars4);
        }

        if(secondary1){
            String[] temp = od.thirdCharacter.split("-");
            String char3 = temp[1].trim();
            temp = od.firstCharacter.split("-");
            String char1 = temp[1];
            offset1 = c.getOffSet(od.gamePlayed, od.thirdCharacter, true);
            verticalOffset1 = 50;
            fighter1 = c.getDoublesResize(od.gamePlayed, fighter1, char1);
            fighter3 = c.getDoublesResize(od.gamePlayed, fighter3, char3);
            fighterY3 = c.getDoublesY(od.gamePlayed, char3);
            fighterX3 = c.getDoublesX(od.gamePlayed, char3, true);
        }
        if(secondary2){
            String[] temp = od.fourthCharacter.split("-");
            String char4 = temp[1];
            temp = od.firstCharacter.split("-");
            String char2 = temp[1].trim();
            offset2 = c.getOffSet(od.gamePlayed, char4, false);
            verticalOffset2 = 50;
            fighter2 = c.getDoublesResize(od.gamePlayed, fighter2, char2);
            fighter4 = c.getDoublesResize(od.gamePlayed, fighter4, char4);
            fighterY4 = c.getDoublesY(od.gamePlayed, char4);
            fighterX4 = c.getDoublesX(od.gamePlayed, char4, false);
        }



        fighter1 = get.dropShadow(fighter1,od.getShadowThickness(),Color.black,'4');
        fighterX1 = fighterX1 - od.getShadowThickness();
        fighterY1 = fighterY1 - od.getShadowThickness();
        fighter2 = get.dropShadow(fighter2,od.getShadowThickness(),Color.black,'4');
        fighter2 = get.flipImage(fighter2,'h');
        fighterX2 = fighterX2 - od.getShadowThickness();
        fighterY2 = fighterY2 - od.getShadowThickness();
        fighter3 = get.dropShadow(fighter3,od.getShadowThickness(),Color.black,'4');
        fighterX3 = fighterX3 - od.getShadowThickness();
        fighterY3 = fighterY3 - od.getShadowThickness();
        fighter4 = get.dropShadow(fighter4,od.getShadowThickness(),Color.black,'4');
        fighter4 = get.flipImage(fighter4,'h');
        fighterX4 = fighterX4 - od.getShadowThickness();
        fighterY4 = fighterY4 - od.getShadowThickness();


        if(od.isDoubles()){
            offset3 = 50;
            offset4 = 50;
            verticalOffest3 = -50;
            verticalOffset4 = -50;
        }
        g.drawImage(fighter3,fighterX3 - offset3,fighterY3 + verticalOffest3,null);//Player 1's secondary character(optional)
        g.drawImage(fighter4,fighterX4 + offset4,fighterY4 + verticalOffset4,null);//Player 2's secondary character(optional)
        g.drawImage(fighter1,fighterX1 + offset1,fighterY1 + verticalOffset1,null);//Player 1's first character
        g.drawImage(fighter2,fighterX2 - offset2,fighterY2 + verticalOffset2,null);//Player 2's first character
    }
    private void drawGradients(Graphics g){
        //get custom gradient images if applicable



        if(od.playerOneName.contains("/") && od.playerTwoName.contains("/")){
            od.setDoubles(false);
        }

        System.out.println(od.playerOneName);
        System.out.println(od.playerTwoName);

        BufferedImage topGradientImage;
        BufferedImage middleGradientImage;
        BufferedImage bottomGradientImage;
        if(od.customGradientTopBottomPath.trim().equals("")){
            topGradientImage = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/01GRADIENT.png");
        }else{
            topGradientImage = c.getCustomImage(od.customGradientTopBottomPath);
        }
        if(od.customGradientMiddlePath.trim().equals("")){
            middleGradientImage = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0GRADIENT.png");
        }else{
            middleGradientImage = c.getCustomImage(od.customGradientMiddlePath);
        }
        if(od.customGradientBottomPath.trim().equals("")){
            bottomGradientImage = get.flipImage(topGradientImage,'v');
        }else{
            bottomGradientImage = c.getCustomImage(od.customGradientBottomPath);
        }

        g.drawImage(middleGradientImage,0,200,null);//Middle gradient
        drawCharacters(g);
        g.drawImage(topGradientImage,0,0,null);//Top gradient
        g.drawImage(bottomGradientImage,0,520,null);//Bottom gradient(optional)
    }
    private void drawText(Graphics g){
        Font kAX = get.getFont("https://bhattarai333.github.io/Websites/Resources/Fonts/KOMIKAX_.ttf",42);
        Font ACN = get.getFont("https://bhattarai333.github.io/Websites/Resources/Fonts/AmericanCaptain.ttf",1000);
        System.out.println("REEE" + od.customFontPath);
        Font CST = getNewFont(od.customFontPath,ACN);

        //set colors and font
        g.setColor(Color.decode(od.fontColor));
        g.setFont(kAX);

        get.drawText(od.date,od.fontColor,od.fontOutlineColor,1219,707-10,60,50,g,0,Integer.MAX_VALUE,0,kAX,'1');
        //Draw the text for the tournament name, or the image for the tournament if given
        if(od.tourneyImagePath.equals("")) {
            get.drawText(od.tournamentName, od.fontColor, od.fontOutlineColor, 0,550, 395,165, g, od.getFontOutlineThickness(), CST);
        }else{
            BufferedImage customTourneyImage = c.getCustomImage(od.tourneyImagePath);
            get.drawImageSizedInCenter(customTourneyImage,0,520,395,199,g,'b');
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

        boolean playerOneSponsor = false;
        boolean playerTwoSponsor = false;
        BufferedImage playerOneSponsorImage;
        BufferedImage playerTwoSponsorImage;

        //get sponsor images if applicable
        if(od.useSponsors) {
            playerOneSponsorImage = c.findSponsor(od.playerOneName,true);
            playerTwoSponsorImage = c.findSponsor(od.playerTwoName,false);
            if(!c.getPrefix(od.playerOneName).trim().equals("")){
                playerOneSponsor = true;
                String[] parts = od.playerOneName.split("\\|");
                od.playerOneName = parts[1].trim();
            }
            if(!c.getPrefix(od.playerTwoName).trim().equals("")){
                playerTwoSponsor = true;
                String[] parts = od.playerTwoName.split("\\|");
                od.playerTwoName = parts[1].trim();
            }
        }else{
            playerOneSponsorImage = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0blank.png");
            playerTwoSponsorImage = playerOneSponsorImage;
        }

        if(playerOneSponsor){
            x1 += 138;
            w1 -= 138;
        }
        if(playerTwoSponsor){
            w2 -= 138;
        }

        //Actually do it
        int maxFontSize1;
        int maxFontSize2;
        int maxFontSize;
        FontMetrics temp = g.getFontMetrics(CST);
        temp = get.getSizedFont(temp,w1,h1,od.playerOneName,g);
        maxFontSize1 = temp.getFont().getSize();
        temp = get.getSizedFont(g.getFontMetrics(CST),w2,h2,od.playerTwoName,g);
        maxFontSize2 = temp.getFont().getSize();
        maxFontSize = Math.min(maxFontSize1,maxFontSize2);
        if(!od.bindNameSizes){
            maxFontSize = 100000;
        }


        System.out.println(od.playerOneName);
        System.out.println(od.playerTwoName);

        //Draw all the strings to the screen
        get.drawText(od.playerOneName, od.fontColor, od.fontOutlineColor,x1,y1,w1,h1,g,od.getFontOutlineThickness(),maxFontSize,0,CST);
        get.drawText(od.playerTwoName, od.fontColor, od.fontOutlineColor,x2,y2, w2,h2,g,od.getFontOutlineThickness(),maxFontSize,0,CST);
        get.drawText(od.round, od.fontColor, od.fontOutlineColor, 407, 550, 465, 169, g,(int) (od.getFontOutlineThickness() * .9),CST);

        drawMisc(g,playerOneSponsorImage,playerTwoSponsorImage);
    }

    private Font getNewFont(String customFontPath, Font ACN) {
        switch (customFontPath) {
            case "":
                return ACN;
            case "nothing":
                return ACN;
            case "Nothing":
                return ACN;
            case "Font Name":
                return ACN;
            default:
                return (c.getCustomFont(customFontPath));
        }
    }

    private void drawMisc(Graphics g, BufferedImage playerOneSponsorImage, BufferedImage playerTwoSponsorImage){
        BufferedImage vsImg;
        vsImg = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0vs4.png");

        BufferedImage gameLogo;
        gameLogo = c.getGameLogo(od.gamePlayed);

        get.drawImageSizedInCenter(vsImg,450,210,370,300,g,'b');//VS in the middle
        get.drawImageSizedInCenter(playerOneSponsorImage,3,0,150,170,g,'b');//Sponsor for player 1
        get.drawImageSizedInCenter(playerTwoSponsorImage,1127,0,150,170,g,'b');//Sponsor for player 2
        get.drawImageSizedInCenter(gameLogo,870,550,409,169,g,'b');
    }


    private BufferedImage getCharacterImage(String character) {
        BufferedImage img;
        String[] parts = character.split("-");
        if(character.equalsIgnoreCase("0Nothing")){
            return get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0blank.png");
        }else{
            img = get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/" + c.getVersionVal(parts,od.gamePlayed) + ".png");
        }
        return img;
    }
}
