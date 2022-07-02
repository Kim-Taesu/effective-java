package com.demo.effectivejava.item3;

import java.util.Objects;

public class Main {
	public static void main(String[] args) {
		Elvis elvis = Elvis.INSTANCE;
		Objects.deepEquals(elvis, Elvis.INSTANCE);

		Elvis2 elvis2 = Elvis2.getInstance();
		Objects.deepEquals(elvis2, Elvis2.getInstance());
	}
}
