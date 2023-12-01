import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFile extends JFrame {
    private JPanel panel1;
    private JButton selectFileButton;
    private JButton compressButton;
    private JLabel fileInput;

    private String filePath;

    public AddFile() {
        Dimension labelSize = new Dimension(450, 30);
        fileInput.setPreferredSize(labelSize);

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(AddFile.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getName();
                    fileInput.setText(filePath);
                }
            }
        });

        setLocationRelativeTo(null);

        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(compressButton.getText().equals("Compress"))
                {
                    Huffman.compress(filePath);
                }
                else if(compressButton.getText().equals("Decompress"))
                {
                    Huffman.decompress(filePath);
                }
                dispose();
            }
        });
    }

    public JPanel getPanel() {
        return panel1;
    }

    public JButton getCompressButton(){
        return compressButton;
    }

}
