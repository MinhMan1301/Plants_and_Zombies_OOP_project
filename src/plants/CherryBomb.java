package plants;

import java.awt.event.ActionEvent;
import javax.swing.Timer;
import view.GamePanel;
import zombies.Zombie;
import java.awt.Rectangle;
import java.util.ArrayList;

public class CherryBomb extends Plant {

    private Timer explodeTimer;
    private final int EXPLOSION_DAMAGE = 1800;

    public CherryBomb(GamePanel parent, int x, int y) {
        super(parent, x, y);
        // Cherry Bomb nổ sau 1 giây
        explodeTimer = new Timer(1000, (ActionEvent e) -> {
            
            // Xử lý nổ
            explode();
            
            // Tự hủy ngay sau khi nổ
            this.stop();
            getGp().getColliders()[this.getX() + this.getY() * 9].removePlant();
        });
        explodeTimer.setRepeats(false); // Chỉ nổ một lần
        explodeTimer.start();
    }

    private void explode() {
        int centerX = 44 + this.getX() * 100;
        int centerY = 109 + this.getY() * 120;
 
        Rectangle explosionArea = new Rectangle(centerX - 100, centerY - 120, 300, 360);


        for (int lane = Math.max(0, this.getY() - 1); lane <= Math.min(4, this.getY() + 1); lane++) {
            
            ArrayList<Zombie> zombiesInLane = getGp().getLaneZombies().get(lane);
            
            for (int i = zombiesInLane.size() - 1; i >= 0; i--) {
                Zombie z = zombiesInLane.get(i);
                
                // Vùng va chạm của Zombie
                Rectangle zRect = new Rectangle(z.getPosX(), 109 + lane * 120, 100, 120); 

                if (explosionArea.intersects(zRect)) {
                    // Gây sát thương
                    z.setHealth(z.getHealth() - EXPLOSION_DAMAGE);
                    
                    // Kiểm tra Zombie chết
                    if (z.getHealth() <= 0) {
                        zombiesInLane.remove(i);
                        GamePanel.setProgress(10);
                    }
                }
            }
        }
    }

    @Override
    public void stop() {
        if (explodeTimer != null) {
            explodeTimer.stop();
        }
    }
}