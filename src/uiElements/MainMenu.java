package uiElements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Top menu
 * 
 * @author megnji
 *
 */
public class MainMenu extends JMenuBar implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1521768584529278585L;
    private JMenuItem openItem, loadHost;
    int returnVal;
    JFileChooser fileChooser;
    File file;

    BufferedReader br;
    public static String fileName;
    public static String graphOrder = "";
    public static String physicalQ = "";
    public static String logicalQ = "";
    public static String embeddings = "";
    public static String[] embedding;

    public MainMenu() {
        super();
        initialize();
    }

    private void initialize() {

        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("new");
        openItem = new JMenuItem("open..");
        openItem.addActionListener(this);
        loadHost = new JMenuItem("Load Host..");
        loadHost.addActionListener(this);
        JMenuItem saveItem = new JMenuItem("save");
        JMenuItem saveAsItem = new JMenuItem("save as..");
        saveItem.setEnabled(false);
        saveAsItem.setEnabled(false);
        newItem.setEnabled(false);
        fileMenu.add(newItem);
        fileMenu.addSeparator();

        fileMenu.add(openItem);
        fileMenu.add(loadHost);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setEnabled(false);
        helpMenu.add(aboutItem);

        add(fileMenu);
        add(helpMenu);
        fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
    }

    public void actionPerformed(ActionEvent e) {
        String currentLine;
        if (e.getSource().equals(openItem)) {
            returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                fileName = file.getName();

                try {
                    br = new BufferedReader(new FileReader(file));

                    while ((currentLine = br.readLine()) != null) {
                        String[] strs = currentLine.split(" ");
                        if (strs.length > 0 && strs[0].matches("\\w\\d")) {
                            System.out.println("   " + currentLine);
                        }
                    }
                } catch (FileNotFoundException e2) {

                    e2.printStackTrace();
                    MainFrame.showError("The file you chosen is invalid!");

                } catch (IOException e1) {

                    e1.printStackTrace();
                }

                MainFrame.renewInfoPanel();
                MainFrame.renewPlotPanel();
            }
        }

    }

    public String getFileName() {
        return fileName;
    }
}
