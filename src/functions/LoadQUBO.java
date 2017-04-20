package functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import bean.Connection;

/**
 * Used to handle the load of .QUBO files
 * @author Zhe WEN
 *
 */
public class LoadQUBO {
	
	public static void loadQUBOFile(File file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int line = 0;
			String currentLine = br.readLine();
			int numOfDots = 0;
			ArrayList<Connection> indexList = new ArrayList<Connection>();
			String embeddingInfo = "";
			while (currentLine != null){
				if (line == 0){
					numOfDots = Integer.parseInt(currentLine.split("\\s+")[0]);
				}else if (line>0 && line <= numOfDots){
					String[] strs = currentLine.split("\\s+");
					for (int i=line; i<strs.length; i++){
						int temp = Integer.parseInt(strs[i]);
						if (temp != 0 && line != (i+1)){
							indexList.add(new Connection(line,i+1));
						}
					}
				}else{
					embeddingInfo += currentLine;
				}
				line ++;
				currentLine = br.readLine();
				
			}
			br.close();
			PlotInfoHandler.updateConnection(embeddingInfo,indexList);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
