import Enums.Games;
import Enums.Overlay;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Originally created by Josh Bhattarai on 5/17/2017.
 */
public class CreateLayout {
    private void reval(JFrame f){
        int width = f.getWidth();
        int height = f.getHeight();
        f.revalidate();
        f.repaint();
        f.pack();
        f.setSize(width,height);
    }
    private JFrame window;
    private GetResources get = new GetResources();
    private final int WINDOW_WIDTH = 720;
    private final int WINDOW_HEIGHT = 500;
    private OverlayData od = new OverlayData();
    private Games game = Games.SMASH_WIIU;
    private Overlay overlay = Overlay.DEFAULT_OVERLAY;
    private BufferedImage currentScreen;
    public static void main(String[] args){
        CreateLayout cl = new CreateLayout();
        cl.start();
    }
    private void start(){
        windowStuff();
        layoutStuff();

        reval(window);
        icons();
    }



    private void windowStuff(){
        window = new JFrame("Thumbnail Generator");
        window.setBounds(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(null);
    }
    private void icons() {
        ArrayList<BufferedImage> iconList = new ArrayList<>();
        //add each icon to the list
        iconList.add(get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0SB16.png"));
        iconList.add(get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0SB32.png"));
        iconList.add(get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0SB64.png"));
        iconList.add(get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0SB128.png"));
        iconList.add(get.getImg("https://bhattarai333.github.io/Websites/Resources/Sprites/0SB256.png"));

        window.setIconImages(iconList);
        //Path for the Custom folder
        File f2 = new File("./Custom");

        //If it doesn't exist, create a new folder called Custom and add the following text into the readme.txt
        if (!f2.exists()) {
            //noinspection ResultOfMethodCallIgnored
            new File("./Custom").mkdir();
            String fileStuff = "Only use .png and .ttf files in here, make sure the ";
            fileStuff = fileStuff + "file name is exact or else there will be an error\n";
            fileStuff = fileStuff + "top/bottom gradient is 1280 width x 200 height";
            fileStuff = fileStuff + " middle gradient is 1280 width x 320 height\n";
            fileStuff = fileStuff + "try to use a generally circular logo";
            fileStuff = fileStuff + " fonts must be in .ttf format, use a converter online for .wtf .otf ect.\n";
            fileStuff = fileStuff + "using a double space for text fields will write the text to a new line,";
            fileStuff = fileStuff + " for example: 'Grand Finals' would display as:";
            fileStuff = fileStuff + " Grand Finals";
            fileStuff = fileStuff + " while 'Grand  Finals' would display as:";
            fileStuff = fileStuff + "\nGrand\nFinals\n";
            fileStuff = fileStuff + "Font color will use hex codes https://www.w3schools.com/colors/colors_picker.asp";
            File f3 = new File("./Custom/readme.txt");
            get.writeText(fileStuff, f3.getPath());
        }

    }
    private void layoutStuff(){
        ArrayList<Component> components = new ArrayList<>();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JComboBox<String> characterOneAltBox = new JComboBox<>(new String[] {"0"});
        characterOneAltBox.setLocation(160,270);
        characterOneAltBox.setSize(50,20);
        add(characterOneAltBox,components);

        JComboBox<String> characterTwoAltBox = new JComboBox<>(new String[] {"0"});
        characterTwoAltBox.setLocation(655,270);
        characterTwoAltBox.setSize(50,20);
        add(characterTwoAltBox,components);

        JComboBox<String> secondaryOneAltBox = new JComboBox<>(new String[] {"0"});
        secondaryOneAltBox.setLocation(160,300);
        secondaryOneAltBox.setSize(50,20);
        add(secondaryOneAltBox,components);

        JComboBox<String> secondaryTwoAltBox = new JComboBox<>(new String[] {"0"});
        secondaryTwoAltBox.setLocation(655,300);
        secondaryTwoAltBox.setSize(50,20);
        add(secondaryTwoAltBox,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        Overlay[] overlays = Overlay.values();
        String[] overlayStrings = new String[overlays.length];
        for(int i = 0; i < overlays.length; ++i){
            overlayStrings[i] = overlays[i].toString();
        }
        JComboBox<String> overlayBox = new JComboBox<>(overlayStrings);
        overlayBox.setLocation(0,430);
        overlayBox.setSize(150,20);
        add(overlayBox,components);
        overlayBox.addActionListener(l -> overlayChange(overlayBox));

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JComboBox<String> fighterOneBox = new JComboBox<>(new String[] {"Mario"});
        fighterOneBox.setLocation(0,270);
        fighterOneBox.setSize(150,20);
        fighterOneBox.addActionListener(e -> characterChoiceEvent(fighterOneBox.getSelectedItem().toString(),characterOneAltBox));
        add(fighterOneBox,components);

        JComboBox<String> fighterTwoBox = new JComboBox<>(new String[] {"Mario"});
        fighterTwoBox.setLocation(510,270);
        fighterTwoBox.setSize(140,20);
        fighterTwoBox.addActionListener(e -> characterChoiceEvent(fighterTwoBox.getSelectedItem().toString(),characterTwoAltBox));
        add(fighterTwoBox,components);

        JComboBox<String> secondaryOneBox = new JComboBox<>(new String[] {"0Nothing"});
        secondaryOneBox.setLocation(0,300);
        secondaryOneBox.setSize(150,20);
        secondaryOneBox.addActionListener(e -> characterChoiceEvent(secondaryOneBox.getSelectedItem().toString(),secondaryOneAltBox));
        add(secondaryOneBox,components);

        JComboBox<String> secondaryTwoBox = new JComboBox<>(new String[] {"0Nothing"});
        secondaryTwoBox.setLocation(510,300);
        secondaryTwoBox.setSize(140,20);
        secondaryTwoBox.addActionListener(e -> characterChoiceEvent(secondaryTwoBox.getSelectedItem().toString(),secondaryTwoAltBox));
        add(secondaryTwoBox,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        String[] gameStrings = { "Smash 64", "Smash Melee"/*,"MSU Melee"*/, "Smash Brawl",
                "Smash PM", "Smash 3DS", "Smash WiiU", "Smash WiiU Full(WIP)", "Rivals of Aether","Shrek Super Slam"};
        JComboBox<String> gameList = new JComboBox<>(gameStrings);
        gameList.setSelectedIndex(5);
        gameList.addActionListener(e -> gameChoiceEvent(gameList.getSelectedItem().toString(),fighterOneBox,
                fighterTwoBox,secondaryOneBox,secondaryTwoBox,true));
        gameList.setLocation(0,3);
        gameList.setSize(150,25);
        gameList.setMaximumSize(gameList.getPreferredSize());
        add(gameList,components);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel customTopGradientLabel = new JLabel("Custom Top/Bottom Gradient:");
        customTopGradientLabel.setLocation(153,5);
        customTopGradientLabel.setSize(170,20);
        add(customTopGradientLabel,components);

        JTextField customTopGradientField = new JTextField();
        customTopGradientField.setLocation(320,7);
        customTopGradientField.setSize(100,20);
        add(customTopGradientField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel customMiddleGradientLabel = new JLabel("Custom Middle Gradient:");
        customMiddleGradientLabel.setLocation(420,5);
        customMiddleGradientLabel.setSize(150,20);
        add(customMiddleGradientLabel,components);

        JTextField customMiddleGradientField = new JTextField();
        customMiddleGradientField.setLocation(570,7);
        customMiddleGradientField.setSize(100,20);
        add(customMiddleGradientField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel customBottomGradientLabel = new JLabel("Custom Bottom Gradient:");
        customBottomGradientLabel.setLocation(0,40);
        customBottomGradientLabel.setSize(150,20);
        add(customBottomGradientLabel,components);

        JTextField customBottomGradientField = new JTextField();
        customBottomGradientField.setLocation(150,42);
        customBottomGradientField.setSize(100,20);
        add(customBottomGradientField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel customTourneyImageLabel = new JLabel("Custom Tourney Image:");
        customTourneyImageLabel.setLocation(250,40);
        customTourneyImageLabel.setSize(140,20);
        add(customTourneyImageLabel,components);

        JTextField customTourneyImageField = new JTextField();
        customTourneyImageField.setLocation(390,42);
        customTourneyImageField.setSize(100,20);
        add(customTourneyImageField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel customLogoLabel = new JLabel("Custom Logo:");
        customLogoLabel.setLocation(495,40);
        customLogoLabel.setSize(80,20);
        add(customLogoLabel,components);

        JTextField customLogoField = new JTextField();
        customLogoField.setLocation(580,42);
        customLogoField.setSize(100,20);
        add(customLogoField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel customFontLabel = new JLabel("Custom Font:");
        customFontLabel.setLocation(0,80);
        customFontLabel.setSize(80,20);
        add(customFontLabel,components);

        JTextField customFontField = new JTextField();
        customFontField.setLocation(80,82);
        customFontField.setSize(100,20);
        add(customFontField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel fontColorLabel = new JLabel("Font Color:");
        fontColorLabel.setLocation(190,80);
        fontColorLabel.setSize(80,20);
        add(fontColorLabel,components);

        JTextField fontColorField = new JTextField("#ffffff");
        fontColorField.setLocation(260,82);
        fontColorField.setSize(100,20);
        add(fontColorField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel outlineColorLabel = new JLabel("Outline Color:");
        outlineColorLabel.setLocation(365,80);
        outlineColorLabel.setSize(90,20);
        add(outlineColorLabel,components);

        JTextField outlineColorField = new JTextField("#000000");
        outlineColorField.setLocation(450,82);
        outlineColorField.setSize(100,20);
        add(outlineColorField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel outlineThicknessLabel = new JLabel("Outline Thickness:");
        outlineThicknessLabel.setLocation(550,80);
        outlineThicknessLabel.setSize(120,20);
        add(outlineThicknessLabel,components);

        JTextField outlineThicknessField = new JTextField("5");
        outlineThicknessField.setLocation(660,82);
        outlineThicknessField.setSize(30,20);
        add(outlineThicknessField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton previewButton = new JButton("Preview Colors");
        previewButton.setLocation((WINDOW_WIDTH/2)-75,120);
        previewButton.setSize(150,20);
        previewButton.addActionListener(e -> previewColorsEvent(fontColorField,outlineColorField,previewButton));
        add(previewButton,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel shadowThicknessLabel = new JLabel("Shadow Thickness:");
        shadowThicknessLabel.setLocation(0,160);
        shadowThicknessLabel.setSize(120,20);
        add(shadowThicknessLabel,components);

        JTextField shadowThicknessField = new JTextField("8");
        shadowThicknessField.setLocation(120,162);
        shadowThicknessField.setSize(50,20);
        add(shadowThicknessField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel tournamentNameLabel = new JLabel("Tournament Name:");
        tournamentNameLabel.setLocation(173,160);
        tournamentNameLabel.setSize(120,20);
        add(tournamentNameLabel,components);

        JTextField tournamentNameField = new JTextField();
        tournamentNameField.setLocation(290,162);
        tournamentNameField.setSize(150,20);
        add(tournamentNameField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setLocation(443,160);
        dateLabel.setSize(50,20);
        add(dateLabel,components);

        JTextField dateField = new JTextField();
        dateField.setLocation(480,162);
        dateField.setSize(100,20);
        add(dateField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        JLabel sponsorLabel = new JLabel("Use Sponsors?");
        sponsorLabel.setLocation(590,160);
        sponsorLabel.setSize(100,20);
        add(sponsorLabel,components);

        JCheckBox sponsorBox = new JCheckBox();
        sponsorBox.setSelected(true);
        sponsorBox.setLocation(685,162);
        sponsorBox.setSize(25,25);
        sponsorBox.setSelected(false);
        add(sponsorBox,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel youtubeLabel = new JLabel("YouTube Playlist URL:");
        youtubeLabel.setLocation(100,200);
        youtubeLabel.setSize(200,20);
        add(youtubeLabel,components);

        JTextField youtubeField = new JTextField();
        youtubeField.setLocation(300,202);
        youtubeField.setSize(200,20);
        add(youtubeField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel playerOneLabel = new JLabel("Player 1:");
        playerOneLabel.setLocation(0,240);
        playerOneLabel.setSize(50,20);
        add(playerOneLabel,components);

        JTextField playerOneBox = new JTextField();
        playerOneBox.setLocation(50,242);
        playerOneBox.setSize(100,20);
        add(playerOneBox,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel playerTwoLabel = new JLabel("Player 2:");
        playerTwoLabel.setLocation(570,240);
        playerTwoLabel.setSize(50,20);
        add(playerTwoLabel,components);

        JTextField playerTwoBox = new JTextField();
        playerTwoBox.setLocation(620,242);
        playerTwoBox.setSize(100,20);
        add(playerTwoBox,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel customFighterOneLabel = new JLabel("Custom Fighter:");
        customFighterOneLabel.setLocation(0,330);
        customFighterOneLabel.setSize(100,20);
        add(customFighterOneLabel,components);

        JTextField customFighterOneField = new JTextField();
        customFighterOneField.setLocation(100,332);
        customFighterOneField.setSize(110,20);
        add(customFighterOneField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel customFighterTwoLabel = new JLabel("Custom Fighter:");
        customFighterTwoLabel.setLocation(510,330);
        customFighterTwoLabel.setSize(100,20);
        add(customFighterTwoLabel,components);

        JTextField customFighterTwoField = new JTextField();
        customFighterTwoField.setLocation(602,332);
        customFighterTwoField.setSize(100,20);
        add(customFighterTwoField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel imageLabel = new JLabel();
        imageLabel.setSize(300,150);
        imageLabel.setLocation(210,230);
        add(imageLabel,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel roundLabel = new JLabel("Round:");
        roundLabel.setLocation(562,200);
        roundLabel.setSize(40,20);
        add(roundLabel,components);

        JTextField roundField = new JTextField();
        roundField.setLocation(602,202);
        roundField.setSize(100,20);
        add(roundField,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel bindLabel = new JLabel("Bind Size?");
        bindLabel.setLocation(590,130);
        bindLabel.setSize(100,20);
        add(bindLabel,components);

        JCheckBox bindBox = new JCheckBox();
        bindBox.setLocation(685,130);
        bindBox.setSize(20,20);
        add(bindBox,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton enterButton = new JButton("Enter");
        enterButton.setLocation(WINDOW_WIDTH/2 + 50,400);
        enterButton.setSize(100,20);
        enterButton.addActionListener(e ->{
            //pass all the info into data
            od.tournamentName = tournamentNameField.getText();
            od.date = dateField.getText();
            od.customLogoPath = customLogoField.getText();
            od.customGradientBottomPath = customBottomGradientField.getText();
            od.customGradientMiddlePath = customMiddleGradientField.getText();
            od.customGradientTopBottomPath = customTopGradientField.getText();
            od.tourneyImagePath = customTourneyImageField.getText();
            od.setFontOutlineThickness(outlineThicknessField.getText());
            od.customFontPath = customFontField.getText();
            od.fontColor = fontColorField.getText();
            od.fontOutlineColor = outlineColorField.getText();
            od.setShadowThickness(shadowThicknessField.getText());
            od.useSponsors = sponsorBox.isSelected();
            od.bindNameSizes = bindBox.isSelected();
            od.youtubePlaylistURL = youtubeField.getText();
            gameChoiceEvent(gameList.getSelectedItem().toString(),fighterOneBox,fighterTwoBox,secondaryOneBox,secondaryTwoBox);
            for(Component c : components){
                window.remove(c);
            }
            enterButtonEvent();
        });
        add(enterButton,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton saveButton = new JButton("Save Image");
        saveButton.setLocation(WINDOW_WIDTH/2 - 50,380);
        saveButton.setSize(100,20);
        saveButton.addActionListener(e -> get.saveImg(currentScreen));
        add(saveButton,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton previewAllButton = new JButton("Preview");
        previewAllButton.setLocation(WINDOW_WIDTH/2 - 150,400);
        previewAllButton.setSize(100,20);
        previewAllButton.addActionListener(l ->{
            String characterOne = fighterOneBox.getSelectedItem().toString();
            String characterTwo = fighterTwoBox.getSelectedItem().toString();
            String secondaryOne = secondaryOneBox.getSelectedItem().toString();
            String secondaryTwo = secondaryTwoBox.getSelectedItem().toString();
            String altOne = characterOneAltBox.getSelectedItem().toString();
            String altTwo = characterTwoAltBox.getSelectedItem().toString();
            String secondaryAltOne = secondaryOneAltBox.getSelectedItem().toString();
            String secondaryAltTwo = secondaryTwoAltBox.getSelectedItem().toString();

            od.playerOneName = checkPlayer(playerOneBox.getText());
            od.playerTwoName = checkPlayer(playerTwoBox.getText());
            od.firstCharacter = altOne + "-" + characterOne;
            od.secondCharacter = altTwo + "-" + characterTwo;
            od.thirdCharacter = findSecondary(secondaryOne, secondaryAltOne);
            od.fourthCharacter = findSecondary(secondaryTwo, secondaryAltTwo);
            od.customFighterOnePath = customFighterOneField.getText();
            od.customFighterTwoPath = customFighterTwoField.getText();
            od.round = roundField.getText();

            od.tournamentName = tournamentNameField.getText();
            od.date = dateField.getText();
            od.customLogoPath = customLogoField.getText();
            od.customGradientBottomPath = customBottomGradientField.getText();
            od.customGradientMiddlePath = customMiddleGradientField.getText();
            od.customGradientTopBottomPath = customTopGradientField.getText();
            od.tourneyImagePath = customTourneyImageField.getText();
            od.fontColor = fontColorField.getText();
            od.setFontOutlineThickness(outlineThicknessField.getText());
            od.customFontPath = customFontField.getText();
            od.fontOutlineColor = outlineColorField.getText();
            od.setShadowThickness(shadowThicknessField.getText());
            od.bindNameSizes = bindBox.isSelected();
            od.useSponsors = sponsorBox.isSelected();
            od.youtubePlaylistURL = youtubeField.getText();
            od.overlay = overlay;
            gameChoiceEvent(gameList.getSelectedItem().toString(),fighterOneBox,fighterTwoBox,secondaryOneBox,secondaryTwoBox);
            previewButtonEvent(imageLabel);
        });
        add(previewAllButton,components);

    }

    private void overlayChange(JComboBox<String> overlayBox) {
        for(Overlay o : Overlay.values()){
            if(overlayBox.getSelectedItem().toString().trim().equalsIgnoreCase(o.toString())){
                overlay = o;
            }
        }
    }


    private void characterChoiceEvent(String characterName, JComboBox<String> altBox) {
        String[] alts = {""};
        switch (od.gamePlayed){
            case "64":
                break;
            case "Melee":
                alts = getMeleeAlts(characterName);
                break;
            case "Brawl":
                alts = getBrawlAlts(characterName);
                break;
            case "PM":
                break;
            case "3DS":
            case "WiiU":
                alts = getSm4shAlts(characterName);
                break;
            case"WiiUFull":
                alts = getSm4shFullAlts(characterName);
                break;
            case "RoA":
                alts = getRivalsAlts(characterName);
                break;
            case "S3":
                alts = getShrekAlts(characterName);
        }
        altBox.setModel(new DefaultComboBoxModel<>(alts));
    }

    private String[] getMeleeAlts(String characterName) {
        String[] fourMeleeAlts = new String[]{
                "Bowser","Falco","Fox","Mr. Game & Watch","Ice Climbers","Luigi","MewTwo",
                "Ness","Pichu","Pikachu"
        };

        String[] fiveMeleeAlts = new String[]{
                "Donkey Kong","Doctor Mario","Ganondorf","Link","Mario","Marth","Peach",
                "Jigglypuff","Roy","Samus","Sheik","Yoshi","Young Link","Zelda"
        };

        String[] sixMeleeAlts = new String[]{
                "Captain Falcon","Kirby"
        };

        if(Arrays.asList(fourMeleeAlts).contains(characterName)){
            return new String[] {"0", "1", "2", "3"};
        }else if(Arrays.asList(fiveMeleeAlts).contains(characterName)){
            return new String[] {"0", "1", "2", "3", "4"};
        }else if(Arrays.asList(sixMeleeAlts).contains(characterName)){
            return new String[] {"0", "1", "2", "3", "4", "5"};
        }else return new String[] {""};

    }
    private String[] getBrawlAlts(String characterName) {
        String[] fourBrawlAlts = new String[]{
                "Pikachu"
        };

        String[] fiveBrawlAlts = new String[]{
                "Jigglypuff", "Lucario","Pokemon Trainer", "Sonic"
        };

        String[] twelveBrawlAlts = new String[]{
                "Wario",
        };

        if(Arrays.asList(fourBrawlAlts).contains(characterName)){
            return new String[] {"0", "1", "2", "3"};
        }else if(Arrays.asList(fiveBrawlAlts).contains(characterName)){
            return new String[] {"0", "1", "2", "3", "4"};
        }else if(Arrays.asList(twelveBrawlAlts).contains(characterName)){
            return new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        }else return new String[] {"0","1","2","3","4","5"};

    }
    private String[] getSm4shAlts(String characterName) {
        if (characterName.equals("Little Mac")) {
            return new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        } else {
            return new String[]{"0", "1", "2", "3", "4", "5", "6", "7"};
        }
    }
    private String[] getSm4shFullAlts(String characterName) {
        System.out.println(characterName);
        return new String[]{"0"};
    }
    private String[] getRivalsAlts(String characterName) {
        System.out.println(characterName);
        return new String[]{"0", "1", "2", "3", "4", "5"};
    }
    private String[] getShrekAlts(String characterName) {
        System.out.println(characterName);
        return new String[]{"0"};
    }

    private void previewButtonEvent(JLabel imageLabel){
        BufferedImage defaultThumbnail = od.createFrame();
        currentScreen = defaultThumbnail;
        defaultThumbnail = get.getSizedImg(defaultThumbnail,300,150);
        imageLabel.setIcon(new ImageIcon(defaultThumbnail));
    }

    private void enterButtonEvent(){
        YouTubeStuff yt = new YouTubeStuff();
        yt.startYoutube(od,window);

    }

    private void gameChoiceEvent
            (String game, JComboBox<String> fighter1, JComboBox<String> fighter2, JComboBox<String> secondary1,
             JComboBox<String> secondary2){
        gameChoiceEvent(game,fighter1,fighter2,secondary1,secondary2,false);
    }

    private void gameChoiceEvent
            (String gameString, JComboBox<String> fighter1, JComboBox<String> fighter2, JComboBox<String> secondary1,
             JComboBox<String> secondary2,Boolean refreshFighters){
        for(Games g : Games.values()){
            if(g.toString().trim().equalsIgnoreCase(gameString)){
                game = g;
            }
        }
        od.gamePlayed = game.getCode();
        if(refreshFighters) {
            setCharacters(fighter1, fighter2, secondary1, secondary2, game);
        }
    }

    private void setCharacters
            (JComboBox<String> fighter1, JComboBox<String> fighter2, JComboBox<String> secondary1,
             JComboBox<String> secondary2, Games game) {
        fighter1.setModel(new DefaultComboBoxModel<>(game.getArray(false)));
        fighter2.setModel(new DefaultComboBoxModel<>(game.getArray(false)));
        secondary1.setModel(new DefaultComboBoxModel<>(game.getArray(true)));
        secondary2.setModel(new DefaultComboBoxModel<>(game.getArray(true)));
    }

    private void previewColorsEvent(JTextField fontColorField, JTextField outlineColorField, JButton previewButton){
        if(fontColorField.getText().trim().equals("")){
            fontColorField.setText("#ffffff");
        }

        if(outlineColorField.getText().trim().equals("")){
            outlineColorField.setText("#000000");
        }

        previewButton.setForeground(Color.decode(fontColorField.getText()));
        previewButton.setBackground(Color.decode(outlineColorField.getText()));

        previewButton.setOpaque(true);
    }
    private void add(Component component,ArrayList<Component> components){
        window.add(component);
        components.add(component);
    }
    private String checkPlayer(String player){
        String tempPlayer = player.toLowerCase().trim();
        if(tempPlayer.equals("")){
            return "player";
        }else{
            return player;
        }
    }
    private String findSecondary(String secondary, String alt) {
        if(secondary.equals("0Nothing")){
            return secondary;
        }else{
            return alt + "-" + secondary;
        }
    }
}
