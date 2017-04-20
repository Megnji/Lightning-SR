package functions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import bean.Connection;
import bean.Connection.ConnectionType;
import uiElements.InfoPanel;
import uiElements.PlotPanel;

/**
 * Helping class of LoadQUBO
 * @author Zhe WEN
 *
 */
public class PlotInfoHandler {

	public static void updateConnection(String info, ArrayList<Connection> setList){
		int numOfUnit = 0;
		int maxLength = 0;
		String[] units =info.split("\\]|\\[");
		String[][] holder = new String[units.length][];
		
		for (String unit:units){
			if (unit.length() > 0){
				if (Character.isDigit(unit.charAt(0))){
					holder[numOfUnit] = unit.split(",|\\s+");
					if (holder[numOfUnit].length > maxLength){
						maxLength = holder[numOfUnit].length;
					}
					numOfUnit++;
				}
			}

		}
		
		int[][] numHolder = new int[numOfUnit][maxLength];
		for (int i=0; i< units.length; i++){
			
			if (holder[i] != null){
				int index= 0;
				for (String str: holder[i]){
					if (str != null && !str.equals("")){
						numHolder[i][index] = Integer.parseInt(str);
						index ++ ;
					}
				}
				//Set the rest empty spaces for the set to be -1, which cannot be connected
				for (int j=index; j<maxLength; j++){
					numHolder[i][j] = -1;
				}
			}

		}
		final int num = numOfUnit;
		
		SwingWorker<ArrayList<Connection>, Void> worker = new SwingWorker<ArrayList<Connection>, Void>() {
		    @Override
		    public ArrayList<Connection> doInBackground() {
		    	ArrayList<Connection> list = new ArrayList<Connection>();
				for (int i=0; i<num; i++){
					randomConnection(numHolder[i],i);
				}
			    boolean allConnected = true;
			    ArrayList<Integer> faultyQubits = new ArrayList<Integer>();
				for (Connection c: setList){
					if (!connectTwoSets(numHolder[c._pa-1],numHolder[c._pb-1])){
						for (int j:numHolder[c._pa-1]){
							if (!PlotPanel.dotExist(j) && !faultyQubits.contains(j) && j>-1){
								faultyQubits.add(j);
							}
						}
						for (int j:numHolder[c._pb-1]){
							if (!PlotPanel.dotExist(j) && !faultyQubits.contains(j) && j>-1){
								faultyQubits.add(j);
							}
						}
						allConnected = false;
					}
				}
				if (!allConnected){
					String errorMsg = "";
					for (int j:faultyQubits){
						errorMsg = errorMsg + j+ " ";
					}
					InfoPanel.setErrorMsg(errorMsg);
				}
		        return list;
		    }

		    @Override
		    public void done() {
		    	ArrayList<Connection> list;
				try {
					list = (ArrayList<Connection>)this.get();
					for (Connection c: list){
						PlotPanel.addConnection(c);
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		    }
		};

		worker.execute();
		int order = 0;
		for (int[] set: numHolder){
			int currentOrder = 0;
			for (int i : set){
				if (i > -1){
					currentOrder ++;
				}
			}
			if (currentOrder > order){
				order = currentOrder;
			}
		}
		
		InfoPanel.setGraphOrder(order);
		InfoPanel.setLogicQubit(numOfUnit);
	}
	

	private static boolean connectTwoSets(int[] set1, int[] set2){
		for (int i=0; i<set1.length; i++){
			for (int j=0; j< set2.length; j++){
				Connection c= new Connection(set1[i],set2[j],ConnectionType.Q);
				if (PlotPanel.connectionExist(c)){
					PlotPanel.addConnection(c);
					return true;
				}
			}
		}
		
		return false;
	}
	
	//Index is used for detect the connections within same group
	private static void randomConnection(int[] dots,int index){
		ArrayList<Integer> connected = new ArrayList<Integer>();
		boolean firstTimeConnect = false;
		for (int i=0; i< dots.length ; i++){
				for (int j=0; j< dots.length; j++){
					if (!firstTimeConnect || connected.contains(dots[i]) || connected.contains(dots[j])){
						Connection c = new Connection(dots[i],dots[j],ConnectionType.embedding,index);
						if (PlotPanel.connectionExist(c)){
							if (!connected.contains(dots[i])){
								connected.add(dots[i]);
							}
							if (!connected.contains(dots[j])){
								connected.add(dots[j]);
							}
					   
							PlotPanel.addConnection(c);
							firstTimeConnect = true;
							//System.out.println(dots[i] + " " + dots[j]);
							break;
						}
					}	
				}
		}
	}
	
}
