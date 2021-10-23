package UI;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import Controller.StaffController;
import Controller.MenuController;
import Controller.OrderController;
import Controller.ReservationController;
import Controller.TableController;
import Database.ReservationDB;
import Entity.Reservation;
import Entity.Staff;

public class RRPSSApp {

	public static void main(String[] args) throws IOException, ParseException {

		Date d = new Date();
		System.out.println(d);
		Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();
        timer.schedule(new checkExpired(), time);
		MenuController.retrieveInstance().loadinDB();
		OrderController.retrieveInstance().loadinDB();

		int main_menu_choice;
		Scanner sc = new Scanner(System.in);
		do {

			System.out.println("==================================================");
			System.out.println(" RESTAURANT RESERVATION AND POINT OF SALE SYSTEM APPLICATION ");
			System.out.println("==================================================");
			
			System.out.println("==================================================");
			System.out.println(" Please select one of the following functions: ");
			System.out.println("==================================================");
			System.out.println("(1) Reservation");
			System.out.println("(2) Table");
			System.out.println("(3) Menu");
			System.out.println("(4) Order"); 
			System.out.println("(5) Promotion Deals"); 
			System.out.println("(6) Generate Sales Report");
			System.out.println("(7) Staff");
			System.out.println("(8) Save and exit");

			System.out.println("\nEnter your choice:");
			main_menu_choice = sc.nextInt();
			sc.nextLine();

		switch (main_menu_choice) {
			case 1:
				int reservation_choice;
				do {
					System.out.println("\n==================================================");
					System.out.println(" Restaurant");
					System.out.println("==================================================");
					System.out.println("(1) Create Reservations");
					System.out.println("(2) Check Reservations");
					System.out.println("(3) Remove Reservations");
					System.out.println("(4) Update Reservations"); // not in functional requirements but may need? 
					System.out.println("(5) Back");
					reservation_choice = sc.nextInt();
					switch (reservation_choice) {
					
					case 1:
						ReservationController.createReservation(); // create Reservation
						break;
					case 2:
						//check Reservation
						break;
					case 3:
						//remove reservation
						break;
					case 4:
						//  update reservation
						break;
					case 5:
						reservation_choice = 5;
						break;
					default:
						System.out.println("Please enter a valid input");
						reservation_choice = 0;
					}
				} while(reservation_choice <5);
				break;
				
			case 2:
				int table_choice;
				do {
					System.out.println("\n==================================================");
					System.out.println(" Table: ");
					System.out.println("==================================================");
					System.out.println("(1) Update Table");
					System.out.println("(2) Check All Table Availability");
					System.out.println("(3) Back");
					table_choice = sc.nextInt();
					switch (table_choice) {
					case 1:
						// Retrieve table and update by table id
						TableController.updateTable();
						break;
					case 2:
						// Retrieve and print all Table details
						TableController.retrieveAllTable();
						break;
					case 3:
						table_choice = 3;
						break;
					default:
						System.out.println("Please enter a valid input");
						table_choice = 0;
					}
				}while(table_choice<3);
				break;
			
			case 3:
				UI.MenuUI.getInstance().displayOptions();
				break;
				
			case 4:
				//ORDER STUFF HERE
				break;
				
			case 5 :
				//PROMOTION STUFF HERE
				break;
				
			case 6:
				// GENERATE SALES REVENUE REPORT HERE
				break;
				
			case 7:
				int staff_management_choice;
				StaffController staffManager = new StaffController();
				staffManager.loadFromDB();
				do{	
					System.out.println("\n=================================================="
										+"\n Staff Management" 
										+"\n=================================================="
										+"\n (1) Display All Employees"
										+"\n (2) Add a new Hire"
										+"\n (3) Remove an employee"
										+"\n (4) Check employee's information"
										+"\n (5) Update employee's job"
										);
					staff_management_choice = Integer.valueOf(sc.nextLine());
					switch (staff_management_choice) {
					

					case 1:
						staffManager.displayStaff();
						break;
						
					case 2:
						System.out.println("Enter employee's name:");
						String employeeName = sc.nextLine();
						System.out.println("Enter employee's gender:");
						String employeeGender = sc.nextLine();
						System.out.println("Enter employee's Job:");
						String employeeJob = sc.nextLine();
						staffManager.addStaff(employeeName, employeeGender, employeeJob);
						break;
						
					case 3:
						System.out.println("Enter the ID of the employee to be removed:");
						String employeeId = sc.nextLine();
						staffManager.removeStaff(employeeId);
						break;
						
					case 4:
						System.out.println("Enter the ID of the employee to be checked:");
						String employeeId = sc.nextLine();
						Staff employee = staffManager.getStaff(employeeIdk);
						break;
					
					case 5:
						System.out.println("Enter the ID of the employee to be updated:");
						String employeeId = sc.nextLine();
						System.out.println("Enter the new job the employee will be holding:");
						String employeeJob = sc.nextLine();
						Staff employee = staffManager.getStaff(employeeId);
						employee.setJobTitle(employeeJob);
						break;
					default:
						System.out.println("Please enter a valid option.");
					}
				}while(staff_management_choice < 6);
				break;
				
			case 8:
				System.out.println("Saving all data... Program terminating ..."); // Save "database into file"
				main_menu_choice = 9;
				break;
				
			default:
				System.out.println("Please enter a valid option.");
				main_menu_choice = 0;
			
		} while (main_menu_choice < 9);
		MenuController.retrieveInstance().savetoDB();
		OrderController.getInstance().savetoDB();
		sc.close();
	}
	
	// this is to remove reservation after a certain period of time has passed
	static class checkExpired extends TimerTask {
        public void run() {

        	String fileName = "Reservation.txt";
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    		Date today = new Date();
    		Date cid;
    		ArrayList<Reservation> reservationList = ReservationController.retrieveReservation();
    		for (int i = 0; i < reservationList.size(); i++) {
    			cid = reservationList.get(i).getDate();
    			if (cid.before(today) && reservationList.get(i).getStatus().equalsIgnoreCase("CONFIRMED")) {
    				reservationList.get(i).setStatus("EXPIRED");
    				// change table status to vacant
    				String tableId = reservationList.get(i).gettableId();
    				TableController.updateTableStatus(tableId, "VACANT");
    			}
    		}
    		// Write Reservation records to file
    		ReservationDB reservationDB = new ReservationDB();
    		try {
    			reservationDB.save(fileName, reservationList);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			System.out.println("IOException > " + e.getMessage());
    		}
        }
	}
}