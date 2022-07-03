package com.demo.effectivejava.item4;

public class UtilityClass {
	private UtilityClass() {
		throw new AssertionError("Utility Class");
	}

	public static int add(int a, int b) {
		return a + b;
	}
}
