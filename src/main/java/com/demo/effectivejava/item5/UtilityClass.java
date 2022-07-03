package com.demo.effectivejava.item5;

import java.util.regex.Pattern;

public class UtilityClass {
	private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

	private UtilityClass() {
		throw new AssertionError("Utility Class");
	}

	public static boolean isRomanNumeral(String s) {
		return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
	}

	public static boolean isRomanNumeral2(String s) {
		return ROMAN.matcher(s).matches();
	}

	public static long sum() {
		Long sum = 0L;
		for (int i = 0; i <= 1000000; i++) {
			sum += i;
		}
		return sum;
	}

	public static long sum2() {
		long sum = 0L;
		for (int i = 0; i <= 1000000; i++) {
			sum += i;
		}
		return sum;
	}
}
