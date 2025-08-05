package com.project.java.webapp.concepts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ComparatorService {

	public String learningComparator() {

		List<Integer> numbers = Arrays.asList(42, 7, 19, 3, 56, 8, 29, 15, 73, 4);

		// 1. Object
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		};

		
		// 2. Ascending
		numbers.sort(null);
		numbers.sort(comparator);
		numbers.sort(Comparator.naturalOrder());
		numbers.sort((a, b) -> a.compareTo(b)); // It internally calls below only
		numbers.sort((a, b) -> Integer.compare(a, b));
		numbers.sort((a, b) -> a - b); // its not recommended as it can cause Integer overflow
		// a= +infinit, b= -infinite... a-b = +inf - (-inf) = +inf + inf

		
		// 3. Descending
		numbers.sort(Comparator.reverseOrder());
		numbers.sort((a, b) -> b.compareTo(a)); // It internally calls below only
		numbers.sort((a, b) -> Integer.compare(b, a));
		numbers.sort((a, b) -> b - a);

		
		
		/* ------------------------- */
		List<String> words = new ArrayList<>(Arrays.asList("a", "hi", "cat", "moon", "apple", "bat", "banana", "mango",
				"elephant", "strawberry", "encyclopedia", "hippopotamus", "Zoo"));

		
		// 4. It will sort on Dictionary order
		List<String> dictOrder = words
				.stream()
				.sorted() // naturalOrder() and reverseOrder() can be used if needed
				.toList();
		log.info("dictOrder = " + dictOrder);
		

		// 5. On Length
		List<String> lenSort = words
				.stream()
				.sorted((a, b) -> Integer.compare(a.length(), b.length()))
				.toList();
		log.info("lenSort = " + lenSort);


		
		// String comparator. Use method reference also to avoid error
		// use thenComparing() methods in chain within sorted()
		List<String> lenThenDictSortDesc = words
				.stream()
				.sorted(
						Comparator.comparingInt(String::length).reversed()
						.thenComparing(Comparator.naturalOrder()))
				.toList();
		log.info("lenThenDictSortDesc = " + lenThenDictSortDesc);

		return "Comparator is working";
	}

}
