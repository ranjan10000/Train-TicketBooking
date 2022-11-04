package TrainTicketBooking;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class TicketBooking {

	Queue<Integer> waitingList = new PriorityQueue<Integer>();
	
	Map<Integer, Passenger> passengers = new LinkedHashMap<Integer, Passenger>();

	static TicketBooking ticketbooking = new TicketBooking();

	List<Integer> lowerBerthsPositions = new ArrayList<Integer>(Arrays.asList(1,2));
	List<Integer> middleBerthsPositions = new ArrayList<Integer>(Arrays.asList(1));
	List<Integer> upperBerthsPositions = new ArrayList<Integer>(Arrays.asList(1));
	List<Integer> waitingListPositions = new ArrayList<Integer>(Arrays.asList(1));

	int availableLowerBerths = 2;
	int availableMiddleBerths = 1;
	int availableUpperBerths = 1;
	int availableWaitingList = 1;

	public static void BookTicket(Passenger p) {

		if (ticketbooking.availableLowerBerths == 0) {
			System.out.println("No Ticket Available");
		}
		if ((p.getBerthPerference().equalsIgnoreCase("L") && ticketbooking.availableLowerBerths > 0)
				|| (p.getBerthPerference().equalsIgnoreCase("M") && ticketbooking.availableMiddleBerths > 0)
				|| (p.getBerthPerference().equalsIgnoreCase("U") && ticketbooking.availableUpperBerths > 0)) {
			System.err.println("Welcome");
			System.out.println("Preferred Berth Available");
			{

				if ((p.getBerthPerference().equalsIgnoreCase("L"))) {
					System.out.println("Lower Berth Given");
					ticketbooking.bookTicket(p, ticketbooking.lowerBerthsPositions.get(0), "L");
					ticketbooking.lowerBerthsPositions.remove(0);
					ticketbooking.availableLowerBerths--;

				} else if (p.getBerthPerference().equalsIgnoreCase("M")) {

					System.out.println("Middle Berth Given");
					ticketbooking.bookTicket(p, ticketbooking.middleBerthsPositions.get(0), "M");
					ticketbooking.middleBerthsPositions.remove(0);
					ticketbooking.availableMiddleBerths--;

				} else if (p.getBerthPerference().equalsIgnoreCase("U")) {
					System.out.println("Upper Berth Given");
					ticketbooking.bookTicket(p, ticketbooking.upperBerthsPositions.get(0), "U");
					ticketbooking.upperBerthsPositions.remove(0);
					ticketbooking.availableUpperBerths--;

				}
			}

		} else if (ticketbooking.availableLowerBerths > 0) {
			System.err.println("Sorry Seat Not Available ");
			System.out.println("Lower Berth Given");
			ticketbooking.bookTicket(p, ticketbooking.lowerBerthsPositions.get(0), "L");
			ticketbooking.lowerBerthsPositions.remove(0);
			ticketbooking.availableLowerBerths--;

		} else if (ticketbooking.availableMiddleBerths > 0) {
			System.err.println("Sorry Seat Not Available ");
			System.out.println("Middle Berth Given");
			ticketbooking.bookTicket(p, ticketbooking.middleBerthsPositions.get(0), "M");
			ticketbooking.middleBerthsPositions.remove(0);
			ticketbooking.availableMiddleBerths--;

		} else if (ticketbooking.availableUpperBerths > 0) {
			System.err.println("Sorry Seat Not Available ");
			System.out.println("Upper Berth Given");
			ticketbooking.bookTicket(p, ticketbooking.upperBerthsPositions.get(0), "U");
			ticketbooking.upperBerthsPositions.remove(0);
			ticketbooking.availableUpperBerths--;
		} else if (ticketbooking.availableWaitingList > 0) {
			System.err.println("Sorry Seat Not Available ");
			System.out.println("Added to WaitingList");
			ticketbooking.addWaitingList(p, ticketbooking.waitingListPositions.get(0), "WL");
			ticketbooking.waitingListPositions.remove(0);
			ticketbooking.availableWaitingList--;

		} else {
			System.out.println("invaild BerthPerference");
		}

	}

	public void bookTicket(Passenger p, int berthInfo, String allotedBerth) {

		p.number = berthInfo;
		p.alloted = allotedBerth;
		passengers.put(p.getPassengerId(), p);
	
	    System.out.println("------Booked Successfully------");

	}

	public void addWaitingList(Passenger p, int waitingListInfo, String allotedWL) {
		p.number = waitingListInfo;
		p.alloted = allotedWL;

		passengers.put(p.getPassengerId(), p);

		waitingList.add(p.getPassengerId());

		availableWaitingList--;

		waitingListPositions.remove(0);

		System.out.println("-------Added to WaitingList Successfully-------");

	}

	public void cancelTicket(int passengerId) {
		try {
			Passenger p = passengers.get(passengerId);
			passengers.remove(Integer.valueOf(passengerId));
		
			int bookedPosition = p.number;

			System.out.println("---------Cancelled Successfully-----------");

			if (p.alloted.equalsIgnoreCase("L")) {
				ticketbooking.availableLowerBerths++;
				lowerBerthsPositions.add(bookedPosition);
			}
			if (p.alloted.equalsIgnoreCase("M")) {
				ticketbooking.availableMiddleBerths++;
				middleBerthsPositions.add(bookedPosition);
			}
			if (p.alloted.equalsIgnoreCase("U")) {
				ticketbooking.availableUpperBerths++;
				upperBerthsPositions.add(bookedPosition);
			}
			if (waitingList.size() > 0) {
				Passenger passengerFromWaitingList = passengers.get(waitingList.poll());
				int position = passengerFromWaitingList.number;
                waitingListPositions.add(position);
				waitingListPositions.remove(Integer.valueOf(passengerFromWaitingList.getPassengerId()));
				availableWaitingList++;

				ticketbooking.BookTicket(passengerFromWaitingList);

			}
		} catch (Exception e) {
			System.out.println("invaild passenger ID");
		}
	}

	public void printAvailable() {

		System.out.println("Available Lower Berths - " + availableLowerBerths);

		System.out.println("Available Middle Berths - " + availableMiddleBerths);
		System.out.println("Available Upper Berths - " + availableUpperBerths);
		System.out.println("Available Waiting List - " + availableWaitingList);
		System.out.println("----------------------------------------------");

	}

	public void printPassenger() {
		if (passengers.size() == 0) {
			System.out.println("No details of passenger");

		}

		for (Passenger p : passengers.values()) {

			System.out.println("Passenger ID -- " + p.getPassengerId());
			System.out.println("Name -- " + p.getName());
			System.out.println("Age -- " + p.getAge());
			System.out.println("Status -- " +p.number+ p.alloted);
			System.out.println("------------------------------------");

		}

	}

	public void serverWrite(Passenger p) {
		try {
			Properties pf = new Properties();
			FileInputStream fs = new FileInputStream(
					"C:\\Users\\User\\eclipse-workspace\\my folder\\My Programs\\src\\TrainTicketBooking\\PassengerDetailList.properties");
			pf.load(fs);

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(pf.getProperty("Dburl"), pf.getProperty("Dbname"),
					pf.getProperty("Dbpassword"));

			PreparedStatement stmt = con.prepareStatement("insert into RANJITHKUMAR_TRAINTICKET values(?,?,?)");

			stmt.setString(1, p.getName());
			stmt.setInt(2, p.getAge());
			stmt.setString(3, p.getBerthPerference());

			int s = stmt.executeUpdate();
			System.out.println("Stored successfully");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void serverWrite() {

		try {
			Properties p = new Properties();
			FileInputStream fs = new FileInputStream(
					"C:\\Users\\User\\eclipse-workspace\\my folder\\My Programs\\src\\TrainTicketBooking\\PassengerDetailList.properties");
			p.load(fs);
			Connection con = DriverManager.getConnection(p.getProperty("Dburl"), p.getProperty("Dbname"),
					p.getProperty("Dbpassword"));
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select *    from RANJITHKUMAR_TRAINTICKET");
			System.out.println(".......Booked Passenger Details......");
			while (rs.next()) {
				int PassengerId = rs.getInt("PASSENGER_ID");
				String Name = rs.getString("NAME");
				int Age = rs.getInt("AGE");
				String berthPerference = rs.getString("BERTHPERPREFERENCE");
				System.out.println("--------------------------------------------\n");
				System.out.println(" PassengerId -- " + PassengerId + " \n " + "Passenger Name is -- " + Name + " \n "
						+ "Patient Age Is -- " + Age + "\n" + " berthPerference is -- " + berthPerference);
				System.out.println("--------------------------------------------\n");
			}
		} catch (Exception e) {
			System.err.println(e);
		}

	}
}
