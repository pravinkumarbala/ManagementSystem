package com.doctor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public abstract class Doctor extends DoctorModel implements Specialization {
	
	private static int choice = 0;
	
	static Scanner scan = new Scanner(System.in);
	
	static File doctorFile = new File("Data/DoctorDetails.txt");
	static File appointmentFile = new File("Data/AppointmentDetails.txt");
	static File slotFile = new File("Data/SlotDetails.txt");
	
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	static List<String> listOfDoctors = new ArrayList<>();
	static List<String> doctorsAppointment = new LinkedList<>();
	static Set<String> bookedSlots = new HashSet<>();
		
	/*
	 * Add Doctor to File
	 */
	public String addDoctor() {
		try {
			String dId = getDrId(); 
			System.out.print("First Name : ");
			setFirstName(scan.next());
			System.out.print("Last Name : ");
			setLastName(scan.next());
			showSpecialist();
			System.out.print("\nAssign the specialization for the doctor : ");
			setSpecialization(selectSpecialist(scan.nextInt()));
			System.out.print("Working Hours\n");
			System.out.print("-------------\n");
			System.out.print("From : ");
			setWorkingHourStart(scan.next());
			System.out.print("To : ");
			setWorkingHourEnd(scan.next());
			System.out.print("Consulting Duration : ");
			setConsultTime(scan.nextInt());
			
			BufferedReader dfr = new BufferedReader(new FileReader(doctorFile));
			FileWriter dfw = new FileWriter(doctorFile, true);
			if(dfr.readLine() == null)
				dfw.write("DocId\t"
						+ "FName\t"
						+ "LName\t"
						+ "Special\t"
						+ "SWH\t"
						+ "EWH\t"
						+ "CT\n");
			else
				dfw.write(dId +
					"\t" + getFirstName() +
					"\t" + getLastName() +
					"\t" + getSpecialization() +
					"\t" + sdf.format(getWorkingHourStart()) + 
					"\t" + sdf.format(getWorkingHourEnd()) +
					"\t" + getConsultTime() +
					"\n");
			dfw.close();
			dfr.close();
			return dId;
		} catch(IOException | ParseException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/*
	 * Retrieve Data from File
	 */
	public void fetchDoctorFile() {
		try {
			listOfDoctors.clear();
			BufferedReader dfr = new BufferedReader(new FileReader(doctorFile));
			String line = dfr.readLine();
			while(line != null) {
				listOfDoctors.add(line);
				line = dfr.readLine();
			}
			dfr.close();
		} catch ( IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Edit Doctor on the file
	 */
	public void editDoctor() {
		System.out.print("Enter the name of the doctor: ");
		String search = scan.next();
		String changeWord = null;
		String retDetail = fetchCollection(search); 
		String[] result = retDetail.split("\t");
		do {
			System.out.println("1. First Name 2. Last Name 3. Specialization 4. Working hours 5. Consulting Time 6. Exit");
			System.out.print("Select the option to be edited: ");
			choice = scan.nextInt();
			if(choice == 1) {
				
				System.out.print("Enter the first name : ");
				String firstName = scan.next();
				changeWord = retDetail.replace(result[1], firstName);
				rewriteDoctor(changeWord, result[0]);
				
			} else if(choice == 2) {
				
				System.out.print("Enter the Last name : ");
				String lastName = scan.next();
				changeWord = retDetail.replace(result[2], lastName);
				rewriteDoctor(changeWord, result[0]);
				
			} else if(choice == 3) {
				
				showSpecialist();
				System.out.print("\nSelect the Specialization : ");
				String special = selectSpecialist(scan.nextInt());
				changeWord = retDetail.replace(result[3], special);
				rewriteDoctor(changeWord, result[0]);
				
			} else if(choice == 4) {
				
				System.out.print("Working hours\n");
				System.out.print("*************\n");
				System.out.print("Enter the start hour[HH:MM] : ");
				String startHour = scan.next();
				changeWord = retDetail.replace(result[4], startHour);
				System.out.print("Enter the end hour[HH:MM] : ");
				String endHour = scan.next();
				changeWord = changeWord.replace(result[5], endHour);
				rewriteDoctor(changeWord, result[0]);
				
			} else if(choice == 5) {
				
				System.out.print("Enter the consulting time : ");
				String consTime = scan.next();
				changeWord = retDetail.replace(result[6], consTime);
				rewriteDoctor(changeWord, result[0]);
				
			} else if(choice == 6) {
				
				System.out.println("Exiting from doctor edit");
				
			} else {
				System.out.println("Sorry, Wrong option");
			}
		}while(choice != 6);
	}
	
	/*
	 * Fetch Doctor from the Collection
	 */
	public String fetchCollection(String name) {
		fetchDoctorFile();
		String singleDoctor = "";
		for(int i = 1; i < listOfDoctors.size(); i++) {
			if(listOfDoctors.get(i).contains(name))
				singleDoctor = listOfDoctors.get(i);
		}
		return singleDoctor;
	}
	
	/*
	 * Write the edited data to the file
	 */
	public boolean rewriteDoctor(String info, String doctorId) {
		boolean deleteStatus = deleteDoctor(doctorId);
		try {
			if(deleteStatus) {
				FileWriter dfw = new FileWriter(doctorFile, true);
				dfw.write(info);
				System.out.println("Record is Edited");
				dfw.close();
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return deleteStatus;
	}
	
	/*
	 * Delete doctor from list
	 */
	public boolean deleteDoctor(String doctorId) {
		String con = "";
		boolean status = false;
		try {
			BufferedReader dfr = new BufferedReader(new FileReader(doctorFile));
			String line = dfr.readLine();
			while(line != null) {
				if(line.contains(doctorId)) {
					status = true;
				} else {
					con = con.concat(line) + "\n";
				}
				line = dfr.readLine();
			}
			FileWriter dfw = new FileWriter(doctorFile);
			dfw.write(con);
			dfw.close();
			dfr.close();
		} catch ( IOException | NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		return status;
	}
	
	/*
	 * Displaying staff list
	 */
	public void showDoctors() {
		fetchDoctorFile();
		for(int i = 1; i < listOfDoctors.size(); i++) {
			String[] list = listOfDoctors.get(i).split("\t");
			System.out.print(i + ": " + list[1] + " " + list[2] + "  ");
		}	
	}
	
	/*
	 * Select staff from list
	 */
	public String selectedDoctor(int num) {
		String[] doctor = listOfDoctors.get(num).split("\t");
		return doctor[0];
	}
	
	/*
	 * Size of array list
	 */
	public int sizeOfFile() {
		fetchDoctorFile();
		return listOfDoctors.size();
	}
	
	public void generateSlot(String doctorId) {
		fetchDoctorFile();
		for(int i = 1; i < listOfDoctors.size(); i++) {
			if(listOfDoctors.get(i).contains(doctorId)) {
				try {
					String[] list = listOfDoctors.get(i).split("\t");
					
					Date startHour = sdf.parse(list[4]);
					Date endHour = sdf.parse(list[5]);
					int conTime = Integer.parseInt(list[6]);
					
					Calendar start = Calendar.getInstance();
					start.setTime(startHour);
					
					Calendar end = Calendar.getInstance();
					end.setTime(endHour);
					
					BufferedReader afr = new BufferedReader(new FileReader(appointmentFile));
					String appt = afr.readLine();
					while(appt != null) {
						if(appt.contains(doctorId)) {
							String[] apt = appt.split("\t");
							doctorsAppointment.add(apt[0]);
						}
						appt = afr.readLine();
					}
					afr.close();
					
					BufferedReader sfr = new BufferedReader(new FileReader(slotFile));
					String slots = sfr.readLine();
					while(slots != null) {
						String[] slot = slots.split("\t");
						if(doctorsAppointment.contains(slot[0]))
							bookedSlots.add(slot[1]);
						slots = sfr.readLine();
					}
					sfr.close();
					
					String startWH = sdf.format(start.getTime());
					while(startWH.compareTo(sdf.format(end.getTime())) < 0 ) {
						if(bookedSlots.contains(startWH))
							System.out.println("Slot is booked");
						else System.out.println(startWH);
						start.add(Calendar.MINUTE, conTime);
						startWH = sdf.format(start.getTime());
					}
				} catch (ParseException | IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
	}
}