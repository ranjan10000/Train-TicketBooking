package TrainTicketBooking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookTicket  {

	public static void BookTicket(Passenger p) {
		
		TicketBooking booker = new TicketBooking();

		if (TicketBooking.availableLowerBerths == 0) {
			System.out.println("No Ticket Available");
		}
		if ((p.getBerthPerference().equalsIgnoreCase("L") && TicketBooking.availableLowerBerths > 0)
				|| (p.getBerthPerference().equalsIgnoreCase("M") && TicketBooking.availableMiddleBerths > 0)
				|| (p.getBerthPerference().equalsIgnoreCase("U") && TicketBooking.availableUpperBerths > 0)) {
			System.err.println("Welcome");
			System.out.println("Preferred Berth Available");
			{

				if ((p.getBerthPerference().equalsIgnoreCase("L"))) {
					System.out.println("Lower Berth Given");
					booker.bookTicket(p, booker.lowerBerthsPositions.get(0), "L");
					booker.lowerBerthsPositions.remove(0);
					booker.availableLowerBerths--;

				} else if (p.getBerthPerference().equalsIgnoreCase("M")) {

					System.out.println("Middle Berth Given");
					booker.bookTicket(p, booker.middleBerthsPositions.get(0), "M");
					booker.middleBerthsPositions.remove(0);
					booker.availableMiddleBerths--;

				} else if (p.getBerthPerference().equalsIgnoreCase("U")) {
					System.out.println("Upper Berth Given");
					booker.bookTicket(p, booker.upperBerthsPositions.get(0), "U");
					booker.upperBerthsPositions.remove(0);
					booker.availableUpperBerths--;

				}
			}

		} else if (booker.availableLowerBerths > 0 ) {
			System.err.println("Sorry Seat Not Available ");
			System.out.println("Lower Berth Given");
			booker.bookTicket(p, booker.lowerBerthsPositions.get(0), "L");
			booker.lowerBerthsPositions.remove(0);
			booker.availableLowerBerths--;

		} else if (booker.availableMiddleBerths > 0 ) {
			System.err.println("Sorry Seat Not Available ");
			System.out.println("Middle Berth Given");
			booker.bookTicket(p, booker.middleBerthsPositions.get(0), "M");
			booker.middleBerthsPositions.remove(0);
			booker.availableMiddleBerths--;

		} else if (booker.availableUpperBerths > 0 ) {
			System.err.println("Sorry Seat Not Available ");
			System.out.println("Upper Berth Given");
			booker.bookTicket(p, booker.upperBerthsPositions.get(0), "U");
			booker.upperBerthsPositions.remove(0);
			booker.availableUpperBerths--;
		} else if (booker.availableWaitingList > 0) {
			System.err.println("Sorry Seat Not Available ");
			System.out.println("Added to WaitingList");
			booker.addWaitingList(p, booker.waitingListPositions.get(0), "WL");
//booker.waitingListPositions.remove(0);
//booker.availableWaitingList--;

		} else {
			System.out.println("invaild BerthPerference");
		}

	}

}
