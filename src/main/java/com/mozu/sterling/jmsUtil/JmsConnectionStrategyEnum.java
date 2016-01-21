package com.mozu.sterling.jmsUtil;

import java.util.HashMap;
import java.util.Map;

public enum JmsConnectionStrategyEnum {
	DIRECT("jmsSib"), WEBSPHEREMQ("jsmMQ"), JNDI("jmsJNDI");

	private final String strategyName;
	private static Map<String, JmsConnectionStrategyEnum> map;

	static {
		map = new HashMap<String, JmsConnectionStrategyEnum>();
		map.put("jmsSib", DIRECT);
		map.put("jmsMQ", WEBSPHEREMQ);
		map.put("jmsJNDI", JNDI);
	}

	private JmsConnectionStrategyEnum(String strategyName) {
		this.strategyName = strategyName;
	}

	public String strategyName() {
		return strategyName;
	}

	public static JmsConnectionStrategyEnum from(String id) {
		return map.get(id);
	}
}
