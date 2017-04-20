package uiElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import bean.Connection;
import bean.Connection.ConnectionType;
import bean.PointBean;

/**
 * Left panel that used to display Chimera graph and embedding information
 * Author : Zhe WEN
 */
public class PlotPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean showGrid = false;
	public static boolean showOLine = false;
	
	
	public static boolean changed = false;
	
	//Variable to control the display of host graph
	private static boolean _draw = false;
	private static int _radiusOfDots = 8;
	//All positions of the points, including the faulty qubits (at location (0,0))
	private static ArrayList<PointBean> _list = new ArrayList<PointBean>();
	//All active qubits' index
	private static ArrayList<Integer> _listOfIndex = new ArrayList<Integer>();
	private static int _maxIndex= 0;
	//All connection between qubits, the coordinate is based on left-up point of the acutal graph
	private static ArrayList<Connection> _connections = new ArrayList<Connection>();
	private static int _index = 0;
	//Zoom rate
	private static int _zoomRate = 1;
	
	/**
	 * Create the panel.
	 */
	public PlotPanel() {
		super();
		setBackground(Color.white);
		setBorder(BorderFactory.createBevelBorder(1));
	}
	
	/*
	 * Add connection into plot panel
	 */
	public static void addConnection(Connection c){
		for (Connection cp:_connections){
			if ((c._pa == cp._pa && c._pb == cp._pb) || (c._pb == cp._pa && c._pa == cp._pb)){
				cp._type = c._type;
				cp._weight = c._weight;
				cp._indexGroup = c._indexGroup;
				return;
			}
		}
		_connections.add(c);
	}
	
	//determine whether the connection is already existed.
	public static boolean connectionExist(Connection c){
		for (Connection cp:_connections){
			if ((c._pa == cp._pa && c._pb == cp._pb) || (c._pb == cp._pa && c._pa == cp._pb)){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Return true if a qubit is active qubit
	 */
	public static boolean dotExist(int index){
		return _listOfIndex.contains(index);
	}
	
	/*
	 * Display the whole set of connections that belongs to one group
	 */
	public static void showGroup(Connection c){
		int num = 0;
		for (Connection c2: _connections){
			if (c2._indexGroup == c._indexGroup && c2._indexGroup != -1){
				c2.isClicked = true;
				num ++;
			}else{
				c2.isClicked = false;
			}
		}
		
		InfoPanel.setGroupNum(num);
	}
	

	/*
	 * Add a set of connections
	 */
	public static void loadConnections(ArrayList<Connection> list){

		for (Connection c: _connections){
			c._type = ConnectionType.host;
		}
		for (Connection c: list){
			addConnection(c);
		}
		
	}
	
	/**
	 * Get the information at point p
	 * @param p : clicked position
	 * @return a string that contains click information
	 */
	public static String getClickInfo(Point p){
		
		System.out.println("Zoom rate:"+ _zoomRate + "Clicked:" +p.toString());
		
		int offset = 4;
		if (_zoomRate != 1){
			for (PointBean pb : _list){
				pb._x = pb._x*_zoomRate;
				pb._y = pb._y*_zoomRate;
			}
			offset = offset * _zoomRate;
		}
		PointBean tempP = _list.get(0);
		System.out.println("Point " + _list.get(0)._index + ":" + _list.get(0)._x + ' '+ _list.get(0)._y);
		String result = "";
		double distance; 
		distance = Math.sqrt(Math.pow((tempP._x +offset - p.x),2) + Math.pow((tempP._y +offset-p.y),2));
		System.out.println("distance : " + distance + " offset: "+ offset);
		for (PointBean pb : _list){
				distance = Math.sqrt(Math.pow((pb._x +offset - p.x),2) + Math.pow((pb._y +offset-p.y),2));
				if (distance <= offset){
					InfoPanel.setClickInfo(pb._index);
					result = "Point clicked: "+pb._index+" "+pb._x+ " "+pb._y +" "+p.x +" "+p.y + " with distance: "+ distance;
					return result;
				}

		}
		
		for (Connection c: _connections){
			PointBean pa = _list.get(c._pa);
			PointBean pb = _list.get(c._pb);
			
//			if (pa._x == pb._x && pa._y<pb._y){ //Vertical line && pa at the top
//				if (Math.abs(pa._x - p.x) < 12 && p.y > pa._y && p.y < pb._y){
//					showGroup(c);
//					return "Line clicked:" +pa._index + " to " + pb._index;
//				}
//			}else if (pa._x == pb._x && pa._y>pb._y){ //Vertical line && pb at the top
//				if (Math.abs(pa._x - p.x) < 12 && p.y <pa._y && p.y > pb._y){
//					showGroup(c);
//					return "Line clicked:" +pa._index + " to " + pb._index;
//				}
//			}else if (pa._y == pb._y && pa._x < pb._x){ //Horizontal line && pa at the left
//				if (Math.abs(pa._y - p.y) < 12 && p.x >pa._x && p.x < pb._x){
//					showGroup(c);
//					return "Line clicked:" +pa._index + " to " + pb._index;
//				}
//			}else if (pa._y == pb._y && pa._x > pb._x){ //Horizontal line && pb at the left
//				if (Math.abs(pa._y - p.y) < 12 && p.x < pa._x && p.x > pb._x){
//					showGroup(c);
//					return "Line clicked:" +pa._index + " to " + pb._index;
//				}
//			}else {
				// The distance between the dot and the line of two points
			    
			    double x0,y0,x1,x2,y1,y2;
			    x0 = (double)p.x;
			    y0 = (double)p.y;
			    x1 = (double)pa._x +offset;
			    y1 = (double)pa._y +offset;
			    x2 = (double)pb._x +offset;
			    y2 = (double)pb._y +offset;
			    distance = Math.abs((y2-y1)*x0 - (x2-x1) * y0 + x2*y1 - y2*x1) / 
						Math.sqrt(Math.pow((y2 - y1),2) + Math.pow((x2-x1),2)); 
			    if (x1 == x2 && (y0< Math.min(y1, y2)||y0> Math.max(y1, y2) )){
			    	distance = 100;
			    }else if (y1 == y2 && (x0 < Math.min(x1, x2)|| x0 > Math.max(x1, x2))){
			    	distance = 100;
			    }else if (x1 != x2 && y1 != y2 && (x0 < Math.min(x1, x2) || x0> Math.max(x1, x2)|| 
			    		 y0 < Math.min(y1,y2) || y0> Math.max(y1, y2))){
			    	distance = 100;
			    }
				
				if (distance <= 5){
					showGroup(c);
					if (c._type == ConnectionType.host && !_draw){
						
					}else{
						InfoPanel.setClickInfo(pa._index, pb._index,c._type,c._weight);
					}
					return "Line clicked:" +pa._index + " to " + pb._index +" distance" + distance;
				}
//			}
		}
		Connection temp = new Connection(0,0); //A temporary connection that reset the click state
		showGroup(temp);
		InfoPanel.resetClick();
		return "Nothing clicked";
	}
	
	/**
	 * Set whether the host graph should be draw
	 * @param draw : boolean value
	 */
	public static void setHostDraw(boolean draw){
		_draw = draw;
	}
	
	private void drawConnections(Graphics g){
		for (Connection c:_connections){
			if (c.isClicked){
				g.setColor(Color.RED);
			}else if(c._type == ConnectionType.embedding){
				g.setColor(Color.BLUE);
			}else if (c._type == ConnectionType.Q){
				g.setColor(Color.GREEN);
			}else {
				g.setColor(Color.lightGray);
			}
			
			if (!_draw && c._type == ConnectionType.host){
				//do nothing
			}else{
				g.drawLine(_list.get(c._pa)._x+_radiusOfDots/2, _list.get(c._pa)._y+_radiusOfDots/2, 
						_list.get(c._pb)._x+_radiusOfDots/2, _list.get(c._pb)._y+_radiusOfDots/2);
			}
			
		}
	}
	
	/**
	 * Disposed function, could be used to draw background grids
	 * @param g : Graphics
	 */
	private void drawGrid(Graphics g){
		Graphics2D g2d = (Graphics2D)g.create();
		
		int height = getHeight();
		int width = getWidth();
		g2d.setColor(Color.gray);
		for (int i=0; i <height; i += 5){
			g2d.drawLine(0, i, width, i);
		}
		for (int i=0; i <width; i += 5){
			g2d.drawLine(i, 0, i, height);
		}
		
		g2d.dispose();
	}
	
	/**
	 * Draw the set of qubits
	 * @param g
	 */
	private void drawBoxs(Graphics g){
		int size = (int)Math.ceil(Math.sqrt(_maxIndex / 8));
		for (int i=0; i<size * 100; i+=100){
			for (int j=0; j<size * 100; j+=100){
				drawBox(g,i,j);
				
			}
		}
	}
	
	/**
	 * Draw the qubits within one set
	 * @param g
	 * @param x : start x position of the set
	 * @param y : start y position of the set
	 */
	private void drawBox(Graphics g,int x,int y){
		drawPoint(g,x+50,y+30);
		drawPoint(g,x+50,y+40);
		drawPoint(g,x+50,y+60);
		drawPoint(g,x+50,y+70);
		drawPoint(g,x+30,y+50);
		drawPoint(g,x+40,y+50);
		drawPoint(g,x+60,y+50);
		drawPoint(g,x+70,y+50);
	}
	
	/**
	 * Draw host graph within one set of qubits, disposed
	 * @param g
	 */
	private void drawLines(Graphics g){
		for (int i=0; i<_list.size(); i++){
			int index = _list.get(i)._index;
			if ( index % 8 < 4){
				connectTwoPoints(g,index,(index/8)*8+4);
				connectTwoPoints(g,index,(index/8)*8+5);
				connectTwoPoints(g,index,(index/8)*8+6);
				connectTwoPoints(g,index,(index/8)*8+7);
			}
		}
	}
	
	/**
	 * Connect two qubit
	 * @param g
	 * @param a : index of first qubit
	 * @param b : index of second qubit
	 */
	private void connectTwoPoints(Graphics g,int a,int b){
		int xa=0,xb=0,ya=0,yb=0;
				xa = _list.get(a)._x+_radiusOfDots/2;
				ya = _list.get(a)._y+_radiusOfDots/2;
				xb = _list.get(b)._x+_radiusOfDots/2;
				yb = _list.get(b)._y+_radiusOfDots/2;

		
		if (xa != 0 && xb != 0 && ya !=0 && yb !=0){
			g.drawLine(xa, ya, xb, yb);
		}
	}
	
	/**
	 * Draw a qubit with its number
	 * @param g
	 * @param x
	 * @param y
	 */
	private void drawPoint(Graphics g,int x, int y){
		int actualRadius = _radiusOfDots;
		Graphics2D g2d = (Graphics2D) g;
		if (_listOfIndex.contains(_index)){
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 3)); 
			g2d.setStroke(new BasicStroke(0.3f));
			g2d.drawOval(x, y, actualRadius, actualRadius);
			g2d.drawString(Integer.toString(_index), x+1, y+5);
			PointBean point = new PointBean(_index,x,y);
			
			point = new PointBean(_index,x,y);
			_list.add(_index, point);
		}else{
			PointBean point = new PointBean(_index,0,0);
			_list.add(_index, point);
		}
		
		_index++;
	}
	
	/**
	 * Set status of all connection to host, should be used on reloading embedding file
	 */
	public static void resetStatus(){
		for (Connection c:_connections){
			c._type = ConnectionType.host;
			c._weight = 0;
		}
	}
	
	/**
	 * Set active set of qubits, should be used upon change host graph
	 * @param l
	 * @param maxIndex
	 */
	public static void setDotList(ArrayList<Integer> l, int maxIndex){
		_listOfIndex = l;
		_maxIndex = maxIndex;
	}
	
	/**
	 * Set zoom rate of the plot panel
	 * @param rate
	 */
	public static void setZoomeRate(int rate){
		_zoomRate = rate;
	}
	
	/**
	 * Get zoom rate
	 * @return int: zoom rate
	 */
	public static int getZoomRate(){
		return _zoomRate;
	}
	
	/**
	 * Reset zoom rate to 1
	 */
	public static void resetZoomRate(){
		_zoomRate = 1;
	}
	public void renewInfoPanel(){
		ArrayList<Integer> list= new ArrayList<Integer>();
		int num = 0;
		for (Connection c: _connections){
			if (c._type == ConnectionType.embedding || c._type == ConnectionType.Q){
				if (!list.contains(c._pa)){
					list.add(c._pa);
				}
				num ++;
				if (!list.contains(c._pb)){
					list.add(c._pb);
				}
			}

		}
		
		InfoPanel.setEdges(num);
		InfoPanel.setPhysicQubitNum(list.size());
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if (showGrid){
			drawGrid(g);
		}

		if (showOLine){
			drawLines(g);
		}
		


		_index = 0;
		_list.clear();
		
		if (_zoomRate != 1){
			setPreferredSize(new Dimension(1200*_zoomRate,1200*_zoomRate));
			Graphics2D g2 = (Graphics2D) g;
			g2.scale((double)_zoomRate, (double)_zoomRate);
			
		}else{
			setPreferredSize(new Dimension(1500,1500));
		}
		drawBoxs(g);
		drawConnections(g);
		
		
		renewInfoPanel();
	}
}
