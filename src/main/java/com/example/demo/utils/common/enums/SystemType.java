package com.example.demo.utils.common.enums;

public enum SystemType {

	/** Android */
	ANDROID(3, "Android "),
	/** IOS */
	IOS(4, "IOS");

	public int getValue() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static SystemType parse(int type) {
		SystemType s = null;
		for (SystemType o : SystemType.values()) {
			if (o.getValue() == type) {
				s = o;
				break;
			}
		}
		return s;
	}

	private SystemType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	private int type;
	private String name;
}
