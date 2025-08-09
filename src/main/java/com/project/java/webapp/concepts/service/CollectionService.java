package com.project.java.webapp.concepts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CollectionService {

	// 1. Iterable - 3 features - forEach(), iterator, spliterator

	public String learningArrayList() {
		log.info("Inside ArrayList Learning");

		// List:
		// 0. index based access, allows duplicate, maintain the insertion order
		// uses array only internally named as 'elementData', initial capacity = 10
		// growth factor = 1.5 .... 10 -> 15 -> 22 -> 33 -> 49 -> 73

		// ------------------------------------------------------------------------- //

		// 1. If we knows in advance that some 1000 elements are coming, we can override
		// the initial capacity
		// 2. We don't have any method to print capacity, but we can use Reflections
		ArrayList<Integer> manualCapacityList = new ArrayList<>(1000);
		manualCapacityList.add(4);
		manualCapacityList.add(5);
		// 3. Now size = 2, but capacity = 1000. grow of array is handled by JVM
		// but its shrink we have to manually if needed
		manualCapacityList.trimToSize();

		// ------------------------------------------------------------------------- //

		// 1. Ways to create arrayList - new ArrayList<>() , Arrays.asList(), List.of()

		// 2. Arrays.asList() - It is type of java.util.Arrays$ArrayList. Hence it does
		// not supports the add() and remove() method. But set() can be used.
		// It will throw UnsupportedOperationException.
		// We can pass Wrapper array also in its construtor, but not primitive array
		Integer[] arr = { 5, 6, 7, 8, 9 };
		List<Integer> arraysList = Arrays.asList(arr);
		log.info("arraysList: {}", arraysList);

		// 3. List.of() - It returns 'ImmutableCollections'. Hence add(), remove(),
		// set() not allowed - UnsupportedOperationException
		// we can pass Wrapper array as well
		List<Integer> ofList = List.of(arr);
		log.info("ofList: {}", ofList);

		// 4. we can pass a list into another list.
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>(list1);
		log.info("list2: {}", list2);

		// 5. Double Brace Initialization
		// Outer - Anonymous Inner class
		// Inner - Instance initializer block
		List<Integer> list = new ArrayList<>() {
			private static final long serialVersionUID = 1L;
			{
				add(10);
				add(20);
				add(40);
				add(55);
				add(9);
			}
		};

		// 6. methods
		list.get(0);
		list.add(66);
		list.add(2, 33); // add and shift
		list.set(2, 33); // replace the existing

		int size = list.size();
		log.info("size: {}", size);

		boolean contains = list.contains(65);
		log.info("contains: {}", contains);

		list.remove(0);
		list.remove((Object) 45); // to avoid ambiguity
		list.addAll(list1);
		list.removeAll(list1); // similar to addAll(), it will remove from list the elements of list1

		Integer[] array = list.toArray(new Integer[0]); // only wrapper array
		log.info("array: {}", Arrays.toString(array));

		list.sort(Comparator.naturalOrder());
		log.info("list2: {}", list2);
		
		list.clear();

		return "ArrayList is working";
	}
	
	
	
	
	public String learningLinkedList() {
		
		// 1. It used Doubly LinkedList internally
		/*
		 private static class Node<E> {
	        E item;
	        Node<E> next;
	        Node<E> prev;
	     }
		 */
		
		// 2. Better insertion and deletion - as no need of shifting of elements.
		// 3. Random Access - slow, as traversal needs to be done.
		// 4. Memory Overhead - storing one data occupying more space compared to AL.
		LinkedList<Integer> linkedList = new LinkedList<>();

		// Functionality wise both are same. but add() is coming from List - boolean
		// while addLast() is coming from Deque - void
		linkedList.add(44); // O(1)
		linkedList.addLast(59); // O(1)

		linkedList.addFirst(12); // O(1)
		linkedList.get(2); // O(n)

		return "LinkedList is working";
	}
	
	
	
	
	public String learningVector() {
		// 1. It is one of the legacy class which came in Java1.0 and is synchronized, making it thread safe.
		// Now it is part of Collection framework.
		// If single thread scenario - go with ArrayList
		// If multi-thread scneario - go with Vector, but in modern Java application 
		// CopyOnWriteArrayList or ConcurrentHashMap are preffered.
		
		
		// 2. Initial capacity = 10, growth factor default = 2x (double)
		Vector<Integer> vector = new Vector<>();
		log.info("vector: {}", vector);
		
		
		// 3. We can override the growth factor and we can also print capacity as well.
		// when size exceeds, increase the size by 4 - 20, 24, 28, 32.....
		Vector<Integer> manualCapacityVector = new Vector<>(20, 4);
		int capacity = manualCapacityVector.capacity();
		log.info("capacity: {}", capacity);
		
		
		// 4. If anyone asks, show AL is not thread safe, 
		// use synchronized code where we were updating the INT value by 2 threads 
		// and replace that INT increment with 2 threads adding into same AL 
		
		
		return "LinkedList is working";
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
