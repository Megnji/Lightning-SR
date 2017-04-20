package functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import bean.Connection;
import uiElements.MainFrame;
import uiElements.PlotPanel;

/**
 * Handle the load of .alist files
 * @author Zhe WEN
 *
 */
public class LoadAlist {

	public static void loadAlistFile(File file){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int line = 0;
			String currentLine = br.readLine();
			int numOfLines = Integer.parseInt(currentLine);
			
			currentLine = br.readLine();
			ArrayList<Connection> hostGraph = new ArrayList<Connection>();
			ArrayList<Integer> activeVertices = new ArrayList<Integer>();
			while (currentLine != null){
				
				if (!currentLine.equals("")){
					String[] strs = currentLine.split("\\s+");
					for (String s:strs){
						hostGraph.add(new Connection(line,Integer.parseInt(s)));
					}
					activeVertices.add(line);
				}
				currentLine = br.readLine();
				line ++;
			}
			PlotPanel.setDotList(activeVertices, numOfLines);
			for (Connection c:hostGraph){
				PlotPanel.addConnection(c);
			}
			//PlotPanel.loadHostGraph(hostGraph);
			MainFrame.renewPlotPanel();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
