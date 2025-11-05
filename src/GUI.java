import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.awt.Font.BOLD;

public class GUI extends JFrame implements ActionListener {

    JPanel panel = new JPanel();
    JButton[] buttons = new JButton[16];
    ArrayList<String> tileList = new ArrayList<>
            (Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                    "11", "12", "13", "14", "15", ""));
    ArrayList<String> correctOrder = new ArrayList<>(tileList);
    Color emptyColor = Color.LIGHT_GRAY;
    Game game;
    JPanel panel2 = new JPanel();
    JButton restartBtn = new JButton("Restart");
    JButton shuffleBtn = new JButton("Shuffle");
    JButton settingsBtn = new JButton("Settings");
    JButton demoBtn = new JButton("Demo");
    Color BGcolor;
    Color textColor;
    JPanel panel3 = new JPanel();
    int moveCounter = 0;
    JLabel moveLabel = new JLabel("Moves: " + moveCounter);
    Font font = new Font("Arial", BOLD, 18);
    BufferedImage noIcon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    String tempTileColor = "Default";
    String tempTextColor = "Black";

    public GUI() {
        Collections.shuffle(tileList);

        this.setIconImage(noIcon);
        this.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(4, 4));

        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton(tileList.get(i));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            buttons[i].setFont(font);
            panel.add(buttons[i]);
            if (buttons[i].getText().isEmpty()) {
                buttons[i].setEnabled(false);
                buttons[i].setBackground(emptyColor);
            }
        }

        panel.setBorder(new EmptyBorder(0, 50, 0, 50));
        this.add(panel2, BorderLayout.SOUTH);
        panel2.add(demoBtn);
        panel2.add(restartBtn);
        panel2.add(shuffleBtn);
        panel2.add(settingsBtn);
        panel2.setBorder(new EmptyBorder(10, 10, 10, 10));
        restartBtn.setFocusPainted(false);
        shuffleBtn.setFocusPainted(false);
        settingsBtn.setFocusPainted(false);
        demoBtn.setFocusPainted(false);
        ActionListener extraBtns = e -> {
            if (e.getSource() == demoBtn) {
                moveCounter = 0;
                moveLabel.setText("Moves: " + moveCounter);
                game.demoShuffle();
            }
            if (e.getSource() == restartBtn) {
                this.dispose();
                new GUI();
            }
            if (e.getSource() == shuffleBtn) {
                game.shuffle();
                moveCounter = 0;
                moveLabel.setText("Moves: " + moveCounter);
            }
            if (e.getSource() == settingsBtn) {
                JFrame settingsFrame = new JFrame();
                JPanel settingsPanel = new JPanel();
                settingsPanel.setLayout(new GridLayout(3, 2));
                settingsFrame.add(settingsPanel);
                settingsFrame.setIconImage(noIcon);

                JLabel colorLabelTile = new JLabel("Tile color:");
                colorLabelTile.setHorizontalAlignment(SwingConstants.CENTER);
                settingsPanel.add(colorLabelTile);
                String[] tileColorArray = {"Default", "Black", "White", "Red", "Blue"};
                JComboBox<String> tileColors = new JComboBox<>(tileColorArray);
                settingsPanel.add(tileColors);
                tileColors.setSelectedItem(tempTileColor);

                JLabel colorLabelText = new JLabel("Text color:");
                colorLabelText.setHorizontalAlignment(SwingConstants.CENTER);
                settingsPanel.add(colorLabelText);
                String[] textColorArray = {"Black", "White"};
                JComboBox<String> textColors = new JComboBox<>(textColorArray);
                settingsPanel.add(textColors);
                textColors.setSelectedItem(tempTextColor);

                JButton cancelBtn = new JButton("Cancel");
                settingsPanel.add(cancelBtn);
                cancelBtn.setFocusPainted(false);
                cancelBtn.addActionListener(eCancel -> {
                    settingsFrame.dispose();
                });

                JButton saveBtn = new JButton("Save");
                settingsPanel.add(saveBtn);
                saveBtn.setFocusPainted(false);
                saveBtn.addActionListener(eSave -> {
                    String color = (String) tileColors.getSelectedItem();
                    tempTileColor = color;
                    BGcolor = switch (color) {
                        case "Black" -> Color.BLACK;
                        case "White" -> Color.WHITE;
                        case "Red" -> Color.RED;
                        case "Blue" -> Color.BLUE;
                        default -> null;
                    };
                    String color2 = (String) textColors.getSelectedItem();
                    tempTextColor = color2;
                    textColor = switch (color2) {
                        case "Black" -> Color.BLACK;
                        case "White" -> Color.WHITE;
                        default -> null;
                    };
                    if (color != null) {
                        for (JButton btns : buttons) {
                            if (btns.isEnabled()) {
                                btns.setBackground(BGcolor);
                            } else {
                                btns.setBackground(emptyColor);
                            }
                        }
                    }
                    if (color2 != null) {
                        for (JButton btns : buttons) {
                            btns.setForeground(textColor);
                        }
                    }
                });

                settingsFrame.pack();
                settingsFrame.setVisible(true);
                settingsFrame.setLocationRelativeTo(this);
                settingsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        };

        restartBtn.addActionListener(extraBtns);
        shuffleBtn.addActionListener(extraBtns);
        settingsBtn.addActionListener(extraBtns);
        demoBtn.addActionListener(extraBtns);

        game = new Game(buttons, correctOrder, this);

        this.add(panel3, BorderLayout.NORTH);
        panel3.add(moveLabel);
        moveLabel.setFont(font);
        moveLabel.setBorder(new EmptyBorder( 10, 20, 10, 0));

        setSize(600, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Color getEmptyColor() {
        return emptyColor;
    }

    public Color getBGcolor() {
        return BGcolor;
    }

    public Color getTextColor() {
        return textColor;
    }

    private void winWindow() {
        JFrame winFrame = new JFrame();
        JPanel winPanel = new JPanel();
        JPanel winPanelTop = new JPanel();
        JLabel winLabel = new JLabel("You won! (" + moveCounter + " moves)");
        JPanel winPanelMid = new JPanel();
        JLabel playAgainLabel = new JLabel("Play again?");
        JPanel winPanelDown = new JPanel();
        JButton yesBtn = new JButton("Yes");
        JButton noBtn = new JButton("No");

        winFrame.add(winPanel);
        winPanel.setLayout(new BorderLayout());
        winPanel.setBorder(new LineBorder(Color.DARK_GRAY));
        winPanel.add(winPanelTop, BorderLayout.NORTH);
        winPanel.add(winPanelMid, BorderLayout.CENTER);
        winPanel.add(winPanelDown, BorderLayout.SOUTH);
        winPanelTop.add(winLabel);
        winPanelMid.add(playAgainLabel);
        winPanelDown.add(yesBtn);
        winPanelDown.add(noBtn);

        noBtn.addActionListener(e -> {
            winFrame.dispose();
        });
        yesBtn.addActionListener(e -> {
            winFrame.dispose();
            moveCounter = 0;
            moveLabel.setText("Moves: " + moveCounter);
            game.shuffle();
        });

        winFrame.setUndecorated(true);
        yesBtn.setFocusPainted(false);
        noBtn.setFocusPainted(false);

        winFrame.pack();
        winFrame.setVisible(true);
        winFrame.setLocationRelativeTo(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton pressedBtn = (JButton) e.getSource();
        int pressed = game.getBtnPos(pressedBtn);
        int empty = game.getEmptyPos();

        if (game.isNextTo(pressed, empty)) {
            game.btnSwap(pressed, empty);
            moveCounter++;
            moveLabel.setText("Moves: " + moveCounter);

            if (game.isSolved()) {
                winWindow();
            }
        }
    }
}