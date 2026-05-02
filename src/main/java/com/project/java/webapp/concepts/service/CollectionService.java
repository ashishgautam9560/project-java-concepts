package com.project.java.webapp.concepts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.springframework.stereotype.Service;

import com.project.java.webapp.concepts.model.Days;

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
	
	
	
	
	public static String learningHashMap() {
		HashMap<Integer, String> hashMap = new HashMap<>();
		hashMap.put(1, "Ashish");
		hashMap.put(2, "Ashwani");
		
		// Returns null when no value is present for given Key
		String valueAt3 = hashMap.get(3);
		log.info(valueAt3 + "\n");

		// containsKey and containsValue
		boolean containsKey = hashMap.containsKey(1); // true
		boolean containsValue = hashMap.containsValue("ashish"); // false - case sensitive
		log.info(containsKey + "");
		log.info(containsValue + "\n");

		// Used to loop on map
		Set<Integer> keySet = hashMap.keySet();
		log.info(keySet + "\n");
		
		Set<Entry<Integer, String>> entrySet = hashMap.entrySet();
		log.info(entrySet + "\n");

		for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
			log.info(entry + "\n");
		}

		// Removes the entry for that key and returns its value.
		String remove = hashMap.remove(4);
		boolean remove2 = hashMap.remove(2, "ashwani");
		log.info(remove + "\n");
		log.info(remove2 + "\n");

		// initial capacity = 20
		// load factor = 2
		HashMap<Integer, String> hashMapNew = new HashMap<>(20, 2f);
		log.info(hashMapNew + "\n");
		
		
		// 1. Not thread-safe by default as no synchronized.
		// 2. getOrDefault()
		String orDefault = hashMap.getOrDefault(3, "Default Value");
		log.info(orDefault + "\n");
		
		// 3. putIfAbsent() -> Only add if that key is not present.
		hashMap.putIfAbsent(3, "Mayank");

		return "HashMap is working";
	}
	
	
	
	
	public static String learningLinkedHashMap() {
		// 1. It maintains the insertion order because it keeps a doubly-linked list of
		// entries, which preserves the order in which keys were inserted.
		LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
		linkedHashMap.put("Orange", 15);
		linkedHashMap.put("Mango", 10);
		linkedHashMap.put("Apple", 20);
		log.info(linkedHashMap + "\n");

		// 2. boolean accessOrder - 
		// false , default value, maintains the Insertion order.
		// true , Entries are reordered based on recent access.
		// ✔ Recently accessed (used) entries → move to the end
		// ✔ Least recently used (LRU) entries → stay at the beginning
		// [ Least Recently Used ........ Most Recently Used ]
		// (start) (end)
		LinkedHashMap<String, Integer> linkedHashMap1 = new LinkedHashMap<>(11, 0.3f, true);
		linkedHashMap1.put("Orange", 15);
		linkedHashMap1.put("Mango", 10);
		linkedHashMap1.put("Apple", 20);
		Integer key = linkedHashMap1.get("Apple"); // As we have accessed "Apple" it will be moved to end.
		log.info(key + "\n");
		log.info(linkedHashMap1 + "\n");
		return "LinkedHashMap is working";
	}
	
	
	
	
	// 1. Uses weak references for keys, allowing them to be garbage collected when
	// not referenced elsewhere.
	// 2. Values are strongly referenced, so only keys control entry lifecycle.
	// 3. Entries are automatically removed when keys are collected by the GC.
	// 4. Commonly used for caches, metadata storage, and listener mappings.
	public static String learningWeakHashMap() {
		WeakHashMap<String, String> weakHashMap = new WeakHashMap<>();
		log.info("" + weakHashMap);

		return "WeakHashMap is working";
	}
	
	
	
	
	// Uses reference equality (==) instead of .equals() to compare keys as well as values.
	// Two objects with the same content are treated as different keys unless they
	// are the same object/instance.
	public static String learningIdentityHashMap() {
		IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();
		log.info("" + identityHashMap);

		return "IdentityHashMap is working";
	}
	
	
	
	
	// Entries are sorted based on Keys - 
	// either on natural ordering (or) by specified comparator.
	
	// TreeMap uses Red Black Tree internally. - TC O(log n)
	public static String learningTreeMap() {
		SortedMap<Integer, String> treeMap = new TreeMap<>(Comparator.reverseOrder());
		treeMap.put(91,"Apple");
		treeMap.put(88,"Aapple");
		treeMap.put(100,"Mango");
		treeMap.put(99,"Guava");
		log.debug(treeMap + "\n");
		
		// To use SortedMap methods we have to create object of SortedMap
		treeMap.firstKey();
		treeMap.lastKey();
		treeMap.headMap(91); // 100, 99. print entries from start till 91, excluding 91
		treeMap.tailMap(91); // 91, 88. including 91
		
		treeMap.subMap(99, 91); // fromKey to toKey
		
		// To use NavigableMap methods we have to create object of NavigableMap
		// Map -> SortedMap -> NavigableMap -> TreeMap
		// So its recommended to use NavigableMap to have access of all Map, SortedMap, NavigableMap
		// Better to use TreeMap directly. To have access of all.
		
		NavigableMap<Integer, String> navigableMap = new TreeMap<>();
		navigableMap.put(1, "One");
		navigableMap.put(5, "Five");
		navigableMap.put(3, "Three");
		
		// lowerKey - Returns the greatest key strictly less than the given key
		// ceilingKey - Returns the least key greater than or equal to the given key
		log.info(navigableMap.lowerKey(4) + "\n"); // 3
		log.info(navigableMap.ceilingKey(4) + "\n"); // 5

		return "TreeHashMap is working";
	}
	
	
	
	
	// 1. It is synchronized - so it is slower than HashMap.
	// 2. This is a legacy class it was present even before the Collection.
	// 3. No NULL, not key not even value.
	// 4. No one uses it - As it is replaced by ConcurrentHashMap.
	// 5. As it is implementing Map, it contains all methods of Map.
	public static String learningHashTable() {
		Hashtable<Integer, String> hashtable = new Hashtable<>();

		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				hashtable.put(i, "Thread1");
			}
		});

		Thread t2 = new Thread(() -> {
			for (int i = 1000; i < 2000; i++) {
				hashtable.put(i, "Thread2");
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

		// But if we use HashMap it will be anything 2000 or less
		log.info(hashtable.size() + "\n"); // 2000
		return "HashTable is working";
	}
	
	
	
	// Concurrency → handling many tasks (not necessarily at the same time)
	// Parallelism → doing many tasks at the same time
	
	// Doesn’t lock the entire map (better performance)
	
	// 2. High concurrency (segment/bucket-level locking)
	// Older versions (Java 7): Segment Based Locking (16 segments) - It will divide the map into 16 smaller HashMaps.
	// So it will lock only that particular segment and not the entire Map. 
	// Hence, multiple threads can update different parts simultaneously.
	
	// 3. Java 8 - Segmentation approach is removed completely. Why ?
	// Because it is not scalable and 16 is fix.. what if Map is too big then still dividing into 16 is not helpful.
	// It uses Compare and Swap(CAS) approach -- there is no lock unless resizing or collision is happening
	// CAS - 
	// Thread A last saw --> x = 45
	// Thread A work --> x to 50
	// --> if x is still 45, then change it to 50 else don't change.. and retry..
	// it can happen that it will ends up calling it many times.. So internally JVM puts some delay between
	// 2 calls if it is occurring again and again.
	public static String learningConcurrentHashMap() {
		ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
		log.info(concurrentHashMap + "\n");
		return "ConcurrentHashMap is working";
	}
	
	
	
	// .of() method --> Return Unmodifiable Collection - Java 9
	// Collections.unmodifiableMap()
	// Map.ofEntries(Map.entry(1, "Apple"));
	public static String learningImmutableMap() {
		Map<Integer, String> hashMap = new HashMap<>();
		hashMap.put(2, "Apple");
		hashMap.put(4, "Mango");

		Map<Integer, String> hashMap1 = Collections.unmodifiableMap(hashMap);

		log.info(hashMap1 + "\n");
		// hashMap1.put(6, "Grapes"); // java.lang.UnsupportedOperationException
		log.info(hashMap1 + "\n");

		// Cons - Map.of(k1,v1, k2,v2, ..., k10,v10); // ✅ It can have up to 10 entries.
		// what if we need 11 entries. Use -Map.ofEntries
		Map<Integer, String> hashMap2 = Map.of(2, "apple", 4, "mango");
		hashMap2.put(6, "Guava"); // Exception in thread "main" java.lang.UnsupportedOperationException

		Map.ofEntries(Map.entry(1, "Apple"));
		return "ImmutableMap is working";
	}
	
	

	// Enum - Internally stores data in fixed size array.
	// So when we use it in EnumMap.. there is no need of calculating HashFunc() for that key.
	// It will be directly attached to that Enum Array Index/Ordinal. 
	// No hashing → faster performance.
	
	// Type safety (compile-time guarantees) - You cannot accidentally use an invalid key.
	public static String learningEnumMap() {
		Map<Days, String> enumMap = new EnumMap<>(Days.class);
		enumMap.put(Days.TUESDAY, "Veg");
		log.info(enumMap + "\n");

		// It returns the index.
		log.info(Days.FRIDAY.ordinal() + "\n"); // 5-1 = 4

		return "EnumMap is working";
	}
	
	
	
	
	// TreeMap - Not thread safe, uses RB Tree
	// ConcurrentSkipList - Thread safe, uses Skip List DS
	// It is a Thread safe treeMap we can say.
	// Keeps keys sorted
    // Allows safe concurrent access
    // Uses a Skip List data structure - sorted LL with multiple layers that skip over portions
	// 1 . . . 5 . . . 9 - Layer 3
	// 1 . 3 . 5 . 7 . 9 - Layer 2
	// 1 2 3 4 5 6 7 8 9 - Layer 1
	// Suppose we are searching for (2) - First it will look into layer3.
	// smaller than <5.. will go to Layer 2.. and so on till found out.
	public static String learningCncurrentSkipListMap() {
		ConcurrentSkipListMap<Integer, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
		log.info(concurrentSkipListMap + "\n");
		return "learningCncurrentSkipListMap is working";
	}
	
	
	
	
	public static String learningIterator() {
		List<Integer> list = new ArrayList<>();
		list.add(5);
		list.add(6);
		list.add(1);
		list.add(2);
		list.add(9);

		for(Integer i : list) {
			log.info("" + i);
		}
		
		// Internally above one is converted to below only.
		// hasNext() -- boolean
		// next() ----- Integer
		Iterator<Integer> iterator = list.iterator();
		while (iterator.hasNext()) {
			log.info("" + iterator.next());
		}

		// remove() --- void
		// On AL if use forEach directly, it will give ConcurrentModificationException. - Fail Fast
		// But is using Iterator it will work.
		while(iterator.hasNext()) {
			if(iterator.next() %2 !=0) {
				iterator.remove();
			}
		}

		return "Iterator is working";
	}
	
	
	
	
	
	
}
