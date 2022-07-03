package com.demo.effectivejava.item6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		Stack stack = new Stack(new Object[] {1, "2", 3});

		for (int i = 0; i < stack.getSize(); i++) {
			log.info("{}", stack.pop());
		}
	}
}
