package uiElements;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;


public class MainFrame {

	private JFrame frame;
	private static PlotPanel plotPanel;
	private static InfoPanel infoPanel;
	private static JScrollPane jsp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame f = new MainFrame();
					f.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		infoPanel = new InfoPanel();
		infoPanel.setPreferredSize(new Dimension(500,800));
		plotPanel = new PlotPanel();
		plotPanel.setPreferredSize(new Dimension(1200,1200));
		jsp = new JScrollPane(plotPanel);
		jsp.setMinimumSize(new Dimension(500,500));
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp,infoPanel);
		JMenuBar menuBar = new MainMenu();
		
		
		frame.setJMenuBar(menuBar);
		frame.add(sp);
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sp.setResizeWeight(0.5);
	}
	
	public static double getVerPosition(){
		double percentage = 0;
		int current =jsp.getVerticalScrollBar().getValue();
		int max = jsp.getVerticalScrollBar().getMaximum();
		percentage = current / max;
		return percentage;
	}
	
	public static double getHonPosition(){
		double percentage = 0;
		int current =jsp.getHorizontalScrollBar().getValue();
		int max = jsp.getHorizontalScrollBar().getMaximum();
		percentage = current / max;
		return percentage;
	}
	
	public static void setScrollBar(double v, double h){
		int vmax = jsp.getVerticalScrollBar().getMaximum();
		int hmax = jsp.getHorizontalScrollBar().getMaximum();
		int vAfter =  (int) v * vmax;
		int hAfter = (int) h * hmax;
		
		jsp.getVerticalScrollBar().setValue(vAfter);
		jsp.getHorizontalScrollBar().setValue(hAfter);
		
	}
	
	public static void renewPlotPanel(){
		plotPanel.repaint();
		jsp.repaint();
		jsp.getVerticalScrollBar().repaint();
		jsp.getHorizontalScrollBar().repaint();
	}
	
	public static void renewInfoPanel(){
		infoPanel.repaint();
	}
	
	public static void showError(String error){
		JOptionPane.showMessageDialog(plotPanel, error);
	}
	
}
