package functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.Connection;
import bean.PointBean;
import uiElements.PlotPanel;

@Deprecated
/**
 * Deprecated class, used to load .out file, no longer supported
 * @author Zhe WEN
 *
 */
public class LoadPlotData {
	private static List<Integer> _dots = new ArrayList<Integer>();
	private static ArrayList<Integer> _dotList = new ArrayList<Integer>();
	private static ArrayList<PointBean> _list = new ArrayList<PointBean>();
	private static int _maxIndex = 0;
	public static void loadData(String fname){
		String everything = "";
		try(BufferedReader br = new BufferedReader(new FileReader(fname))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        line = br.readLine();
		    }
		    everything = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!everything.equals("")){
			String[] temp = everything.split(",|\\(|\\)|\\s+");
			for (String i : temp){
				
				if (!i.equals("")){
					int index= Integer.parseInt(i);
					_dots.add(index);
					PointBean p = new PointBean(index);
					if (!_list.contains(p)){
						_list.add(p);
					}
					if (!_dotList.contains(index)){
						_dotList.add(index);
					}
					if (index > _maxIndex){
						_maxIndex= index;
					}
				}
			}
			
			
			PlotPanel.setDotList(_dotList,_maxIndex);
			
			for (int i=0;i<_dots.size();i+=2){
				Connection c = new Connection(_dots.get(i),_dots.get(i+1));
				PlotPanel.addConnection(c);
			}						
		}		
	}
}
