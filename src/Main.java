import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        GUIHuffman guiHuf = new GUIHuffman();
        guiHuf.setContentPane(guiHuf.getPanel());
        int width = 800;
        int height = 400;
        guiHuf.setSize(width , height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width)/2;
        int y = (screenSize.height - height)/2;
        guiHuf.setLocation(x , y);
        guiHuf.setVisible(true);
        guiHuf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}