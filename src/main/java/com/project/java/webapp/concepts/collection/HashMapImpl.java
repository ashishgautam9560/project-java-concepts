package com.project.java.webapp.concepts.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashMapImpl<K, V> {

	class HMNode {
		K key;
		V value;

		public HMNode(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private LinkedList<HMNode>[] buckets;
	private int size; // no. of nodes and not number of buckets

	public HashMapImpl() {
		// java.util.HashMap = 16
		initBuckets(4);
		size = 0;
	}

	public void put(K key, V value) {
		int bi = hashFunction(key);
		int di = getIndexWithinBucket(key, bi);

		if (di != -1) {
			HMNode node = buckets[bi].get(di);
			node.value = value;
		} else {
			HMNode node = new HMNode(key, value);
			buckets[bi].add(node);
			size++;
		}

		// Load Factor = No. of nodes / No. of Buckets
		// java.util.HashMap LF = 0.75
		double lambda = (size * 1.0) / buckets.length;
		if (lambda > 2.0) {
			rehash();
		}
	}

	public boolean containsKey(K key) {
		int bi = hashFunction(key);
		int di = getIndexWithinBucket(key, bi);
		return di != -1;
	}

	public V get(K key) {
		int bi = hashFunction(key);
		int di = getIndexWithinBucket(key, bi);

		if (di != -1) {
			return buckets[bi].get(di).value;
		}
		return null;
	}

	public int size() {
		return this.size;
	}

	public V remove(K key) {
		int bi = hashFunction(key);
		int di = getIndexWithinBucket(key, bi);

		if (di != -1) {
			size -= 1;
			return buckets[bi].remove(di).value;
		}
		return null;
	}

	public List<K> keySet() {
		List<K> keys = new ArrayList<>();
		for (LinkedList<HMNode> nodes : buckets) {
			for (HMNode node : nodes) {
				keys.add(node.key);
			}
		}
		return keys;
	}

	private void initBuckets(int n) {
		buckets = new LinkedList[n];
		for (int bi = 0; bi < buckets.length; bi++) {
			buckets[bi] = new LinkedList<>();
		}
	}

	private void rehash() {
		LinkedList<HMNode>[] oba = buckets; // Old Buckets Array
		initBuckets(oba.length * 2);
		size = 0;

		for (LinkedList<HMNode> nodes : oba) {
			for (HMNode node : nodes) {
				put(node.key, node.value);
			}
		}
	}

	private int hashFunction(K key) {
		int hashCode = key.hashCode();
		return Math.abs(hashCode) % buckets.length; // As hashCode can be negative also.
	}

	private int getIndexWithinBucket(K key, int bi) {
		int di = 0;
		for (HMNode node : buckets[bi]) {
			if (node.key.equals(key)) {
				return di;
			}
			di += 1;
		}
		return -1;
	}

}
