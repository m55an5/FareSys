import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import app.fareSys.TransitManagementSys;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
	
	TransitManagementSys tms;
	List<String[]> tapRecords = new ArrayList<>();
	String csv_taps_file = "taps_test.csv";
	String csv_trips_file = "trips.csv";
	String bashCmdOutput = "";
	String actualCost = "";
	String actualStatus ="";
	List<String> lines = new ArrayList<String>();
	
	File file;
	
	@Given("There are three bus stop Stop1, Stop2 and Stop3")
	public void there_are_three_bus_stop_stop1_stop2_and_stop3() {
	    // Currently app has built in Bus stops, once we have ability to add more we can use this step
	    assert true;
	}

	@Given("Trip Cost from Stop1 to Stop2 is ${double}")
	public void trip_cost_from_stop1_to_stop2_is_$(Double double1) {
		assert true;
	}

	@Given("Trip Cost from Stop2 to Stop3 is ${double}")
	public void trip_cost_from_stop2_to_stop3_is_$(Double double1) {
		assert true;
	}

	@Given("Trip Cost from Stop1 to Stop3 is ${double}")
	public void trip_cost_from_stop1_to_stop3_is_$(Double double1) {
		assert true;
	}

	@Given("I perform a tap {string} at {string} with {string}")
	public void i_perform_a_tap_at_with(String tapType, String stopID, String pan) {
		tapRecords.add(new String[] {"ID", " DateTimeUTC", " TapType", " StopId", " CompanyId", " BusID", " PAN"});
		tapRecords.add(new String[] {"10", " 24-01-2021 11:58:00", " "+tapType, " "+stopID, " C101", " B101", " "+pan});
	}
	
	@Given("I again perform a tap {string} at {string} with {string}")
	public void i_again_perform_a_tap_at_with(String tapType, String stopID, String pan) {
		tapRecords.add(new String[] {"10", " 24-01-2021 11:58:00", " "+tapType, " "+stopID, " C101", " B101", " "+pan});
	}

	@Given("I create the csv file with above record")
	public void i_create_the_csv_file_with_above_record() throws FileNotFoundException {
		File csvOutputFile = new File(csv_taps_file);
	    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	    	tapRecords.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	    }
	    assertEquals(true, csvOutputFile.exists());
	}

	@When("I run the transit management app")
	public void i_run_the_transit_management_app() throws IOException {
		String [] command = {"bash", "-c", "java -jar ./build/libs/FareSys-1.0.0.jar" +
				" " + csv_taps_file };
		
		bashCmdOutput = runBashCmd(command);
		assertNotEquals("", bashCmdOutput);
	}

	@Then("The trips csv is created")
	public void the_trips_csv_is_created() {
		File csvTripFile = new File(csv_trips_file);
		assertEquals(true, csvTripFile.exists());
	}
	
	@Then("I read the trips csv file")
	public void i_read_the_trips_csv_file() {
		List<String> tmpLines = readCSVFile();
		String[] lineContent = tmpLines.get(0).split(", ");
		actualCost = lineContent[5];
		actualStatus = lineContent[9];
	}

	@Then("The trip status is {string}")
	public void the_trip_status_is(String string) {
	    assertEquals(actualStatus, string);
	}

	@Then("The trip charge is {string}")
	public void the_trip_charge_is(String string) {
		assertEquals(actualCost, string);
	}
	
	public String runBashCmd(String[] command) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(command);
		
		BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));
		
		String line = null;
		String out = "";
		while ((line = stdInput.readLine()) != null) {
			out += line;
		}
		return out;
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
	
	public List<String> readCSVFile() {
		BufferedReader br = null;
		String line = "";
		try {
			File file = new File(csv_trips_file);
			if (file.exists()) {
				br = new BufferedReader(new FileReader(file));
				String csvHeader = br.readLine();
				while ( (line = br.readLine()) != null) {
					lines.add(line);
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
		return lines;
	}
}
