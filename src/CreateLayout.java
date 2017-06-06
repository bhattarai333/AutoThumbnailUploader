import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

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
    private Data d = new Data();
    private SmashFrame sf = new SmashFrame();
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
        String[] gameStrings = { "Smash 64", "Smash Melee"/*,"MSU Melee"*/, "Smash Brawl",
                "Smash PM", "Smash 3DS", "Smash WiiU", "Smash WiiU Full", "Rivals of Aether","Shrek Super Slam"};
        JComboBox<String> gameList = new JComboBox<>(gameStrings);
        gameList.setSelectedIndex(5);
        gameList.addActionListener(e -> gameChoiceEvent(gameList.getSelectedItem().toString()));
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
        add(outlineColorLabel,components);

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

        JLabel playerOneLabel = new JLabel("Player1:");
        playerOneLabel.setLocation(0,240);
        playerOneLabel.setSize(50,20);
        add(playerOneLabel,components);

        JTextField playerOneBox = new JTextField();
        playerOneBox.setLocation(50,242);
        playerOneBox.setSize(100,20);
        add(playerOneBox,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton enterButton = new JButton("Enter");
        enterButton.setLocation(WINDOW_WIDTH/2 - 150,400);
        enterButton.setSize(100,20);
        enterButton.addActionListener(e ->{
            //pass all the info into data
            d.tournamentName = tournamentNameField.getText();
            d.date = dateField.getText();
            d.customLogo = customLogoField.getText();
            d.customGradientBottom = customBottomGradientField.getText();
            d.customGradientMiddle = customMiddleGradientField.getText();
            d.customGradientTop = customTopGradientField.getText();
            d.tournamentImage = customTourneyImageField.getText();
            d.fontColor = fontColorField.getText();
            d.setFontThickness(outlineThicknessField.getText());
            d.customFont = customFontField.getText();
            d.outlineColor = outlineColorField.getText();
            d.setShadowThickness(shadowThicknessField.getText());
            d.useSponsors = sponsorBox.isSelected();
            d.youtubePlaylistURL = youtubeField.getText();
            gameChoiceEvent(gameList.getSelectedItem().toString());
            for(Component c : components){
                window.remove(c);
            }
            enterButtonEvent();
        });
        add(enterButton,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel imageLabel = new JLabel();
        imageLabel.setSize(300,150);
        imageLabel.setLocation(210,230);
        add(imageLabel,components);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton previewAllButton = new JButton("Preview");
        previewAllButton.setLocation(WINDOW_WIDTH/2 + 50,400);
        previewAllButton.setSize(100,20);
        previewAllButton.addActionListener(l ->{
            d.tournamentName = tournamentNameField.getText();
            d.date = dateField.getText();
            d.customLogo = customLogoField.getText();
            d.customGradientBottom = customBottomGradientField.getText();
            d.customGradientMiddle = customMiddleGradientField.getText();
            d.customGradientTop = customTopGradientField.getText();
            d.tournamentImage = customTourneyImageField.getText();
            d.fontColor = fontColorField.getText();
            d.setFontThickness(outlineThicknessField.getText());
            d.customFont = customFontField.getText();
            d.outlineColor = outlineColorField.getText();
            d.setShadowThickness(shadowThicknessField.getText());
            d.useSponsors = sponsorBox.isSelected();
            d.youtubePlaylistURL = youtubeField.getText();
            gameChoiceEvent(gameList.getSelectedItem().toString());
            previewButtonEvent(imageLabel);
        });
        add(previewAllButton,components);
    }

    private void previewButtonEvent(JLabel imageLabel){
        BufferedImage defaultThumbnail = sf.createFrame(
             d.tempChar1,d.tempChar2,d.tempSecondary1,d.tempSecondary2,d.tempRound,d.tournamentName,
                d.date,d.tempPlayer1, d.tempPlayer2, d.game,d.customLogo,d.customGradientTop,d.customGradientMiddle,
                d.customGradientBottom,d.fontColor,d.customFont,d.tournamentImage,d.outlineColor,d.getFontThickness(),
                d.useSponsors,d.tempCustomFighter1,d.tempCustomFighter2,d.getShadowThickness()
        );
        defaultThumbnail = get.getSizedImg(defaultThumbnail,300,150);
        imageLabel.setIcon(new ImageIcon(defaultThumbnail));
    }

    private void enterButtonEvent(){
        YouTubeStuff yt = new YouTubeStuff();
        yt.startYoutube(d,window);

    }

    private void gameChoiceEvent(String game){
        String version = "";
        switch (game) {
            case "Smash WiiU":
                version = "WiiU";
                break;
            case "Smash 3DS":
                version = "3DS";
                break;
            case "Smash WiiU Full":
                version = "WiiUFull";
                break;
            case "Smash Melee":
                version = "Melee";
                break;
            case "MSU Melee":
                version = "Melee";
                break;
            case "Rivals of Aether":
                version = "RoA";
                break;
            case "Smash Brawl":
                version = "Brawl";
                break;
            case "Smash PM":
                version = "PM";
                break;
            case "Smash 64":
                version = "64";
                break;
            case "Shrek Super Slam":
                version = "S3";
                break;
        }
        d.game = version;
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
}
