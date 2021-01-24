package app.fareSys;

import java.util.LinkedList;


public class BusStopNode {
	private String name;
	private LinkedList<BusStopRoute> routeList;
	
	public BusStopNode (String name) {
		this.name = name;
		this.routeList = new LinkedList<>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public LinkedList<BusStopRoute> getRoutes() {
		return routeList;
	}
}