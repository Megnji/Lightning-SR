package uiElements;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * Right panel that used to display the informations
 * @author Yuankai WANG
 *
 */
public class InfoPanel extends JPanel implements ItemListener,ActionListener{
	static JTextArea textAreaFN = new JTextArea("Default File");
	/**
	 * 
	 */
	private static final long serialVersionUID = -1936736402101917359L;
	private static JTextField textPhysicalQ;
	private static JTextField textEdgesShown;
	private static JTextField textGraphOrder;
	private static JTextField textLogicalQ;
	
	private static JLabel itemClicked;
	private static JLabel label1,label2,label3,label4,label5,errorMsg;
	private static JLabel hostLabel;
	
	private static JButton zoomin,zoomout;
	private static JCheckBox box1, box2;
	
	/**
	 * Create the panel.
	 */
	public InfoPanel() {
		super();
		initialize();
	}
	public static void addFileName(){
		String fileName = MainMenu.fileName;
		textAreaFN.setText(fileName);
	}
	public static void addGraphOrder(){
		String graphOrder = MainMenu.graphOrder;
		textGraphOrder.setText(graphOrder);
	}
	public static void addPhysicalQ(){
		String physicalQ = MainMenu.physicalQ;
		textPhysicalQ.setText(physicalQ);
	}

	public static void addLogicalQ(){
		String logicalQ = MainMenu.logicalQ;
		textLogicalQ.setText(logicalQ);
	}


	
	private void initialize(){
		JLabel infoLabel = new JLabel("Embedding Information");
		infoLabel.setBounds(100, 10, 200, 14);
		setLayout(null);
		add(infoLabel);
		
		textAreaFN.setBounds(115, 265, 244, 24);
		//JLabel fileNameL = new JLabel("Current File: ");
		add(textAreaFN);
		
		JLabel lblCurrentFile = new JLabel("Current File:");
		lblCurrentFile.setBounds(27, 265, 200, 24);
		add(lblCurrentFile);
		
		JLabel lblNewLabel = new JLabel("Physical qubits used");
		lblNewLabel.setBounds(27, 55, 129, 14);
		add(lblNewLabel);
		
		JLabel lblEdgesShown = new JLabel("Edges Shown");
		lblEdgesShown.setBounds(27, 94, 129, 14);
		add(lblEdgesShown);
		
		JLabel lblGraphOrder = new JLabel("Max chain size");
		lblGraphOrder.setBounds(27, 130, 129, 14);
		add(lblGraphOrder);
		
		JLabel lblLogicalQubitsUsed = new JLabel("Logical qubits used");
		lblLogicalQubitsUsed.setBounds(27, 167, 129, 14);
		add(lblLogicalQubitsUsed);
		
		textPhysicalQ = new JTextField("0");
		textPhysicalQ.setBounds(215, 52, 86, 20);
		add(textPhysicalQ);
		textPhysicalQ.setColumns(10);
		
		textEdgesShown = new JTextField("0");
		textEdgesShown.setBounds(215, 91, 86, 20);
		add(textEdgesShown);
		textEdgesShown.setColumns(10);
		
		textGraphOrder = new JTextField("0");
		textGraphOrder.setBounds(215, 127, 86, 20);
		add(textGraphOrder);
		textGraphOrder.setColumns(10);
		
		textLogicalQ = new JTextField("0");
		textLogicalQ.setBounds(215, 164, 86, 20);
		add(textLogicalQ);
		textLogicalQ.setColumns(10);
		//set text fields to be uneditable
		textAreaFN.setEditable(false);
		textPhysicalQ.setEditable(false);
		textLogicalQ.setEditable(false);
		textEdgesShown.setEditable(false);
		textGraphOrder.setEditable(false);
		
		JLabel hostInfo = new JLabel("Host information");
		hostInfo.setBounds(100,190,150,20);
		add(hostInfo);
		
		hostLabel = new JLabel("L = 4  M = 12  N = 12");
		hostLabel.setBounds(27,210,200,20);
		add(hostLabel);
		
		itemClicked = new JLabel("No item clicked");
		itemClicked.setBounds(60, 300, 150, 24);
		add(itemClicked);
		
		label1 = new JLabel("");
		label1.setBounds(27,330, 150, 24);
		add(label1);
		
		label2 = new JLabel("");
		label2.setBounds(200, 330, 150, 24);
		add(label2);
		
		label3 = new JLabel("");
		label3.setBounds(27, 360, 150,24);
		add(label3);
		
		label4 = new JLabel("");
		label4.setBounds(200, 360, 150, 24);
		add(label4);
		
		label5 = new JLabel("");
		label5.setBounds(27,390,150,24);
		add(label5);
		
		errorMsg = new JLabel("");
		errorMsg.setBounds(27, 480, 400,100);
		add(errorMsg);
		
		box1 = new JCheckBox("Show Faulty qubits");
		box1.setBounds(27, 420, 200, 24);
		//add(box1);
		
		box2 = new JCheckBox("Show host graph");
		box2.setBounds(27, 450, 200, 24);
		box2.addItemListener(this);
		add(box2);
		
		zoomin = new JButton("Zoom in");
		zoomin.setBounds(27, 480, 150, 30);
		zoomin.addActionListener(this);
		add(zoomin);
		
		zoomout = new JButton("Zoom out");
		zoomout.setBounds(200, 480, 150, 30);
		zoomout.setEnabled(false);
		zoomout.addActionListener(this);
		add(zoomout);
	}
	
	public static void resetError(){
		errorMsg.setText("");
	}
	public static void resetClick(){
		itemClicked.setText("No item clicked");
		label1.setText("");
		label2.setText("");
		label3.setText("");
		label4.setText("");
		label5.setText("");
	}
	public static void setClickInfo(int i){
		itemClicked.setText("Vertice clicked");
		label1.setText("Vertice number: "+ i);
		label2.setText("");
		label3.setText("");
		label4.setText("");
		label5.setText("");
	}
	
	public static void setGroupNum(int i){
		if (i > 0){
			label4.setText("Group edges number: "+ i);
		}
	}
	public static void setPhysicQubitNum(int i){
		textPhysicalQ.setText("" + i);;
	}
	
	public static void setEdges(int i){
		textEdgesShown.setText(""+ i);
	}
	
	public static void setGraphOrder(int i){
		textGraphOrder.setText("" + i);
	}
	
	public static void setLogicQubit(int i){
		textLogicalQ.setText("" + i);
	}
	
	public static void setErrorMsg(String error){
		errorMsg.setText("<HTML>" + "Faulty qubits used : " +error + "</HTML>");
	}
	
	public static void setFileName(String fname){
		textAreaFN.setText(fname);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
