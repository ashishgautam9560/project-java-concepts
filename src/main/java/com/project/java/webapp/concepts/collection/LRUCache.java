package com.project.java.webapp.concepts.collection;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	private int capacity;

	public LRUCache(int capacity) {
		super(capacity, 0.75f, true);
		this.capacity = capacity;
	}

	// When the LinkedHashMap size becomes > capacity. 
	// It will remove the LRU entry.
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return super.size() > capacity;
	}

}
