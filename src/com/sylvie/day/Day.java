package com.sylvie.day;

import java.util.Calendar;

public class Day {
		
	Calendar cal = Calendar.getInstance();
	 
    public int getDay() {	
    	return cal.get(Calendar.DAY_OF_WEEK);
    }


}
