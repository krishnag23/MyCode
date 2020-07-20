package com.swire.smo.inspection;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 *
 * @author Sony George : sony.george@swirecnco.com
 */
public class MultiUtil {

	public static Instant toInstant(java.sql.Timestamp columnValue) {
		return (null == columnValue ? null : columnValue.toInstant());
	}

	public static Timestamp toTimeStamp(Instant fieldValue) {
		return (null == fieldValue ? null : Timestamp.from(fieldValue));
	}

	public static Instant toInstant(Date columnValue) {
		return (null == columnValue ? null : columnValue.toInstant());
	}

	public static Instant toInstantOrNull(java.sql.Timestamp columnValue) {
		return (null == columnValue ? null : columnValue.toInstant());
	}

	public static Date toDateOrNull(java.sql.Timestamp columnValue) {
		return (null == columnValue ? null : new java.util.Date(columnValue.getTime()));
	}

	public static String toDayIn_yyyy_MM_dd() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static Date stringToDate_yyyy_MM_dd(String yyyyMMdd) {
		if (yyyyMMdd == null || yyyyMMdd.isEmpty()) {
			return null;
		}
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(yyyyMMdd);
		} catch (Exception e) {
			return null;
		}
	}

	public static Instant stringToInstant_yyyy_MM_dd(String yyyyMMdd) {
		if (yyyyMMdd == null || yyyyMMdd.isEmpty()) {
			return null;
		}
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(yyyyMMdd).toInstant();
		} catch (Exception e) {
			return null;
		}
	}

}
