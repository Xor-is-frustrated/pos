package com.increff.pos.service;

public abstract class AbstractService {

	public static void checkNull(Object obj, String msg) throws ApiException {
		if (obj != null) {
			throw new ApiException(msg);
		}
	}

	public static void checkPositive(int obj, String msg) throws ApiException {
		if (obj < 0) {
			throw new ApiException(msg);
		}
	}

	public static void checkPositive(double obj, String msg) throws ApiException {
		if (obj < 0) {
			throw new ApiException(msg);
		}
	}

	public static void checkNotNull(Object obj, String msg) throws ApiException {
		if (obj == null) {
			throw new ApiException(msg);
		}
	}
	
	public static void checkZero(int length, String msg) throws ApiException {
		if (length == 0) {
			throw new ApiException(msg);
		}
	}

}
