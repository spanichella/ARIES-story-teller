package ui;


public class SWMGui {
    public static final SWMFrame frame = new SWMFrame();

    @SuppressWarnings("EmptyMethod")
    public static void main(String[] args) {
    }

    public static void killFrames() {
        frame.closeWindow();
    }
}
