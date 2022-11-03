package TrainTicketBooking;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Passenger {

	static int id = 1;
	private String name;
	private int age;
	private String berthPerference;
	private int passengerId;
	String alloted = "";
	int number = 0;
	private int passengerd;

	public void passenger(String name, int age, String berthPerference) {
		this.name = name;
		this.age = age;
		this.berthPerference = berthPerference;
		this.passengerId = id++;

	}

	public String getName() {

		return name;

	}

	public int getAge() {
		return age;
	}

	public String getBerthPerference() {
		return berthPerference;
	}

	public int getPassengerId() {
		return passengerId;
	}

	public int getPassengerd() {
		return passengerd;
	}

}
