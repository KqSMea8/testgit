package com.hanweb.jmp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hanweb.common.util.DateUtil;
import com.hanweb.jmp.constant.StaticValues;

public class TimeUtil { 
	/**
	 * keys
	 */
	private static final String[] KEYS = { "y", "M", "d", "H", "m", "s" };
	
	
	/**
	 * logger
	 */
	private static final Log LOGGER = LogFactory.getLog(TimeUtil.class);
	/**
     * 
     * convertTime:时间转换类.
     *
     * @param time 时间字符串.
     * @return    设定参数 .
     */
    public static String convertTime(String time){
        try{
            if(time==null || "".equals(time)){
                return null;
            }
            time=formatDate(time.replaceAll("　", "").trim());
            return time;
        }catch(Exception e){
        	LOGGER.error("convertTime时间转换异常：", e);
            return DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
        }
    }
	/**
	 * formatDate:(这里用一句话描述这个方法的作用).
	 *
	 * @param time logger
	 * @return    设定参数 .
	*/
	public static String formatDate(String time) {
		if(StaticValues.getFormats()==null || StaticValues.getFormats().length==0){
			return time;
		}
		String rex = "";
		Date date = null;
		SimpleDateFormat sdft = null;
		for (String format : StaticValues.getFormats()) {
			rex = format;
			for(String key : KEYS){
				rex = rex.replaceAll(key, "[0-9]");
			} 
			try {
				if (Pattern.compile(rex).matcher(time).matches()) { 
					sdft = new SimpleDateFormat(format);
					date = sdft.parse(time);
					return new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(date);
				} else {
					return time;
				}
			} catch (Exception e) {
				LOGGER.error("formatDate异常：", e); 
			}
	
		}
		return new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss").format(new Date());
	} 


    /**
     * 位数不足补0
     * @param num       需要补0的字符
     * @param formart   位数
     * @return 补足 后的结果
     */
    public static String getNumber(String num, int formart){
        String result = ""; 
        int numlength = String.valueOf(num).length(); 
        
        if(numlength >= formart){
            return num; 
        }else{
            result = "0"+num; 
            num = getNumber(result, formart); 
        }
        return num; 
    }
    
    /**
	 * befoDay:(获取指定天数前的时间日期).
	 *
	 * @param day 天数
	 * @return    设定参数 .
	*/
	public static String befoDay(int day){
		Calendar cal = Calendar.getInstance();
	    cal.setTime(new Date());
	    cal.add(6, day*(-1));
	    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	    formater.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
	    return formater.format(cal.getTime()); 
	}   
	
	/**
     * befoDay:获取指定分钟之前的时间.
     *
     * @param minute 分钟
     * @return    设定参数 .
    */
    public static String befoMinute(int minute){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, minute*(-1));
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formater.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formater.format(cal.getTime());
    }
	 
    /**
     * timeConvert:(这里用一句话描述这个方法的作用).
     *
     * @param time time
     * @return    设定参数 .
    */
    public String timeConvert(String time) {

		//没有空格的时候处理方法
    	if(time.indexOf(" ") == -1) {
    		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    		return yearMonthDay(time) + now.substring(now.indexOf(" "));
    	} else {
    		String[] times = time.split(" ");
    		//年月日
    		String yMD = times[0];
    		//时分秒
    		String hMS = times[1];
    		return yearMonthDay(yMD) + " " + hourMinSS(hMS);
    	}
    }
    
   
    /**
     * yearMonthDay:(这里用一句话描述这个方法的作用).
     *
     * @param time time
     * @return    设定参数 .
    */
    public String yearMonthDay(String time) {
    	//当只有年份的时候
    	if(time.length()==4) {
    		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    		return time+now.substring(now.indexOf("-"), now.lastIndexOf(" "));
    	}
		if(time.indexOf("-") != -1) {
			return timeChange(time, "-");
		} else if(time.indexOf("/") != -1) {
			return timeChange(time, "/");
		} else if(time.indexOf(":") != -1) {
			return timeChange(time, ":");
		} else if(time.indexOf(",") != -1) {
			return timeChange(time, ",");
		} else if(time.indexOf(".") != -1) {
			return timeChange(time, "\\.");
		} else if(time.indexOf("年") != -1) {
			return yearMonthDayByC(time);
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
     
    /**
     * timeChange:(这里用一句话描述这个方法的作用).
     *
     * @param time time
     * @param flag flag
     * @return    设定参数 .
    */
    public String timeChange(String time, String flag) {
    	String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	String[] times = time.split(flag);
		int len = times.length;
		if(len==3) {
			return time.replaceAll(flag, "-") ;
		} else if(len==2) {
			if(time.endsWith(flag)) {
				return time.replaceAll(flag, "-") 
					+ now.substring(now.lastIndexOf("-")+1, now.indexOf(" "));
			} else {
				return time.replaceAll(flag, "-") 
					+ now.substring(now.lastIndexOf("-"), now.indexOf(" "));
			}
		} else {
			return time.replaceAll(flag, "-") + now.substring(now.indexOf("-")+1, now.indexOf(" "));
		}
    }
    
    /**
     * yearMonthDayByC:(这里用一句话描述这个方法的作用).
     *
     * @param time time
     * @return    设定参数 .
    */
    public String yearMonthDayByC(String time) {
    	String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	int yearIndex = time.indexOf("年");
    	int monthIndex = 0;
    	int dayIndex = 0;
    	if(time.indexOf("月") != -1) {
    		monthIndex = time.indexOf("月");
    		if(time.indexOf("日") != -1) {
    			dayIndex = time.indexOf("日");
    			return time.substring(0, yearIndex) + "-" 
    				+ time.substring(yearIndex + 1, monthIndex) 
    						+ "-" + time.substring(monthIndex + 1, dayIndex);
    		}
    		return time.substring(0, yearIndex) + "-" + time.substring(yearIndex + 1, monthIndex) 
						+ now.substring(now.lastIndexOf("-"), now.indexOf(" "));
    	}
    	return time.substring(0, yearIndex) + now.substring(now.indexOf("-"), now.indexOf(" "));
    }
	 
    /**
     * hourMinSS:(这里用一句话描述这个方法的作用).
     *
     * @param time time
     * @return    设定参数 .
    */
    public String hourMinSS(String time) {
    	String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	if(time.indexOf(":")==-1) {
    		if(time.length()>2) {
    			return now.substring(now.indexOf(" ")+1);
    		} else {
    			return time + now.substring(now.indexOf(":"));
    		}
    	} else {
    		String[] times = time.split(":");
    		if(times.length==3) {
    			return time;
    		} else if(times.length==2) {
    			if(time.endsWith(":")) {
    				return time + now.substring(now.lastIndexOf(":")+1);
    			} else {
    				return time + now.substring(now.lastIndexOf(":"));
    			}
    		} else {
    			return time + now.substring(now.indexOf(":")+1);
    		}
    	}
    }
     
	/**
	 * startCheck:对时间格式进行匹配.
	 *
	 * @param reg reg
	 * @param string string
	 * @return    设定参数 .
	*/
	public static boolean startCheck(String reg, String string) {
		boolean tem=false; 
		 
		Pattern pattern = Pattern.compile(reg); 
		Matcher matcher=pattern.matcher(string); 
		
		tem=matcher.matches(); 
		return tem; 
	}
}
