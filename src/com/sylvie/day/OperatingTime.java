package com.sylvie.day;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OperatingTime {
	Calendar cal = Calendar.getInstance();
	Date dt = new Date();
	
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
    public String checkOperatingTime(Timestamp open, Timestamp close) throws ParseException {
    	
    	Date openHr = new Date(open.getTime());
    	String ophr = sdf.format(openHr);
    	Date op = sdf.parse(ophr); // 1970
    	
    	Date closeHr = new Date(close.getTime());
    	String cshr = sdf.format(closeHr);
    	Date cs = sdf.parse(cshr);
  
    	String now = sdf.format(cal.getTime());
    	Date nw = sdf.parse(now);
    	
    	if(nw.compareTo(op)>=0&&nw.compareTo(cs)<=0)
    		return "營業中";
    	else
    		return "休息中";

    }
}
