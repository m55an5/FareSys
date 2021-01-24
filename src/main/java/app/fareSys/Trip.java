package app.fareSys;

public class Trip {

	private String startTime;
	private String finishTime;
	private String lastTapType;
	private String startStopID;
	private String finishStopID;
	private String busID;
	private String companyID;
	private String pan;
	private String status;
	private int tripCharge;
	private String tripDurationSecs; 
	
	public Trip(String startTime, String finishTime, String lastTapType, String startStopID, String finishStopID,
			String busID, String companyID, String pan, String status, int tripCharge) {
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.lastTapType = lastTapType;
		this.startStopID = startStopID;
		this.finishStopID = finishStopID;
		this.busID = busID;
		this.companyID = companyID;
		this.pan = pan;
		this.status = status;
		this.tripCharge = tripCharge;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getLastTapType() {
		return lastTapType;
	}

	public void setLastTapType(String lastTapType) {
		this.lastTapType = lastTapType;
	}

	public String getStartStopID() {
		return startStopID;
	}

	public void setStartStopID(String startStopID) {
		this.startStopID = startStopID;
	}

	public String getFinishStopID() {
		return finishStopID;
	}

	public void setFinishStopID(String finishStopID) {
		this.finishStopID = finishStopID;
	}

	public String getBusID() {
		return busID;
	}

	public void setBusID(String busID) {
		this.busID = busID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTripCharge() {
		return tripCharge;
	}

	public void setTripCharge(int tripCharge) {
		this.tripCharge = tripCharge;
	}

	public String getTripDurationSecs() {
		return tripDurationSecs;
	}

	public void setTripDurationSecs(String tripDurationSecs) {
		this.tripDurationSecs = tripDurationSecs;
	}
	
}
