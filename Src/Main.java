import Controller.GardenController;
import Model.GardenModel;
import View.GardenView;

public class Main {
    public static void main(String[] args) {
        GardenModel model = new GardenModel(5, 9);
        GardenView view = new GardenView(model);
        GardenController controller = new GardenController(model, view);

        controller.start();

        // Thử hiển thị vài ô
        controller.placePlant(2, 1);
        controller.placePlant(3, 4);
        controller.addZombie(1, 6);
        controller.addZombie(2, 8);
    }
}
