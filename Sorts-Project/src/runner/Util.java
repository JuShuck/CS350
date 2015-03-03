package runner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util
{
	public static final String DATE_FORMAT = "MM-dd-YYYY HH.mm.ss";
	
	public static String format(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		
		return formatter.format(date);
	}
	
	public static String format(Calendar cal)
	{
		return format(cal.getTime());
	}
}
