package com.demo.effectivejava.item2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		example1();
		example2();
	}

	private static void example2() {
		NyPizza nyPizza = new NyPizza.Builder(NyPizza.Size.SMALL)
			.addTopping(Pizza.Topping.SAUSAGE)
			.addTopping(Pizza.Topping.ONION)
			.build();

		log.info("{}", nyPizza);
	}

	private static void example1() {
		NutritionFacts nutritionFacts = new NutritionFacts.Builder(240, 8)
			.calories(100)
			.sodium(35)
			.carbohydrate(27)
			.build();

		log.info("{}", nutritionFacts);
	}
}
