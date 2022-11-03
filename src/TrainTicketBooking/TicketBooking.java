package TrainTicketBooking;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class TicketBooking {

	static int availableLowerBerths = 2;
	static int availableMiddleBerths = 1;
	static int availableUpperBerths = 1;
	static int availableWaitingList = 1;
	static Queue<Integer> waitingList = new LinkedList<Integer>();
	static List<Integer> bookedTicketList = new ArrayList<Integer>();
	static Map<Integer, Passenger> passengers = new LinkedHashMap<Integer, Passenger>();
	

	List<Integer> lowerBerthsPositions = new ArrayList<Integer>(Arrays.asList(1));
	List<Integer> middleBerthsPositions = new ArrayList<Integer>(Arrays.asList(1));
	List<Integer> upperBerthsPositions = new ArrayList<Integer>(Arrays.asList(1));
	List<Integer> waitingListPositions = new ArrayList<Integer>(Arrays.asList(1));
	
	
	

	public void bookTicket(Passenger p, int berthInfo, String allotedBerth)
	{
		
		
		p.number = berthInfo; 
		p.alloted = allotedBerth;
		passengers.put(p.getPassengerId(), p);
		bookedTicketList.add(p.getPassengerId());

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
			bookedTicketList.remove(Integer.valueOf(passengerId));
			int bookedPosition = p.number;

			System.out.println("---------Cancelled Successfully-----------");

			if (p.alloted.equalsIgnoreCase("L")) {
				TicketBooking.availableLowerBerths++;
				lowerBerthsPositions.add(bookedPosition);
			}
			if (p.alloted.equalsIgnoreCase("M")) {
				TicketBooking.availableMiddleBerths++;
				middleBerthsPositions.add(bookedPosition);
			}
			if (p.alloted.equalsIgnoreCase("U")) {
				TicketBooking.availableUpperBerths++;
				upperBerthsPositions.add(bookedPosition);
			}
			if (waitingList.size() > 0) {
				Passenger passengerFromWaitingList = passengers.get(waitingList.poll());
				int position = passengerFromWaitingList.number;
//waitingListPositions.add(position);
				waitingListPositions.remove(Integer.valueOf(passengerFromWaitingList.getPassengerId()));
				availableWaitingList++;
				BookTicket bt = new BookTicket();
				bt.BookTicket(passengerFromWaitingList);

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
			System.out.println("Status -- " + p.alloted);
			System.out.println("------------------------------------");
			
			
		}
		
		

	}

	public void serverWrite(Passenger p)
	{
		try {
            Properties pf = new Properties();
            FileInputStream fs=new FileInputStream("C:\\Users\\User\\eclipse-workspace\\my folder\\My Programs\\src\\TrainTicketBooking\\PassengerDetailList.properties");
            pf.load(fs);
           
                           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                           Connection con = DriverManager.getConnection(
                                   pf.getProperty("Dburl"),pf.getProperty("Dbname") ,pf.getProperty("Dbpassword") );

       
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
	
	public void serverWrite()
	{

		try {
	     Properties p = new Properties();
         FileInputStream fs=new FileInputStream("C:\\Users\\User\\eclipse-workspace\\my folder\\My Programs\\src\\TrainTicketBooking\\PassengerDetailList.properties");
         p.load(fs);
        Connection con = DriverManager.getConnection(
                p.getProperty("Dburl"),p.getProperty("Dbname") ,p.getProperty("Dbpassword") );
        java.sql.Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select *    from RANJITHKUMAR_TRAINTICKET");
        System.out.println(".......Booked Passenger Details......");
        while (rs.next()) {
            int PassengerId = rs.getInt("PASSENGER_ID");
            String Name = rs.getString("NAME");
            int Age = rs.getInt("AGE");
            String berthPerference = rs.getString("BERTHPERPREFERENCE");
            System.out.println("--------------------------------------------\n");
            System.out.println(
                    " PassengerId -- " + PassengerId + " \n " + "Passenger Name is -- " + Name + " \n " + "Patient Age Is -- " + Age+"\n"+" berthPerference is -- "+berthPerference);
            System.out.println("--------------------------------------------\n");
        }}
		catch(Exception e)
		{
			System.err.println(e);
		}

    
	}
}
