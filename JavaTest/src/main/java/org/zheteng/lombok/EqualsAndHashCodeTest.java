package org.zheteng.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class EqualsAndHashCodeTest {

	public static void main(String[] args) {
		Child c1 = new Child("sb", 1000000, 100, "111");
		Child c2 = new Child("sb", 1000000, 200, "222");
		System.out.println(c1.equals(c2));
	}
}

@Data
@AllArgsConstructor
class Parent {
	private int id;
	private String name;
}

@Data
@EqualsAndHashCode(callSuper = true, exclude = "nickname")
class Child extends Parent {
	public Child(String nickname, long savings, int id, String name) {
		super(id, name);
		this.nickname = nickname;
		this.savings = savings;
	}
	private String nickname;
	private long savings;
}
