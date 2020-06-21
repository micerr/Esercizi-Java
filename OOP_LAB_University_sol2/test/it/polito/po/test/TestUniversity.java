package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import university.University;
import university.UniversityException;

public class TestUniversity {
	
	static final String universityName = "Politecnico di Torino";
	private final static int MISSING=-1; // predefined return values in initial implementation
	private University poli;
	
	@Before
	public void setUp() throws Exception { // FIXTURE, 
						 // the name 'setUp' is convention deriving from JUnit 3
		poli = new University(universityName);
		poli.setRector("Guido", "Saracco");
	}

	@Test
	public void testNameRector(){
		String name = poli.getName();
		String rector = poli.getRector();
		
		assertNotNull("Missing university name",
				name);
		
		assertEquals("Wrong university name", // msg in case of failure
						universityName,		  // expected value
						name);	  // actual value
		
		assertNotNull("Missing rector name",rector);
		
		assertContained("Wrong rector name","Saracco",rector);
	}
	
	@Test
	public void testEnroll(){				
		int s1 = poli.enroll("Mario","Rossi");
		int s2 = poli.enroll("Francesca","Verdi");
		
		assertTrue("Missing student ID when enrolling", s1 != MISSING);

		assertEquals("Wrong id number for first enrolled student",10000,s1);
		assertEquals("Wrong id number for second enrolled student",10001,s2);		
	}

	@Test
	public void testStudents() throws UniversityException {				
		int s1 = poli.enroll("Vilfredo","Pareto");
		int s2 = poli.enroll("Galileo","Ferraris");
		int s3 = poli.enroll("Leo", "Da Vinci");
	
		String ss1 = poli.student(s1);
		assertNotNull("Missing student info", ss1);
		assertContained("Pareto",ss1);

		String ss2 = poli.student(s2);
		assertNotNull("Missing student info", ss2);
		assertContained("Galileo",ss2);

		String ss3 = poli.student(s3);
		assertNotNull("Missing student info", ss3);
		assertContained("Vinci",ss3);
	}

	@Test(expected=UniversityException.class)
	public void testStudentsErrati() throws UniversityException {
		poli.student(0);
	}
	
	// OPPURE
	
	@Test
	public void testStudentsErratiAlternativa() {
		try {
			poli.student(0);
			fail("Eccezione attesa ma non generata!");
		} catch (UniversityException e) {
			// OK!!! era attesa!!
			assertTrue(true);
		}
	}

	
	@Test(expected=UniversityException.class)
	public void testStudentsErrati2() throws UniversityException {
		int s1 = poli.enroll("Vilfredo","Pareto");
		poli.student(s1);
		poli.student(s1+1);
	}
	

	@Test
	public void testCourseActivation(){
		int macro = poli.activate("Macro Economics", "Paul Krugman");
		int oop = poli.activate("Object Oriented Programming", "James Gosling");
		
		assertTrue("Missing course ID when activating", macro != MISSING);

		assertEquals("Wrong id number for first activated course",10,macro);
		assertEquals("Wrong id number for second activated course",11,oop);		
	}
	
	@Test
	public void testCourses(){
		int macro = poli.activate("Macro Economics", "Paul Krugman");
		int oop = poli.activate("Object Oriented Programming", "James Gosling");
		
		assertNotNull("Missing course description",poli.course(macro));
		assertContained("Wrong description of course", "Macro Economics", poli.course(macro));
		assertContained("Wrong description of course", "Oriented", poli.course(oop));
		assertContained("Wrong description of course", "James", poli.course(oop));
	}
	
	@Test
	public void testAttendees() throws UniversityException {
		poli.enroll("Mario","Rossi");
		poli.enroll("Francesca","Verdi");
		
		poli.activate("Macro Economics", "Paul Krugman");
		poli.activate("Object Oriented Programming", "James Gosling");
		
		poli.register(10000, 10);
		poli.register(10001, 10);
		poli.register(10001, 11);
		
		String attendees = poli.listAttendees(10);
		assertNotNull("Missing attendees list",attendees);
		assertEquals("Wrong number of attendees for course 10",2,countLines(attendees));

		attendees = poli.listAttendees(11);
		assertEquals("Wrong number of attendees for course 11",1,countLines(attendees));
	}
	
	@Test
	public void testStudyPlan() throws UniversityException {
		poli.enroll("Mario","Rossi");
		poli.enroll("Francesca","Verdi");
		
		poli.activate("Macro Economics", "Paul Krugman");
		poli.activate("Object Oriented Programming", "James Gosling");
		
		poli.register(10000, 10);
		poli.register(10001, 10);
		poli.register(10001, 11);
		
		String plan = poli.studyPlan(10001);
		assertNotNull("Missing study plan",plan);
		assertEquals("Wrong number of courses for student 10001",2,countLines(plan));

		plan = poli.studyPlan(10000);
		assertEquals("Wrong number of courses for student 10000",1,countLines(plan));
	}
	
	
	// -------------------- Utility methods (o Helper methods) ------------------------------------------
	
	// specialized assert methods, make the test easier to read
	
	/**
	 * Assert that a sub string is contained in the actual string
	 * 
	 * @param expectedSubStr the expected sub string
	 * @param actualStr      the actual string
	 */
	private static void assertContained(String expectedSubStr, String actualStr) {
		assertContained(null,expectedSubStr,actualStr);
	}
	
	
	/**
	 * Assert that a sub string is contained in the actual string
	 * 
	 * @param expectedSubStr the expected sub string
	 * @param actualStr      the actual string
	 */
	private static void assertContained(String msg, String expectedSubStr, String actualStr) {
		assertTrue((msg==null?"":msg+": ") + "expected sub string [" + expectedSubStr + "] is not contained in ["+actualStr+"]",
					(actualStr==null?false:actualStr.contains(expectedSubStr)));
	}
	
	// other support methods
	
	private static int countLines(String s) {
		if(s==null) return 0;
		return 1 + s.trim().replaceAll("[^\n]", "").length();
	}
}