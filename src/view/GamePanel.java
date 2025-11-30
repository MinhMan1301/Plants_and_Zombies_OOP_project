package view;

import javax.swing.*;
import core.Collider;
import core.LevelData;
import plants.CherryBomb; // IMPORT MỚI
import plants.FreezePeashooter;
import plants.Peashooter;
import plants.Plant;
import plants.Sunflower;
import plants.WallNut; // IMPORT MỚI
import utils.FreezePea;
import utils.Pea;
import zombies.ConeHeadZombie;
import zombies.NormalZombie;
import zombies.Zombie;
import utils.Sun;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JLayeredPane implements MouseMotionListener {

    private Image bgImage;
    private Image peashooterImage;
    private Image freezePeashooterImage;
    private Image sunflowerImage;
    private Image peaImage;
    private Image freezePeaImage;
    
    private Image wallNutImage;      // THÊM WALLNUT IMAGE
    private Image cherryBombImage;   // THÊM CHERRY BOMB IMAGE

    private Image normalZombieImage;
    private Image coneHeadZombieImage;
    private Collider[] colliders;

    private ArrayList<ArrayList<Zombie>> laneZombies;
    private ArrayList<ArrayList<Pea>> lanePeas;
    private ArrayList<Sun> activeSuns;

    private Timer redrawTimer;
    private Timer advancerTimer;
    private Timer sunProducer;
    private Timer zombieProducer;
    private JLabel sunScoreboard;

    private Main.PlantType activePlantingBrush = Main.PlantType.None;

    @SuppressWarnings("unused")
    private int mouseX, mouseY;

    private int sunScore;

    public int getSunScore() {
        return sunScore;
    }

    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreboard.setText(String.valueOf(sunScore));
    }

    @SuppressWarnings({ "removal", "static-access" })
    public GamePanel(JLabel sunScoreboard) {
        setSize(1000, 752);
        setLayout(null);
        addMouseMotionListener(this);
        this.sunScoreboard = sunScoreboard;
        setSunScore(150);  //pool avalie

        bgImage = new ImageIcon(this.getClass().getResource("/view/images/items/mainBG.png")).getImage();

        peashooterImage = new ImageIcon(this.getClass().getResource("/view/images/plants/peashooter.gif")).getImage();
        freezePeashooterImage = new ImageIcon(this.getClass().getResource("/view/images/plants/freezepeashooter.gif")).getImage();
        sunflowerImage = new ImageIcon(this.getClass().getResource("/view/images/plants/sunflower.gif")).getImage();
        peaImage = new ImageIcon(this.getClass().getResource("/view/images/items/pea.png")).getImage();
        freezePeaImage = new ImageIcon(this.getClass().getResource("images/items/freezepea.png")).getImage();
        
        // TẢI HÌNH ẢNH MỚI
        wallNutImage = new ImageIcon(this.getClass().getResource("/view/images/plants/wallnut.gif")).getImage(); 
        cherryBombImage = new ImageIcon(this.getClass().getResource("/view/images/plants/cherrybomb.png")).getImage(); 

        normalZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/zombie1.png")).getImage();
        coneHeadZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/zombie2.png")).getImage();

        laneZombies = new ArrayList<>();
        laneZombies.add(new ArrayList<>()); //line 1
        laneZombies.add(new ArrayList<>()); //line 2
        laneZombies.add(new ArrayList<>()); //line 3
        laneZombies.add(new ArrayList<>()); //line 4
        laneZombies.add(new ArrayList<>()); //line 5

        lanePeas = new ArrayList<>();
        lanePeas.add(new ArrayList<>()); //line 1
        lanePeas.add(new ArrayList<>()); //line 2
        lanePeas.add(new ArrayList<>()); //line 3
        lanePeas.add(new ArrayList<>()); //line 4
        lanePeas.add(new ArrayList<>()); //line 5

        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i % 9) * 100, 109 + (i / 9) * 120);
            a.setAction(new PlantActionListener((i % 9), (i / 9)));
            colliders[i] = a;
            add(a, new Integer(0));
        }


        activeSuns = new ArrayList<>();

        redrawTimer = new Timer(25, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(60, (ActionEvent e) -> advance());
        advancerTimer.start();

        sunProducer = new Timer(5000, (ActionEvent e) -> {
            Random rnd = new Random();
            Sun sta = new Sun(this, rnd.nextInt(800) + 100, 0, rnd.nextInt(300) + 200);
            activeSuns.add(sta);
            add(sta, new Integer(1));
        });
        sunProducer.start();

        zombieProducer = new Timer(7000, (ActionEvent e) -> {
            Random rnd = new Random();
            LevelData lvl = new LevelData();
            String[] Level = lvl.LEVEL_CONTENT[Integer.parseInt(lvl.LEVEL_NUMBER) - 1];
            int[][] LevelValue = lvl.LEVEL_VALUE[Integer.parseInt(lvl.LEVEL_NUMBER) - 1];
            int l = rnd.nextInt(5);
            int t = rnd.nextInt(100);
            Zombie z = null;
            for (int i = 0; i < LevelValue.length; i++) {
                if (t >= LevelValue[i][0] && t <= LevelValue[i][1]) {
                    z = Zombie.getZombie(Level[i], GamePanel.this, l);
                }
            }
            laneZombies.get(l).add(z);
        });
        zombieProducer.start();

    }

    private void advance() {
        for (int i = 0; i < 5; i++) {
            for (Zombie z : laneZombies.get(i)) {
                z.advance();
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.advance();
            }

        }

        for (int i = 0; i < activeSuns.size(); i++) {
            activeSuns.get(i).advance();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);
        
        // Kích thước cố định cho tất cả các cây (Giữ lại kích thước WallNut/PotatoMine trước đó là 80x80)
        int plantWidth = 80;
        int plantHeight = 80;

        //Draw Plants
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if (c.assignedPlant != null) {
                Plant p = c.assignedPlant;
                
                // Vị trí để căn giữa ảnh 80x80 trong ô 100x120
                int cellX = 44 + (i % 9) * 100;
                int cellY = 109 + (i / 9) * 120;
                int drawX = cellX + (100 - plantWidth) / 2; 
                int drawY = cellY + (120 - plantHeight) / 2;
                
                // LOGIC VẼ CÂY MỚI (Sử dụng drawImage có tham số kích thước)
                if (p instanceof WallNut) {
                     g.drawImage(wallNutImage, drawX, drawY, plantWidth, plantHeight, null);
                } else if (p instanceof CherryBomb) {
                     g.drawImage(cherryBombImage, drawX, drawY, plantWidth, plantHeight, null);
                } else if (p instanceof Peashooter) {
                    g.drawImage(peashooterImage, drawX, drawY, plantWidth, plantHeight, null);
                } else if (p instanceof FreezePeashooter) {
                    g.drawImage(freezePeashooterImage, drawX, drawY, plantWidth, plantHeight, null);
                } else if (p instanceof Sunflower) {
                    g.drawImage(sunflowerImage, drawX, drawY, plantWidth, plantHeight, null);
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (Zombie z : laneZombies.get(i)) {
                if (z instanceof NormalZombie) {
                    g.drawImage(normalZombieImage, z.getPosX(), 109 + (i * 120), null);
                } else if (z instanceof ConeHeadZombie) {
                    g.drawImage(coneHeadZombieImage, z.getPosX(), 109 + (i * 120), null);
                }
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea pea = lanePeas.get(i).get(j);
                if (pea instanceof FreezePea) {
                    g.drawImage(freezePeaImage, pea.getPosX(), 130 + (i * 120), null);
                } else {
                    g.drawImage(peaImage, pea.getPosX(), 130 + (i * 120), null);
                }
            }

        }
    }

    private class PlantActionListener implements ActionListener {

        int x, y;

        public PlantActionListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            // LOGIC MUA SUNFLOWER
            if (activePlantingBrush == Main.PlantType.Sunflower) {
                if (getSunScore() >= 50) {
                    colliders[x + y * 9].setPlant(new Sunflower(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 50);
                }
            }
            // LOGIC MUA PEASHOOTER
            if (activePlantingBrush == Main.PlantType.Peashooter) {
                if (getSunScore() >= 100) {
                    colliders[x + y * 9].setPlant(new Peashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 100);
                }
            }
            // LOGIC MUA FREEZE PEASHOOTER
            if (activePlantingBrush == Main.PlantType.FreezePeashooter) {
                if (getSunScore() >= 175) {
                    colliders[x + y * 9].setPlant(new FreezePeashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 175);
                }
            }
            
            // LOGIC MUA WALLNUT (Giá 50)
            if (activePlantingBrush == Main.PlantType.WallNut) {
                if (getSunScore() >= 50) {
                    colliders[x + y * 9].setPlant(new WallNut(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 50);
                }
            }
            
            // LOGIC MUA CHERRY BOMB (Giá 150)
            if (activePlantingBrush == Main.PlantType.CherryBomb) {
                if (getSunScore() >= 150) {
                    colliders[x + y * 9].setPlant(new CherryBomb(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 150);
                }
            }
            
            activePlantingBrush = Main.PlantType.None;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    static int progress = 0;

    public static void setProgress(int num) {
        progress = progress + num;
        System.out.println(progress);
        if (progress >= 150) {
            if ("1".equals(LevelData.LEVEL_NUMBER)) {
                JOptionPane.showMessageDialog(null, "LEVEL_CONTENT Completed !!!" + '\n' + "Starting next LEVEL_CONTENT");
                Main.gw.dispose();
                LevelData.write("2");
                Main.gw = new Main();
            } else {
                JOptionPane.showMessageDialog(null, "LEVEL_CONTENT Completed !!!" + '\n' + "More Levels will come soon !!!" + '\n' + "Resetting data");
                LevelData.write("1");
                System.exit(0);
            }
            progress = 0;
        }
    }

    public Main.PlantType getActivePlantingBrush() {
        return activePlantingBrush;
    }

    public void setActivePlantingBrush(Main.PlantType activePlantingBrush) {
        this.activePlantingBrush = activePlantingBrush;
    }

    public ArrayList<ArrayList<Zombie>> getLaneZombies() {
        return laneZombies;
    }

    public void setLaneZombies(ArrayList<ArrayList<Zombie>> laneZombies) {
        this.laneZombies = laneZombies;
    }

    public ArrayList<ArrayList<Pea>> getLanePeas() {
        return lanePeas;
    }

    public void setLanePeas(ArrayList<ArrayList<Pea>> lanePeas) {
        this.lanePeas = lanePeas;
    }

    public ArrayList<Sun> getActiveSuns() {
        return activeSuns;
    }

    public void setActiveSuns(ArrayList<Sun> activeSuns) {
        this.activeSuns = activeSuns;
    }

    public Collider[] getColliders() {
        return colliders;
    }

    public void setColliders(Collider[] colliders) {
        this.colliders = colliders;
    }
}