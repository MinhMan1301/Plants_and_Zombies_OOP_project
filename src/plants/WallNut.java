package plants;

import view.GamePanel;

public class WallNut extends Plant {

    public WallNut(GamePanel parent, int x, int y) {
        super(parent, x, y);
        // Đặt máu cao (ví dụ: 4000)
        setHealth(4000); 
    }

    // WallNut không cần Timer
    @Override
    public void stop() {
        // Do nothing
    }

}