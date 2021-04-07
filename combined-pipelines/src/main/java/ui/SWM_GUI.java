package ui;


public class SWM_GUI {
    public static final SWM_Frame frame = new SWM_Frame();

    @SuppressWarnings("EmptyMethod")
    public static void main(String[] args) {
    }

    public static void killFrames() {
        frame.closeWindow();
    }
}
