package com.demo.effectivejava.item5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	private static final String VALUE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void main(String[] args) {
		roman1();
		roman2();

		sum1();
		sum2();
	}

	private static void roman1() {
		long start = System.currentTimeMillis();
		boolean romanNumeral = UtilityClass.isRomanNumeral(VALUE);
		long end = System.currentTimeMillis();
		log.info("[roman1] {} {}", romanNumeral, end - start);
	}

	private static void roman2() {
		long start = System.currentTimeMillis();
		boolean romanNumeral = UtilityClass.isRomanNumeral2(VALUE);
		long end = System.currentTimeMillis();
		log.info("[roman2] {} {}", romanNumeral, end - start);
	}

	private static void sum1() {
		long start = System.currentTimeMillis();
		long result = UtilityClass.sum();
		long end = System.currentTimeMillis();
		log.info("[sum1] {} {}", result, end - start);
	}

	private static void sum2() {
		long start = System.currentTimeMillis();
		long result = UtilityClass.sum2();
		long end = System.currentTimeMillis();
		log.info("[sum2] {} {}", result, end - start);
	}
}
