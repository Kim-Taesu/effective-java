package com.demo.effectivejava.item3;

/**
 * public static final 필드 방식의 싱글턴
 */
public class Elvis {
	public static final Elvis INSTANCE = new Elvis();

	private Elvis() {
	}

	// 싱글턴임을 보장해주는 readResolve 메서드
	public Object readResolve() {
		// 진짜 INSTNACE를 반환하고, 가짜 INSTANCE는 가비지 컬렉터에 맡긴다.
		return INSTANCE;
	}
}
