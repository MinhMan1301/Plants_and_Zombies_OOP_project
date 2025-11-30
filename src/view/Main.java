package view;
import javax.swing.*;

import java.awt.event.ActionEvent;

public class Main extends JFrame {

    enum PlantType {
        None,
        Sunflower,
        Peashooter,
        FreezePeashooter,
        WallNut,
        CherryBomb
    }


    @SuppressWarnings("removal")
    public Main() {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel("SUN");
        sun.setLocation(37, 80);
        sun.setSize(60, 20);

        GamePanel gp = new GamePanel(sun);
        gp.setLocation(0, 0);
        getLayeredPane().add(gp, new Integer(0));

        // --- CARD: SUNFLOWER (50) ---
        PlantCard sunflower = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_sunflower.png")).getImage());
        sunflower.setLocation(110, 8);
        sunflower.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Sunflower);
        });
        getLayeredPane().add(sunflower, new Integer(3));

        // --- CARD: PEASHOOTER (100) ---
        PlantCard peashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_peashooter.png")).getImage());
        peashooter.setLocation(175, 8);
        peashooter.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Peashooter);
        });
        getLayeredPane().add(peashooter, new Integer(3));

        // --- CARD: FREEZE PEASHOOTER (175) ---
        PlantCard freezepeashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_freezepeashooter.png")).getImage());
        freezepeashooter.setLocation(240, 8);
        freezepeashooter.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.FreezePeashooter);
        });
        getLayeredPane().add(freezepeashooter, new Integer(3));
        
        // --- CARD: WALLNUT (50) ---
        PlantCard wallNut = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_wallnut.png")).getImage());
        wallNut.setLocation(305, 8); // Vị trí mới
        wallNut.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.WallNut);
        });
        getLayeredPane().add(wallNut, new Integer(3));
        
        // --- CARD: CHERRY BOMB (150) ---
        PlantCard cherryBomb = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_cherrybomb.png")).getImage());
        cherryBomb.setLocation(370, 8); // Vị trí mới
        cherryBomb.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.CherryBomb);
        });
        getLayeredPane().add(cherryBomb, new Integer(3));


        getLayeredPane().add(sun, new Integer(2));
        setResizable(false);
        setVisible(true);
    }

    @SuppressWarnings("removal")
    public Main(boolean b) {
        Menu menu = new Menu();
        menu.setLocation(0, 0);
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu, new Integer(0));
        menu.repaint();
        setResizable(false);
        setVisible(true);
    }

    public static Main gw;

    public static void begin() {
        gw.dispose();
        gw = new Main();
    }

    public static void main(String[] args) {
        gw = new Main();
    }

}