package com.shineyue.utils;

/**
 * snowflake算法生成唯一ID
 * @author Administrator
 *
 */
public class IdWorker {
	
	private static final long TWEPOCH = 1288834974657L;
	private static final long WORKERIDBITS = 5L;
	private static final long DATACENTERIDBITS = 5L;
	private static final long SEQUENCEBITS = 12L;
	private static final long WORKERIDSHIFT = SEQUENCEBITS;
	private static final long DATACENTERIDSHIFT = SEQUENCEBITS + WORKERIDBITS;
	private static final long TIMESTAMPLEFTSHIFT = SEQUENCEBITS + WORKERIDBITS + DATACENTERIDBITS;
	private static final long SEQUENCEMASK = -1L ^ (-1L << SEQUENCEBITS);

	private static final long WORKERID = 18;
	private static final long DATACENTERID = 01;
	private static long sequence = 0L;
	private static long lastTimestamp = -1L;
	
	/**
	 * @param WORKERID : 机器ID 0--31
	 * @param DATACENTERID : 业务ID  0--31
	
	public IdWorker(long WORKERID, long DATACENTERID) {
		if (WORKERID > maxWorkerId || WORKERID < 0) {
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
		if (DATACENTERID > maxDatacenterId || DATACENTERID < 0) {
			throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
		}
		this.WORKERID = WORKERID;
		this.DATACENTERID = DATACENTERID;
	} */

	public static synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & SEQUENCEMASK;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}
		lastTimestamp = timestamp;
		return ((timestamp - TWEPOCH) << TIMESTAMPLEFTSHIFT) | (DATACENTERID << DATACENTERIDSHIFT) | (WORKERID << WORKERIDSHIFT) | sequence;
	}
	
	protected static long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}
	
	protected static long timeGen() {
		return System.currentTimeMillis();
	}
	

}
