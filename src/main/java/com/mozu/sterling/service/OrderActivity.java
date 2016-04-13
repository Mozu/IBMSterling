package com.mozu.sterling.service;

import com.mozu.sterling.jmsUtil.MostRecentlyUsedMap;

/**
 * Keeps track of recently processed orders to mitigate endless loops due to fired
 * events in both mozu and sterling
 *
 */
public class OrderActivity {
	private static final MostRecentlyUsedMap<String> orderCache;
	private static final Object lockObj = new Object();
	private static final long maxDuration = 10000l;

	static {
		orderCache = new MostRecentlyUsedMap<String>(1000, 0.75f, true, maxDuration, 5000);
	}

	public void sawOrder(String orderId) {
		if (orderId != null) {
			synchronized (lockObj) {
				if (orderCache.containsKey(orderId)) {
					orderCache.remove(orderCache.get(orderId));
				}
				orderCache.put(orderId, System.currentTimeMillis());
			}
		}
	}

	public boolean contains(String orderId) {
		boolean found = false;

		synchronized (lockObj) {
			Long timestamp = orderCache.get(orderId);
			if (timestamp != null) {
				long diff = System.currentTimeMillis() - timestamp.longValue();

				if (maxDuration > diff) {
					orderCache.put(orderId, Long.valueOf(timestamp.longValue() + diff));
					found = true;
				}
			}
		}

		return found;
	}
}
