package com.doctor;

import java.util.Random;

public interface Specialization {

	String[] specialist = {
			"CARDIOLOGISTS", "DERMATOLOGISTS", "GASTROENTEROLOGISTS", 
			"GENERAL_PHYSICIAN", "PATHOLOGISTS", "RADIOLOGISTS"
			};
	
	public default String getDrId() {
		return  "dr" + new Random().nextInt(999);
	}
	
	public default void showSpecialist() {
		int i = 0;
		for(String spl: specialist)
			System.out.print(++i + ": " + spl + "\t");
	}
	
	public default String selectSpecialist(int sel) {
		return specialist[sel - 1];
	}
}