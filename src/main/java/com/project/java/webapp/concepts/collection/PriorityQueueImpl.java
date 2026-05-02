package com.project.java.webapp.concepts.collection;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/*
 * Heap has 2 properties - 
 * 1. Heap Order Property - Parent should have high priority as compared to children
 * 2. Complete Binary Tree - Filling of elements :
 * 		=> Left to Right
 * 		=> Top to Bottom
 */

@Slf4j
public class PriorityQueueImpl {

	List<Integer> data;

	public PriorityQueueImpl() {
		data = new ArrayList<>();
	}

	// This will take O(nlogn)
	// n elements call add() which is of log-n
	public PriorityQueueImpl(int[] arr) {
		data = new ArrayList<>();
		for (int val : arr) {
			this.add(val);
		}
	}

	// We can achieve the same in O(n) using downHeapify()
	// passing Str just to avoid ambiguity between constructor overloading
	public PriorityQueueImpl(int[] arr, String str) {

		data = new ArrayList<>();

		// step1 -- Add into data arrayList directly
		for (int val : arr) {
			this.data.add(val);
		}

		// step2 -- As here we are calling downHeapify on n/2 elements
		// which is half of tree size which makes its TC = O(n)
		// it can be proved by Formulas
		for (int i = data.size() / 2 - 1; i >= 0; i--) {
			downHeapify(i);
		}
	}

	public void add(int value) {
		data.add(value);
		upHeapify(data.size() - 1);
	}

	private void upHeapify(int i) {
		if (i == 0) {
			return;
		}

		int pi = (i - 1) / 2;
		if (data.get(i) < data.get(pi)) {
			swap(i, pi);
			upHeapify(pi);
		}

	}

	private void swap(int i, int j) {
		int temp = data.get(i);
		data.set(i, data.get(j));
		data.set(j, temp);
	}

	public Integer remove() {
		if (this.data.isEmpty()) {
			log.info("Underflow..!!");
			return -1;
		}
		swap(0, data.size() - 1);
		int val = data.remove(data.size() - 1);
		downHeapify(0);
		return val;
	}

	private void downHeapify(int pi) {
		int mini = pi;
		int li = 2 * pi + 1;
		if (li < data.size() && data.get(li) < data.get(mini)) {
			mini = li;
		}

		int ri = 2 * pi + 2;
		if (ri < data.size() && data.get(ri) < data.get(mini)) {
			mini = ri;
		}

		// this is for last node - where we have to stop finally.
		if (mini != pi) {
			swap(pi, mini);
			downHeapify(mini);
		}
	}

	public Integer peek() {
		if (this.data.isEmpty()) {
			log.info("Underflow..!!");
			return -1;
		}
		return data.get(0);
	}

	public Integer size() {
		return data.size();
	}

}
