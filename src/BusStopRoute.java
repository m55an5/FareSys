class BusStopRoute {
	private int routeCost;
	private BusStopNode destinationVertex;
	
	public BusStopRoute(BusStopNode dest, int weight) {
		this.routeCost = weight;
		this.destinationVertex = dest;
	}
	
	public int getRouteCost() {
		return this.routeCost;
	}
	
	public BusStopNode getDestinationVertex() {
		return this.destinationVertex;
	}
}