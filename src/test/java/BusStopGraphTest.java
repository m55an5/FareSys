

import app.fareSys.BusStopGraph;
import app.fareSys.BusStopNode;
import junit.framework.TestCase;



public class BusStopGraphTest extends TestCase{
	
	private BusStopGraph bsg;
	
	public void setUp() throws Exception {	
		super.setUp();
		
		bsg = new BusStopGraph();
	}
	
	public void testAddBusStopRoute() {
		// given
		BusStopNode v1 = new BusStopNode("Stop1");
		BusStopNode v2 = new BusStopNode("Stop2");
		
		bsg.addBusStopNode(v1);
		bsg.addBusStopNode(v2);
		
		//when
		boolean result = bsg.addBusStopRoute(v1, v2, 5);
		
		//then 
		assertEquals(true, result);
		
	}
	
	public void testGetMaxRouteCost() {
		// given
		BusStopNode v3 = new BusStopNode("Stop3");
		BusStopNode v4 = new BusStopNode("Stop4");
		BusStopNode v5 = new BusStopNode("Stop5");
		
		bsg.addBusStopNode(v3);
		bsg.addBusStopNode(v4);
		bsg.addBusStopNode(v5);
		
		bsg.addBusStopRoute(v3, v4, 5);
		bsg.addBusStopRoute(v3, v5, 15);
		
		//when
		int cost = bsg.getMaxRouteCost(v3);
		
		//then
		assertEquals(15, cost);
	}
	
	public void testGetBusStopNodeNegative() {
		//given
		String busStop = "Stop6";
		
		//when
		BusStopNode temp = bsg.getBusStopNode(busStop);
		
		//then
		assertEquals(null, temp);	
	}
	
	public void testGetBusStopNodePositive() {
		//given
		BusStopNode v5 = new BusStopNode("Stop5");
		bsg.addBusStopNode(v5);
		
		//when
		BusStopNode temp = bsg.getBusStopNode(v5.getName());
		
		//then
		assertEquals(v5.getName(), temp.getName());	
	}
	
}
