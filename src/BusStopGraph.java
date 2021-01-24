import java.util.HashSet;

public class BusStopGraph {

	private HashSet<BusStopNode> busStopNodes;
	
	public BusStopGraph() {
		busStopNodes = new HashSet<>();
	}
	
	public BusStopNode getBusStopNode(String busStop) {
		for (BusStopNode bsn: busStopNodes) {
			if (bsn.getName().equals(busStop)) {
				return bsn;
			}
		}
		return null;
	}

	public boolean addBusStopRoute(BusStopNode v1, BusStopNode v2, int weight) {
		return ( v1.getRoutes().add(new BusStopRoute(v2, weight)) && 
				v2.getRoutes().add(new BusStopRoute(v1, weight)) );
	}
	
	public boolean addBusStopNode(BusStopNode v) {
		return busStopNodes.add(v);
	}
	
	public int getRouteCost(BusStopNode source, BusStopNode dest) {
		
		for (BusStopRoute bsr : source.getRoutes()) {
			if(bsr.getDestinationVertex().equals(dest)) {
				return bsr.getRouteCost();
			}
		}
		return 0;
	}
	
	public int getMaxRouteCost(BusStopNode source) {
		int max = 0;
		for (BusStopRoute bsr : source.getRoutes()) {
			if ( bsr.getRouteCost() > max) {
				max = bsr.getRouteCost();
			} 
		}
		
		return max;
	}
	
	public void printGraph() {
		for (BusStopNode v : busStopNodes) {
			System.out.print("Bus Stop name: " + v.getName() + ": ");
			
			for (BusStopRoute e : v.getRoutes()) {
				System.out.print("Destination Vertex: " + e.getDestinationVertex().getName() 
						+ " weight: " + e.getRouteCost() + " | ");
			}
			System.out.println("\n");
		}
		
	}
	
}


