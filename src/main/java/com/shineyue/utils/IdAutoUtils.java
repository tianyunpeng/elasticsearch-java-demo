package com.shineyue.utils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ID自增器
 * @author Administrator
 *  max Long 19
 */
public class IdAutoUtils {
  
	 public static  AtomicLong atomicTimeMills = new AtomicLong(0);
	 private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	 private  static DecimalFormat sizeFormatter=new DecimalFormat("00");
     public  static  AtomicInteger  no=new AtomicInteger();  	 
	 public  static  long   lastId=0L;
 	 public  static  int maxIncreSize=90;
 	 /**
 	 --- 2020-0429-162601-799-44
 	    时间戳+两位递增ID
 	  * @return
 	  */
	 public static long getNextTimeId() {
		    while (true) {
		        long currentMill = atomicTimeMills.get();
		        lastId = Long.parseLong(LocalDateTime.now().format(formatter) +sizeFormatter.format(no.incrementAndGet()));
		        if (lastId > currentMill && atomicTimeMills.compareAndSet(currentMill, lastId)) {
		            if (currentMill>maxIncreSize) {
						 no.set(0);
					}
		            return lastId;
		        }
		    }
		}
}
