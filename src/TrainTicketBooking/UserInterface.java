package TrainTicketBooking;

import java.util.Scanner;

public class UserInterface {
	
	public static void start() {

		boolean loop = true;
		Scanner s = new Scanner(System.in);
		Scanner s1 = new Scanner(System.in);

		TicketBooking booker = new TicketBooking();
		try {
			while (loop) {

				System.out.println(
						" 1.Book Ticket \n 2.Cancel Ticket \n 3.Available Ticket \n 4.Booked Tickets \n 5.ServerDetails \n 6.Exit ");
				int choice = s.nextInt();

				switch (choice) {
				// Book Ticket
				case 1: {
					System.out.println("Enter Name,Age,BerthPerference(L,M,U)");

					String name = s.next().trim();
					char[] charArray = name.toCharArray();

					for (int i = 0; i < charArray.length; i++) {
						if (Character.isAlphabetic(charArray[i]) == false) {
							System.out.println("invalid user");
							break;
						} else {
							int age = s1.nextInt();
							String berthPerference = s.next().trim();

							if (age > 5 && age < 99) {

								Passenger p = new Passenger();
								p.passenger(name, age, berthPerference);
								BookTicket bt = new BookTicket();
								TicketBooking tb = new TicketBooking();
								bt.BookTicket(p);
								tb.serverWrite(p);

							} else {

								System.out.println("invaild Age");
								System.out.println("Enter vaild Age");
								System.out.println();
							}
							break;
						}
					}

				}
					break;
				// Cancel Ticket
				case 2: {
					System.out.println("Enter Passenger Id to Cancel");
					int id = s1.nextInt();
					booker.cancelTicket(id);
				}
					break;
				// Available Ticket
				case 3: {

					booker.printAvailable();
				}
					break;
				// Booked Tickets
				case 4: {
					booker.printPassenger();
				}
					break;
				// Exit
				case 5: {

					TicketBooking tb = new TicketBooking();
					tb.serverWrite();

				}
					break;

				case 6: {
					loop = false;
				}
					break;

				}
			}
		} catch (Exception e) {
			System.out.println("invaild Data");
			start();
		}

	}
}