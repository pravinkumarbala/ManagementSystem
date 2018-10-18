import java.util.Scanner;

import com.appointment.Appointment;
import com.doctor.Doctor;
import com.patient.Customer;
import com.slot.Slot;

public class Schedule extends Appointment{
	static Doctor dr = new Doctor() { };
	static Customer cus = new Customer() { };
	static Slot sl = new Slot() { };
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Schedule sch = new Schedule();
		int sel = 0;
		do {
			System.out.println("**************************");
			System.out.println("Schedule Management System");
			System.out.println("**************************");
			System.out.println("1. Doctor Login 2. Customer Login 3.Appointment History 4. Exit");
			System.out.print("Select the option: ");
			sel = sc.nextInt();
			switch(sel) {
				case 1: System.out.println("------------");
						System.out.println("Doctor Login");
						System.out.println("------------");
						System.out.println("1. Doctor Details 2. Pull Recorder 3. Exit");
						System.out.print("Select the option: ");
						int main = 0;
						do {
							main = sc.nextInt();
							switch(main) {
							case 1: int choice = 0;
									do {
										System.out.println("..............");
										System.out.println("CURD OPERATION");
										System.out.println("..............");
										System.out.println("1: Add Doctor 2: Edit Doctor 3: Delete Doctor 4: Exit");
										System.out.print("Select the option : ");
										choice = sc.nextInt();
										switch(choice) {
											case 1: System.out.println("Add the doctor");
													dr.addDoctor();
													System.out.println("Doctor Record is created");
													break;
											case 2: System.out.println("Edit the doctor");
													dr.showDoctors();
													System.out.println();
													dr.editDoctor();
													System.out.println("Doctor Record is edited");
													break;
											case 3: System.out.println("Delete the doctor");
													dr.showDoctors();
													System.out.print("Select the doctor from the list: ");
													dr.deleteDoctor(dr.selectedDoctor(sc.nextInt()));
													System.out.println("Doctor Record is deleted");
													break;
											default: System.out.println("Please select the correct option");
													break;
										}
									}while(choice != 4); 
									break;
							case 2: System.out.println("************");
									System.out.println("Pull Records");
									System.out.println("************");
									int pull = 0;
									do {
										System.out.println("1. Doctors 2. Customers 3. Slots 4. Exit");
										System.out.print("Select the option: ");
										pull = sc.nextInt();
										switch(pull) {
											case 1: dr.showDoctors();
													System.out.print("\nSelect the option: ");
													sch.pullDocRec(dr.selectedDoctor(sc.nextInt()));
													break;
											case 2: System.out.print("Enter the customer name :");
													cus.searchCustomer(sc.next());
													cus.showCustomer();
													System.out.print("Enter the customer Id : ");
													cus.pullCustomer(sc.next());
													break;
											case 3: sl.showSlot();
													System.out.print("Enter the slot: ");
													sl.pullSlot(sc.next());
													break;
											case 4: System.out.println("Exit from the pull record");
													break;
											default:System.out.println("Sorry, wrong option");
													break;
										}
									}while(pull != 4);
									break;
							case 3: System.out.println("Exit from Doctor Menu");
									break;
							default: System.out.println("Sorry, wrong option");
									break;
							}
						}while(main != 3);
						break;
				case 2: System.out.println("--------------");
						System.out.println("Customer Login");
						System.out.println("--------------");
						int docSelect = 0;
						int exit = dr.sizeOfFile();
						do {
							dr.showDoctors();
							System.out.println(exit + ": exit");
							System.out.print("Select the doctor from the list : ");
							docSelect = sc.nextInt();
							if(docSelect < exit) {
								String doctorId = dr.selectedDoctor(docSelect);
								dr.generateSlot(doctorId);
								System.out.print("Enter the slot to be selected: ");
								String selectedSlot = sc.next();
								System.out.println("1. Existing Customer 2. New Customer");
								System.out.print("Select the option: ");
								int custChoice = sc.nextInt();
								if(custChoice == 1) {
									System.out.print("Email Address: ");
									if(sl.bookSlot(sch.bookAppointment(doctorId, cus.existingCustomer(sc.next())), selectedSlot))
										System.out.println("Appointment is booked");
									else System.out.println("Appointment is not booked");
								} else {
									if(sl.bookSlot(sch.bookAppointment(doctorId, cus.addCustomer()), selectedSlot))
										System.out.println("Appointment is booked");
									else System.out.println("Appointment is not booked");
								}
							} else if(docSelect == exit){
								System.out.println("Exit from Customer login");
							} else {
								System.out.println("Sorry, Wrong option");
							}
						}while(docSelect != exit);
						break;
				case 3: System.out.println("-------------------");
						System.out.println("Appointment History");
						System.out.println("-------------------");
						sl.showFullDetails();
						break;
				case 4: System.out.println("Exit from the system");
						break;
				default: System.out.println("Please select the correct option");
						break;
			}
		}while(sel != 4);
		sc.close();
	}
}