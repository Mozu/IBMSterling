package com.mozu.sterling.jmsUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Keeps a LRU or MRU list that will evict members base on a timeout and max size.
 *
 * @param <K>
 */
public class MostRecentlyUsedMap<K> extends LinkedHashMap<K, Long> {
	private static final long serialVersionUID = 1L;
	private long maxMillisecondDuration;
	private int maxSize;

	protected MostRecentlyUsedMap() {
		super();
	}

	public MostRecentlyUsedMap(int initialCapacity, float loadFactor,
			boolean accessOrder, long maxMillisecondDuration, int maxSize) {
		super(initialCapacity, loadFactor, accessOrder);
		this.maxMillisecondDuration = maxMillisecondDuration;
		this.maxSize = maxSize;
	}

	@Override
	protected boolean removeEldestEntry(Entry<K, Long> eldest) {
		for (Iterator<Map.Entry<K, Long>> it = this.entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<K, Long> entry = it.next();
			if (maxMillisecondDuration <= (System.currentTimeMillis() - entry
					.getValue().longValue())) {
				it.remove();
			} else {
				break;
			}
		}

		return this.size() > maxSize;
	}

	public long getMaxMillisecondDuration() {
		return maxMillisecondDuration;
	}
}
