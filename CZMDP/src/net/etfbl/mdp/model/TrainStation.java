package net.etfbl.mdp.model;

public class TrainStation {

	private String name;
	private String expectedTime;
	private String realTime;
	private boolean passed;
	
	
	public TrainStation(String name) {
		super();
		this.name = name;	
		expectedTime = "13:00";
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getExpectedTime() {
		return expectedTime;
	}


	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}


	public String getRealTime() {
		return realTime;
	}


	public void setRealTime(String realTime) {
		this.realTime = realTime;
	}


	public boolean isPassed() {
		return passed;
	}


	public void setPassed(boolean passed) {
		this.passed = passed;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainStation other = (TrainStation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String toString() {
				
		return name + " ("+ expectedTime + " , " + (realTime==null ? "--:--" : realTime ) + ")    •  ";
	}
	
	
	
}
