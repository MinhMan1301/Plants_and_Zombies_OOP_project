package utils;

import java.awt.*;

import view.GamePanel;
import zombies.Zombie;


public class Pea {

    private int posX;
    protected GamePanel gp;
    private int myLane;
    // THÊM: Sát thương của viên đạn
    protected int damage = 300; 

    public Pea(GamePanel parent, int lane, int startX) {
        this.gp = parent;
        this.myLane = lane;
        posX = startX;
    }

    public void advance() {
        // Tăng tốc độ đạn
        posX += 15; 
        
        // Vùng va chạm của đạn (tọa độ cố định trong làn, chỉ X thay đổi)
        Rectangle pRect = new Rectangle(posX, 130 + myLane * 120, 28, 28);
        
        // Kiểm tra va chạm với Zombies trong làn
        for (int i = 0; i < gp.getLaneZombies().get(myLane).size(); i++) {
            Zombie z = gp.getLaneZombies().get(myLane).get(i);
            
            // Vùng va chạm của Zombie (400x120 là quá rộng, nhưng giữ lại theo logic cũ của bạn)
            // Tối ưu hơn nên dùng kích thước thực của Zombie (ví dụ: 100x120)
            Rectangle zRect = new Rectangle(z.getPosX(), 109 + myLane * 120, 100, 120); 
            
            if (pRect.intersects(zRect)) {
                
                // 1. Gây sát thương
                z.setHealth(z.getHealth() - damage);
                
                // 2. Xóa đạn khỏi danh sách Peas (Quan trọng!)
                // Sử dụng 'this' để tham chiếu đến Pea hiện tại
                gp.getLanePeas().get(myLane).remove(this);
                
                // 3. Kiểm tra Zombie chết
                if (z.getHealth() <= 0) {
                    System.out.println("ZOMBIE DIED");

                    // Xóa Zombie khỏi danh sách
                    gp.getLaneZombies().get(myLane).remove(i);
                    GamePanel.setProgress(10);
                    // Sau khi xóa Zombie, cần thoát khỏi vòng lặp và phương thức
                    // vì danh sách đã bị thay đổi (giảm kích thước)
                }

                return; // Đạn đã va chạm và bị xóa, thoát khỏi advance()
            }
        }

        // Kiểm tra nếu đạn bay ra ngoài màn hình (xóa để tiết kiệm bộ nhớ)
        if (posX > 1000) {
             gp.getLanePeas().get(myLane).remove(this);
        }
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getMyLane() {
        return myLane;
    }

    public void setMyLane(int myLane) {
        this.myLane = myLane;
    }
}