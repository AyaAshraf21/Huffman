import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIHuffman extends JFrame{
    private JButton decompressionButton;
    private JButton compressionButton;
    private JPanel panel;


    public GUIHuffman() {
        compressionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddFile add = new AddFile();
                add.setContentPane(add.getPanel());
                int width = 700;
                int height = 350;
                add.getCompressButton().setText("Compress");
                add.setSize(width , height);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - width)/2;
                int y = (screenSize.height - height)/2;
                add.setLocation(x , y);
                add.setVisible(true);

            }
        });

        decompressionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddFile add = new AddFile();
                add.setContentPane(add.getPanel());
                int width = 700;
                int height = 350;
                add.setSize(width , height);
                add.getCompressButton().setText("Decompress");
                add.setSize(width , height);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - width)/2;
                int y = (screenSize.height - height)/2;
                add.setLocation(x , y);
                add.setVisible(true);
            }
        });
    }



    public JPanel getPanel() {
        return panel;
    }
}
