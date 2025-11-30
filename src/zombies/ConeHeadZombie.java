package zombies;
import view.GamePanel;

public class ConeHeadZombie extends Zombie {

    public ConeHeadZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setHealth(1800);
    }
}
