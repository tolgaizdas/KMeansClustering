import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class App extends JFrame {
    private final int WIDTH = 1280, HEIGHT = 720;

    private File file = null;
    private int totalIterations;
    private int totalCenters;

    private JPanel settingsPanel;

    public App() {
        this.setConfigurations();
    }

    private void setConfigurations() {
        this.setTitle("K-Means Clustering");
        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void init() {
        this.settingsPanel = new JPanel();
        this.addSettings();
        this.setVisible(true);
    }

    private void addSettings() {
        // iteration part
        JLabel iterationLabel = new JLabel("Iterasyon:");
        JTextField iterationBox = new JTextField(4);
        this.settingsPanel.add(iterationLabel);
        this.settingsPanel.add(iterationBox);

        // center part
        JLabel centerLabel = new JLabel("K sayisi:(Center)");
        JComboBox<String> centerBox = new JComboBox<String>(
                new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
        centerBox.setSelectedIndex(0);
        this.settingsPanel.add(centerLabel);
        this.settingsPanel.add(centerBox);

        // file part
        JButton fileButton = new JButton("Dosyadan sec");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                fileChooser.setCurrentDirectory(workingDirectory);
                fileChooser.showOpenDialog(fileChooser);
                file = fileChooser.getSelectedFile();
                // check the file extension (should be .csv)
                if (file != null && !file.getName().substring(file.getName().indexOf("."), file.getName().length())
                        .equals(".csv")) {
                    JOptionPane.showMessageDialog(null, "Dosya \".csv\" uzantili olmalidir.");
                    file = null;
                }
            }
        });
        this.settingsPanel.add(fileButton);

        // k-means clustering part
        JButton clusterButton = new JButton("K-Means Clustering");
        clusterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    JOptionPane.showMessageDialog(null, "Dosya secilmedi.");
                    return;
                }
                try {
                    totalIterations = Integer.parseInt(iterationBox.getText());
                    if (totalIterations < 0)
                        throw new Exception();
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Iterasyon degeri gecerli degil.");
                    return;
                }
                clear();
                totalCenters = centerBox.getSelectedIndex() + 1;
                KMeansClustering kmc = new KMeansClustering(totalIterations, totalCenters, file);
                kmc.cluster(App.this);
                remove(settingsPanel);
                add(settingsPanel, BorderLayout.SOUTH);
                App.this.setVisible(true);
            }
        });
        this.settingsPanel.add(clusterButton);

        // add settings panel
        this.add(this.settingsPanel, BorderLayout.SOUTH);
    }

    private void clear() {
        this.getContentPane().removeAll();
        this.repaint();
        this.add(this.settingsPanel, BorderLayout.SOUTH);
    }
}
