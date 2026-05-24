package com.project.java.webapp.concepts.misc;

/*
 * 1. Here every field is basically an instance of Day itself.
 * 2. ordinal() - index of that instance.
 * 
 * At compiles time - 
 * 1. It makes the 'Day' as final class and extends java.lang.Enum<Day>
 * 
 * 2. It creates instance of own class itself like below for all the defined constants:
 * 		public static final Day SUNDAY = new Day("SUNDAY", 0);
 * 		public static final Day MONDAY = new Day("MONDAY", 1); and so on..
 * 
 * 3. Creates an array Day[] -
 * 		private static final Day[] VALUES = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}
 * 
 * 4. private parameterize constructor - 
 * 		private Day(String name, int ordinal) { 
 * 			super(name, ordinal)
 * 		}
 * 
 * 5. Static method to return above Day[]
 * 		public static Day[] values() {
 * 			return VALUES.clone();
 * 		}
 * 
 * 6. Static method to get individual Day based on name - 
 * 		public static Day valueOf(String name) {
 * 			for(Day day: VALUES) {
 * 				if(day.name().equals(name)) {
 * 					return day;
 * 				}
 * 			}
 * 		}
 * 
 */

public enum Day {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}
