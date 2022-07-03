package com.demo.effectivejava.item6;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack {
	private static final int CAPACITY = 16;
	private Object[] elements;
	private int size = 0;

	public Stack(Object[] elements) {
		this.elements = new Object[CAPACITY];

		for (Object element : elements) {
			push(element);
		}
	}

	public int getSize() {
		return size;
	}

	public void push(Object object) {
		ensureCapacity();
		elements[size++] = object;
	}

	public Object pop() {
		if (size == 0)
			throw new EmptyStackException();
		return elements[--size];
	}

	public Object pop2() {
		if (size == 0)
			throw new EmptyStackException();
		Object element = elements[--size];
		elements[size] = null;
		return element;
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}
}
