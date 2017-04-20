package bean;

/**
 * Jave bean to hold the connection information
 * @author Zhe WEN
 *
 */
public class Connection {

	//index of point a
	public int _pa;
	
	//index of point b
	public int _pb;
	
	public int _indexGroup = -1 ;
	//type of connection
	public enum ConnectionType{host,embedding,Q};
	public boolean isClicked = false;
	public ConnectionType _type = ConnectionType.host;
	
	public double _weight = 0;
	public Connection(int pa,int pb){
		_pa = pa;
		_pb = pb;
	}
	
	public Connection(int pa,int pb, ConnectionType type){
		this(pa,pb);
		_type = type;
	}
	
	
	public Connection(int pa,int pb, Double weight){
		this(pa,pb);
		_weight = weight;
	}
	
	public Connection(int pa,int pb, ConnectionType type, Double weight){
		this(pa,pb);
		_type = type;
		_weight = weight;
	}
	
	public Connection(int pa,int pb, ConnectionType type, int groupIndex){
		this(pa,pb);
		_type = type;
		_indexGroup = groupIndex;
	}
	public static boolean sameAs(Connection a, Connection b){
		if (a._pa == b._pa && a._pb == b._pb)
			return true;
		else if (a._pa == b._pb && a._pb == b._pa)
			return true;
		else return false;
	}
}
