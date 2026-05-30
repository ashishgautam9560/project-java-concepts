package com.project.java.webapp.concepts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Java8StreamService {
	
	
	/*
	 * =========== Collections - Utility Class ===========
	 */
	public static String learningCollections() {
		
		/*
		 * 1. e
		 */
		
		Collections.emptyList(); // Internally it calls - Collections.EMPTY_LIST; Returns an empty list (immutable).
		Collections.emptyMap();  // Internally it calls - Collections.EMPTY_MAP;  Returns an empty map (immutable).
		Collections.emptySet();  // Internally it calls - Collections.EMPTY_SET;  Returns an empty set (immutable).
		
		Collections.emptyEnumeration(); // Legacy way of Iterator for legacy collections like Vector, Stack, and HashTable. - Java1.0
		Collections.emptyIterator();
		Collections.emptyListIterator();
		
		Collections.emptySortedMap();  // Returns an empty sorted map (immutable).
		Collections.emptySortedSet();  // Returns an empty sorted set (immutable).
		
		Collections.emptyNavigableMap(); // Returns an empty navigable map (immutable).
		Collections.emptyNavigableSet(); // Returns an empty navigable set (immutable).
		
		Collections.enumeration(new Vector<>()); // Returns an enumeration over the specified collection.
		
		
		return "Collections is working.";
	}
	
	
	
	public static String testStreams() {
		
		// Q1 - Sort the words on length then on dictionary order
		List<String> words = new ArrayList<>(Arrays.asList("a", "hi", "cat", "moon", "apple", "bat", "banana", "mango",
				"elephant", "strawberry", "encyclopedia", "hippopotamus", "Zoo"));
		List<String> lenThenDictSortDesc = words
				.stream()
				.sorted(
						Comparator.comparingInt(String::length).reversed()
						.thenComparing(Comparator.naturalOrder()))
				.toList();
		log.debug("lenThenDictSortDesc: {}", lenThenDictSortDesc);
		
		
		
		// Q2 - Find even, double then sum - Output = 40
		List<Integer> q2list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Integer q2Res = q2list
						.stream()
						.filter(e -> e % 2 == 0)
						.map(e -> 2 * e)
						.reduce((t, u) -> t + u)
						.orElse(-1);
		log.debug("q2Res: {}", q2Res);
		
		// Alternate for prefix sum (use sum()). But it can be called on mapToInt()
		int sum = q2list
		.stream()
		.mapToInt(e -> e)
		.sum();
		log.info("sum: {}", sum);
		
		
		
		// Q4 - Find length of Kth word in a sentence
		// Eg - This is the best place restaurant I have ever visited k=5(place) . Len = 5
		String msg = "This is the best place restaurant I have ever visited";
		int k = 6;
		int length = Arrays.stream(msg.split(" "))
							.skip(k-1)
							.findFirst()
							.orElse("")
							.length();
		log.debug("q3Res = {}", length);
		
		
		// Q5 - Map vs FlatMap
		List<Integer> numbers = Arrays.asList(42, 7, 19, 3, 88, 25, 10);
		List<Integer> doubleList = numbers.stream().map(t -> 2 * t).toList();
		log.info("q5.1Res = {}", doubleList);

		List<List<Integer>> asList = Arrays.asList(
										Arrays.asList(4, 7, 9), 
										Arrays.asList(1, 6, 10, 2), 
										Arrays.asList(9, 11, 25));
		List<Integer> collect = asList.stream().flatMap(t -> t.stream()).map(t->2*t).toList();
		log.info("q5.2Res = {}", collect);

		return "Streams are working";
	}
	
	public static List<Integer> streamIterate() {
		List<Integer> list = Stream.iterate(1, i -> i + 2).limit(5).toList();
		log.info(""+ list);
		return list;
	}
	
	
	public void maxMin2nd() {
		List<Integer> numbers = Arrays.asList(42, 7, 19, 3, 88, 25, 10);

		Integer min = numbers.stream().sorted()
						.limit(2)
						.skip(1)
						.toList().get(0); // 3,7,10,19,25,42,88
		
		Integer max = numbers.stream().sorted()
				.skip(numbers.size() - 2)
				.toList().get(0);

		log.info("" + min);
		log.info("" + max);
	}
	
	
	public static void isPangram() {

		String str = "The quick brown fox jumps over the lazy dog";

		boolean[] visited = new boolean[26];

		int length = str.length();
		for (int i = 0; i < length; i++) {
			char ch = Character.toLowerCase(str.charAt(i));
			if (ch == ' ')
				continue;
			visited[ch - 'a'] = true;
		}

		boolean isPangram = false;
		for (boolean b : visited) {
			if (!b)
				return;
		}

		log.info("IsPangram = " + isPangram);

	}
	
	
	private static String reverse(String str) {
		char[] charArray = str.toCharArray();
		int i = 0;
		int j = charArray.length - 1;
		while (i <= j) {
			char ch = charArray[i];
			charArray[i] = charArray[j];
			charArray[j] = ch;
			i++;
			j--;
		}
		return new String(charArray);
	}
	
	public static void reverseWordWithinString() {
		String str = "java is great";

		// There is no reverse method in string.. have to use SB
		String reverse = Arrays.stream(str.split(" "))
								.map(t -> reverse(t))
								.collect(Collectors.joining(" "));
		log.info(reverse);

		String strUsingSB = Arrays.stream(str.split(" "))
									.map(t -> new StringBuilder(t).reverse().toString())
									.collect(Collectors.joining(" "));
		log.info(strUsingSB);
	}
	
	public static void boxed() {
		int[] arr = { 10, 15, 8, 49, 25, 98, 32 };

		List<Integer> list = Arrays.stream(arr).boxed()
			.map(t -> String.valueOf(t))
			.filter(t -> t.startsWith("1"))
			.map(t -> Integer.valueOf(t)).toList();
		log.info("" + list);
	}

	public static void findDuplicate() {
		Set<Integer> set = new HashSet<>();
		List<Integer> myList = Arrays.asList(10, 15, 8, 49, 25, 98, 98, 32, 15);
		List<Integer> list = myList.stream().filter(t -> !set.add(t)).toList();
		log.info("" + list);
	}
	
	
	public static void firstNonRepeating() {
		String str = "Java articles are Awesome";
		
		// Use LinkedHashMap because it maintains the insertion order.
		Character result = str.chars() // Converts String into an IntStream of ASCII values - "aba" = 97,98,97
                			  .mapToObj(c -> (char) c) // Converts ASCII numbers back into Characters
                			  .collect(Collectors.groupingBy(
                					  		Function.identity(), // Use key = element itself,'a','b'
                					  		LinkedHashMap::new, // Use LinkedHashMap
                					  		Collectors.counting() // Use value as the frequency
                					  ))
                			  // {J=1, a=4, v=1,  =3, r=2, t=1, i=1, c=1, l=1, e=4, s=2, A=1, w=1, o=1, m=1}
                
                			  .entrySet().stream()
                			  .filter(e -> e.getValue() == 1)
                			  .findFirst()
                			  .map(t -> t.getKey())
                .orElse(null);

        log.info(""+result);
        
	}
	
	
	public static void oddEvenList() {
		// {false=[15, 49, 25, 15], true=[10, 8, 98, 98, 32]}
		List<Integer> myList = Arrays.asList(10, 15, 8, 49, 25, 98, 98, 32, 15);
		Map<Boolean, List<Integer>> collect = myList.stream().collect(Collectors.partitioningBy(t -> t % 2 == 0));
		log.info("" + collect);
	}
	

}
