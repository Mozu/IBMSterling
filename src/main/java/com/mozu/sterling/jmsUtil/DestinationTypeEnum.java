package com.mozu.sterling.jmsUtil;

import java.util.HashMap;
import java.util.Map;

public enum DestinationTypeEnum {
	QUEUE("queue"), TOPIC("topic");

	private final String destinationName;
	private static Map<String, DestinationTypeEnum> map;

	static {
		map = new HashMap<String, DestinationTypeEnum>();
		map.put("queue", QUEUE);
		map.put("topic", TOPIC);
	}

	private DestinationTypeEnum(String destinationName) {
		this.destinationName = destinationName;
	}

	public String destinationName() {
		return destinationName;
	}

	public static DestinationTypeEnum from(String id) {
		return map.get(id);
	}
}
