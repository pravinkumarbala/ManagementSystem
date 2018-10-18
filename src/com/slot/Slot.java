package com.slot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Slot {
	
	// Files
	static File slotFile = new File("Data/SlotDetails.txt");
	static File doctorFile = new File("Data/DoctorDetails.txt");
	static File appointmentFile = new File("Data/AppointmentDetails.txt");
	static File customerFile = new File("Data/CustomerDetails.txt");
	
	// Collection
	static Set<String> slotList = new HashSet<>();
	static Map<String, String> slotHistory = new HashMap<>();
	
	/*
	 * Book slot
	 */
	public boolean bookSlot(String appointmentID, String slot) {
		boolean status = false;
		try {
			BufferedReader sfr = new BufferedReader(new FileReader(slotFile));
			FileWriter sfw = new FileWriter(slotFile, true);
			String slots = sfr.readLine();
			if(slots == null) {
				sfw.write("ApptID\t"
						+ "SlotTime\n");
			} else {
				sfw.write(appointmentID +
						"\t" + slot);
				status = true;
			}
			sfw.close();
			sfr.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return status;
	}
	
	/*
	 * Slots from File
	 */
	public void slotFromFile() {
		try {
			BufferedReader sfr = new BufferedReader(new FileReader(slotFile));
			String slotLine;
			slotLine = sfr.readLine();
			while(slotLine != null) {
				slotList.add(slotLine);
				slotLine = sfr.readLine();
			}
			sfr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Show Slots
	 */
	public void showSlot() {
		slotFromFile();
		System.out.println("ID --> ApptId\t Slot");
		for(String list: slotList) {
			int i = 1;
			if(list.contains("ApptID")) {
				continue;
			} else {
				String[] slot = list.split("\t");
				System.out.println(i + " --> " + slot[0] + "\t " + slot[1]);
				i++;
			}
		}
	}
	
	/*
	 * Pull Slots
	 */
	public void pullSlot(String aptId) {
		try {
			BufferedReader afr = new BufferedReader(new FileReader(appointmentFile));
			String line = afr.readLine();
			while(line != null) {
				if(line.contains(aptId)) {
					String[] his = line.split("\t");
					  slotHistory.put(his[1], his[2]);
				}
				line = afr.readLine();
			}
			afr.close();
			System.out.println("DoctorName\t Specialist\t CustomerName\t Customeremail");
			for(Map.Entry<String, String> doc: slotHistory.entrySet()) {
				BufferedReader sfr = new BufferedReader(new FileReader(doctorFile));
				String docLine = sfr.readLine();
				while(docLine != null) {
					if(docLine.contains(doc.getKey())) {
						String[] doctor = docLine.split("\t");
						System.out.print(doctor[1] + doctor[2] + "\t" + doctor[3] + "\t");
					}
					docLine = sfr.readLine();
				}
				sfr.close();
			}
			for(Map.Entry<String, String> cus: slotHistory.entrySet()) {
				BufferedReader cfr = new BufferedReader(new FileReader(customerFile));
				String customerLine = cfr.readLine();
				while(customerLine != null) {
					if(customerLine.contains(cus.getValue())) {
						String[] customer = customerLine.split("\t");
						System.out.println(customer[1] + customer[2] + "\t" + customer[3] + "\n");
					}
					customerLine = cfr.readLine();
				}
				cfr.close();	
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Appointment Slot
	 */
	public void showFullDetails() {
		try {
			slotFromFile();
			System.out.println("APPTID\t SLOT\t DOCTORNAME\t SPECIALIST\t CUSTOMERNAME\t CUSTOMEREMAILADDR");
			for(String list: slotList) {
				if(list.contains("ApptID")) {
					continue;
				} else {
					String[] his = list.split("\t");
					System.out.print(his[0] + "\t" + his[1] + "\t");
					BufferedReader afr = new BufferedReader(new FileReader(appointmentFile));
					String aptLine = afr.readLine();
					while(aptLine != null) {
						if(aptLine.contains(his[0])) {
							String[] ids = aptLine.split("\t");
							BufferedReader dfr = new BufferedReader(new FileReader(doctorFile));
							String docLine = dfr.readLine();
							while(docLine != null) {
								if(docLine.contains(ids[1])) {
									String[] doctor = docLine.split("\t");
									System.out.print(doctor[1] + doctor[2] + "\t" + doctor[3] + "\t");
								}
								docLine = dfr.readLine();
							}
							dfr.close();
							BufferedReader cfr = new BufferedReader(new FileReader(customerFile));
							String customerLine = cfr.readLine();
							while(customerLine != null) {
								if(customerLine.contains(ids[2])) {
									String[] customer = customerLine.split("\t");
									System.out.println(customer[1] + customer[2] + "\t" + customer[3]);
								}
								customerLine = cfr.readLine();
							}
							cfr.close();
						}
						aptLine = afr.readLine();
					}
					afr.close();
				}
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}