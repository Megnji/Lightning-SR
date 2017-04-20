package functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import bean.Connection;
import uiElements.PlotPanel;

/**
 * The functions of load .dwave files
 * @author Zhe WEN
 *
 */
public class LoadIsing {
	
	public static void loadQUBOFile(File file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int line = 0;
			String currentLine = br.readLine();
			//int numOfDots = 0;
			while (currentLine != null){
				if (line == 0){
					//numOfDots = Integer.parseInt(currentLine.split("\\s+")[1]);
				}else{
					String[] strs = currentLine.split("\\s+");
					int pa = Integer.parseInt(strs[0]);
					int pb = Integer.parseInt(strs[1]);
					double weight = Double.parseDouble(strs[2]);
					if (weight > 0){
						Connection c = new Connection(pa,pb,weight);
						PlotPanel.addConnection(c);
					}
				}
				line ++;
				currentLine = br.readLine();
				
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
