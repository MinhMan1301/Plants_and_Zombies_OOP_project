package plants;

import javax.swing.*;

import utils.Sun;
import view.GamePanel;

import java.awt.event.ActionEvent;


public class Sunflower extends Plant {

    private Timer sunProduceTimer;

    @SuppressWarnings("removal")
    public Sunflower(GamePanel parent, int x, int y) {
        super(parent, x, y);
        sunProduceTimer = new Timer(15000, (ActionEvent e) -> {
            Sun sta = new Sun(getGp(), 60 + x * 100, 110 + y * 120, 130 + y * 120);
            getGp().getActiveSuns().add(sta);
            getGp().add(sta, new Integer(1));
        });
        sunProduceTimer.start();
    }

}
