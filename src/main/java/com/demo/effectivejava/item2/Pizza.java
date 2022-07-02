package com.demo.effectivejava.item2;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class Pizza {
	final Set<Topping> toppings;

	Pizza(Builder<?> builder) {
		toppings = builder.toppings.clone();
	}

	@Override
	public String toString() {
		return "Pizza{" +
			"toppings=" + toppings +
			'}';
	}

	public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}

	abstract static class Builder<T extends Builder<T>> {
		EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

		public T addTopping(Topping topping) {
			toppings.add(Objects.requireNonNull(topping));
			return self();
		}

		abstract NyPizza build();

		// 하위 클래스는 이 메서드를 오버라이딩하여 "this"를 반환하도록 해야 한다.
		protected abstract T self();
	}
}
