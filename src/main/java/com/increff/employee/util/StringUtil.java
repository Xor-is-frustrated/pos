package com.increff.employee.util;

import org.apache.fop.afp.modca.Document;

public class StringUtil {

	
	
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static String toLowerCase(String s) {
		return s == null ? null : s.trim().toLowerCase();
	}

}
