package com.project.java.webapp.concepts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Java8StreamService {
	
	public String testStreams() {
		
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

		return "Streams are working";
	}

}
