package bean;

/**
 * Used for recording the points
 * @author Zhe WEN
 *
 */
public class PointBean {

	public int _x;
	public int _y;
	public int _index;
	public boolean _valid = true;
	
	public PointBean(int index){
		_index = index;
		_x = -10;
		_y = -10;
	}
	
	public PointBean(int index,int x,int y){
		_index = index;
		_x = x;
		_y = y;
	}
}
