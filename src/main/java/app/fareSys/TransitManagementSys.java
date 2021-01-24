package app.fareSys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransitManagementSys {
	
	private String csvTapsFileame;

	private HashMap<String, Trip> trips = new HashMap<String, Trip>();
	
	BusStopGraph bsg;
	
	public TransitManagementSys(BusStopGraph busStopGraph, String csvfile) {
		this.bsg = busStopGraph;
		this.csvTapsFileame = csvfile;
	}
	
	public String getCsvTapsFileame() {
		return csvTapsFileame;
	}

	public void setCsvTapsFileame(String csvTapsFileame) {
		this.csvTapsFileame = csvTapsFileame;
	}

	private void processFile() {
		readCSVFile();
		processInCompleteTrips();
	}
	
	private void readCSVFile() {
		BufferedReader br = null;
		String line = "";
		try {
			File file = new File(this.csvTapsFileame);
			if (file.exists()) {
				br = new BufferedReader(new FileReader(file));
				String csvHeader = br.readLine();
				while ( (line = br.readLine()) != null) {
					parseLine(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void parseLine(String line) {
		String[] lineContent = line.split(", ");
		Trip trip;
		
		if(!trips.containsKey(lineContent[6])){
			trip = new Trip(lineContent[1], null, lineContent[2], lineContent[3],
					null, lineContent[5], lineContent[4], lineContent[6], "INCOMPLETE", 0);
			trips.put(lineContent[6], trip);
		} else {
			try {
				trip = trips.get(lineContent[6]);
				trip.setFinishTime(lineContent[1]);
				trip.setLastTapType(lineContent[2]);
				trip.setFinishStopID(lineContent[3]);
				String tripDuration = calTripDuration(trip.getStartTime(),trip.getFinishTime());
				trip.setTripDurationSecs(tripDuration);
				
				if ( trip.getStartStopID().equals(trip.getFinishStopID())) {
					trip.setStatus("CANCELLED");
				} else { 
					BusStopNode source = bsg.getBusStopNode(trip.getStartStopID());
					BusStopNode dest = bsg.getBusStopNode(trip.getFinishStopID());
					int cost = bsg.getRouteCost(source, dest);
					trip.setTripCharge(cost);
					trip.setStatus("COMPLETED");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String calTripDuration(String startTime, String finishTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Date iniTime = sdf.parse(startTime);
			Date endTime = sdf.parse(finishTime);
			long diffInMillies = Math.abs(endTime.getTime() - iniTime.getTime());
		    long diffSeconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			
			return Long.toString(diffSeconds);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void processInCompleteTrips() {
		for(String pan: trips.keySet()) {
			Trip trip = trips.get(pan);
			if (trip.getStatus().equals("INCOMPLETE")) {
				BusStopNode source = bsg.getBusStopNode(trip.getStartStopID()); 
				int maxCharge = bsg.getMaxRouteCost(source);
				trip.setTripCharge(maxCharge);
			}
		}
	}
	
	public void printTrips() {
		System.out.println("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status");
		for(String pan: trips.keySet()) {
			Trip trip = trips.get(pan);
			String cost = "$"+Double.toString((double) trip.getTripCharge() / 100);
			System.out.println(trip.getStartTime()+", "+trip.getFinishTime()+", "+trip.getTripDurationSecs()+", "+trip.getStartStopID()+", "+trip.getFinishStopID()
									+", "+cost+", "+trip.getCompanyID()+", "+trip.getBusID()+", "+trip.getPan()+", "+trip.getStatus());
		}
	}
	
	public void createTripsCSV() {
		File csvOutputFile = new File("trips.csv");
		
		try {
//			CSVWriter csv_w = new CSVWriter(new FileWriter(csvOutputFile)); 
			List<String[]> rows = new LinkedList<String[]>();
			rows.add(new String[] {"Started", " Finished", " DurationSecs", " FromStopId", " ToStopId", " ChargeAmount", " CompanyId", " BusID", " PAN", " Status"});
			
			for (String pan: trips.keySet()) {
				Trip trip = trips.get(pan);
				String cost = "$"+Double.toString((double) trip.getTripCharge() / 100);
				String space = " ";
				rows.add(new String[] {trip.getStartTime(), space+trip.getFinishTime(), space+trip.getTripDurationSecs(), space+trip.getStartStopID(),
						space+trip.getFinishStopID(), space+cost, space+trip.getCompanyID(), space+trip.getBusID(), space+trip.getPan(), space+trip.getStatus()});
			}
//			csv_w.writeAll(rows,false);
//			csv_w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void generateTripsCSV() {
		File csvOutputFile = new File("trips.csv");
	    
		try { 
			List<String[]> rows = new LinkedList<String[]>();
			rows.add(new String[] {"Started", " Finished", " DurationSecs", " FromStopId", " ToStopId", " ChargeAmount", " CompanyId", " BusID", " PAN", " Status"});
			
			for (String pan: trips.keySet()) {
				Trip trip = trips.get(pan);
				String cost = "$"+Double.toString((double) trip.getTripCharge() / 100);
				String space = " ";
				rows.add(new String[] {trip.getStartTime(), space+trip.getFinishTime(), space+trip.getTripDurationSecs(), space+trip.getStartStopID(),
						space+trip.getFinishStopID(), space+cost, space+trip.getCompanyID(), space+trip.getBusID(), space+trip.getPan(), space+trip.getStatus()});
			}
			try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
		    	rows.stream()
		          .map(this::convertToCSV)
		          .forEach(pw::println);
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .map(this::escapeSpecialCharacters)
	      .collect(Collectors.joining(","));
	}
	
	public String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
	
	public static void main(String [] args) {
		BusStopGraph busStopGraph = new BusStopGraph();
		
		BusStopNode v1 = new BusStopNode("Stop1");
		BusStopNode v2 = new BusStopNode("Stop2");
		BusStopNode v3 = new BusStopNode("Stop3");
		
		busStopGraph.addBusStopNode(v1);
		busStopGraph.addBusStopNode(v2);
		busStopGraph.addBusStopNode(v3);
		
		busStopGraph.addBusStopRoute(v1, v2, 325);
		busStopGraph.addBusStopRoute(v2, v3, 550);
		busStopGraph.addBusStopRoute(v1, v3, 730);
		
//		busStopGraph.printGraph();
		
		if (args.length != 1) {
			System.out.println("Taps CSV file required");
//			usage();
			return;
		} else {
				TransitManagementSys tms = new TransitManagementSys(busStopGraph, args[0]);
				try {
					tms.processFile();
					tms.printTrips();
					tms.generateTripsCSV();
				} catch (Exception e) {
					System.out.println(e);
				}
		}		
		
	}
	
}
