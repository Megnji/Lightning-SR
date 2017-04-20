package uiElements;



import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 * Left panel that used to display Chimera graph and embedding information
 * Author : Zhe WEN
 */
public class PlotPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PlotPanel() {
		super();
		setBackground(Color.white);
		setBorder(BorderFactory.createBevelBorder(1));
	}
	
}
