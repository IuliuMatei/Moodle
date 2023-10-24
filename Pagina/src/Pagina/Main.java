package Pagina;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Aici am facut clasa Persoana care o sa fie mostenita de Student si Profesor

abstract class Person{
	protected String name;
	protected String password;
	
	
	//Setters
	public void set_Name(String name) {
		this.name = name;	
	}
	
	public void set_Password(String password) {
		this.password = password;
	}
	
	//Getters
	public String get_Name() {
		return name;
	}
	
	public String get_Password() {
		return password;
	}
	
}
 
class Test{
	public String Lesson;
	public String Name;
	public String Question;
	public String Answer;
		
	public Test(String Lesson,String Name, String Question, String Answer) {
		this.Lesson = Lesson;
		this.Name = Name;
		this.Question = Question;
		this.Answer = Answer;
	}
	
	public Test() {
		Lesson = "";
		Question = "";
		Answer = "";
		Name = "";
	}
}

class Student extends Person{
	
	SortedMap<String, Integer> map;
	ArrayList<Test> tests;
	
	//Constructor cu parametru
	public Student(String name, String password) {
		this.name = name;
		this.password = password;
		//this.year = year;
		map = new TreeMap<>();
		tests = new ArrayList<Test>();
	}
	
	//Functie care ajuta la copierea unui obiect
	public Student(Student s1) {
		this.name = s1.name;
		//this.year = s1.year;
		this.password = s1.password;
		this.map = s1.map;
		this.tests = s1.tests;
	}

	public void take_Test(Test t, String Answer) {
		t.Answer = Answer;
		t.Name = name;
	}
}

class Lesson{
	
	private String lesson;//acesta este numele
	private int year;
	public ArrayList<Student> stud1;
	Test t;
	
	//Constructor cu parametru
	public Lesson(String lesson, int year) {
		this.lesson = lesson;
		this.year = year;
		stud1 = new ArrayList<Student>();
		t = new Test();
	}
	
	public Lesson(Lesson l1) {
		this.lesson = l1.get_Lesson();
		//this.grade = l1.get_Grade();
		this.year = l1.get_Year();
		this.stud1 = l1.get_Studs();
		this.t = l1.t;
	}
	
	//Setters
	public void set_Lesson(String lesson) {
		this.lesson = lesson;
	}
	
	public void set_Year(int year) {
		this.year = year;
	}
	
	/*public void set_Grade(int grade) {
		this.grade = grade;
	}*/
	
	//Getters
	public String get_Lesson() {
		return lesson;
	}
	
	public ArrayList<Student> get_Studs(){
		return stud1;
	}
	
	public int get_Year() {
		return year;
	}
}

class Professor extends Person{
	
	public Lesson l;
	ArrayList<Test> tests;
	
	//Constructor cu parametru
	public Professor(String name, String password, Lesson l1) {
		this.name = name;
		this.password = password;
		l = new Lesson(l1);
		tests = new ArrayList<Test>();
	}
	
	//Functie care ajuta la copierea unui obiect
	public Professor(Professor p1) {
		this.name = p1.name;
		this.password = p1.password;
		this.l = p1.l;
		this.tests = p1.tests;
	}
	
	public void change_grade(ArrayList<Student> stud, String name, int grade) {
		for (int i = 0; i < stud.size(); i++) {
			if (name.equals(stud.get(i).get_Name())) {
				for (String key : stud.get(i).map.keySet()) {
					if (key.equals(l.get_Lesson())) {
						stud.get(i).map.put(key, grade);
					}
				}
			}
		}
	}
	
	public void create_Test(String question) {
		l.t.Lesson = l.get_Lesson(); 
		l.t.Question = question;
		l.t.Answer = "";
		for(int i = 0; i < l.stud1.size(); i++) {
			l.stud1.get(i).tests.add(l.t);
		}
	}
	
	public void see_Answers() {
		for (Test i : tests) {
			System.out.println("Student: " + i.Name + "\n" + "Answer: " + i.Answer + "\n\n");
		}
		
		tests.clear();
	}
}



public class Main {
	
	public static void create_Curriculum(ArrayList<Lesson> les) {
		String name1, name2;
		int year;
		name1 = "Math";
		name2 = "Chemistry";
		year = 1;
		Lesson l1 = new Lesson(name1, year);
		Lesson l2 = new Lesson(name2, year);
		les.add(l1);
		les.add(l2);
	}
	
	
	public static void main(String args[]) {
		ArrayList<Student> stud = new ArrayList<Student>();
		ArrayList<Professor> prof = new  ArrayList<Professor>();
		ArrayList<Lesson> les = new ArrayList<Lesson>();
		create_Curriculum(les);
		int choice1, choice2, choice3, correctness = 0, year, stop = 0, stop1 = 0, grade;
		String name, password, lesson, answer;
		Student s1 = null;
		Professor p1 = null;
		Lesson l = null;
		Scanner scn = new Scanner(System.in);
		BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
		do {
			System.out.println("----------WELCOME TO MOODLE----------\n\n");
			System.out.println("1. Login");
			System.out.println("2. Sign up as a Student");
			System.out.println("3. Sign up as a Professor");
			System.out.println("4. Exit\n");
			System.out.println("Your choice: ");
			choice1 = scn.nextInt(); 
			if (choice1 == 1) {
				System.out.println("\n\nUsername: ");
				name = scn.next();
				System.out.println("Password: ");
				password = scn.next();
				for (Student i : stud) {
					if (name.equals(i.get_Name()) && password.equals(i.get_Password())) {
						correctness = 1;
						s1 = new Student(i);
					}
				}
				
				for (Professor i : prof) {
					if (name.equals(i.get_Name()) && password.equals(i.get_Password())) {
						correctness = 2;
						p1 = new Professor(i);
					}
				}
				
				if (correctness == 1) {
					do {
						System.out.println("\n\n----------WELCOME, " + s1.get_Name() + "----------\n\n");
						System.out.println("1. See grades");
						System.out.println("2. Take tests");
						System.out.println("3. Exit");
						System.out.println("\nYour choice: ");
						choice2 = scn.nextInt();
						System.out.println("\n\n");
						if (choice2 == 1) {
							System.out.println("----------GRADES----------\n\n");
							for (String key : s1.map.keySet()) {
								System.out.println("Lesson: " + key + "\n" + "Grade: " + s1.map.get(key) + "\n\n");
							}
							do {
								System.out.println("Press 1 to go back");
								stop = scn.nextInt();
							}while(stop == 0);
						}
						
						if (choice2 == 2) {
							System.out.println("----------TESTS----------\n\n");
							if (s1.tests.size() == 0) {
								System.out.println("You have no tests to take.");
							}
							else {
								try {
									System.out.println("You want to take the test for: ");
									lesson = scn.next();
									System.out.println("\n\n");
									for (Test i : s1.tests) {
										if (i.Lesson.equals(lesson)) {
											System.out.println("The question is: ");
											System.out.println(i.Question);
											System.out.println("Your answer: ");
											answer = input.readLine();
											System.out.println("\n");
											System.out.println("Submitted!\n\n");
											Test t = new Test(lesson, s1.get_Name(), i.Question, answer);
											for (int j = 0; j < prof.size(); j++) {
												if (prof.get(j).l.get_Lesson().equals(lesson)) {
													prof.get(j).tests.add(t);
												}
											}
										}
									}
									s1.tests.clear();
									do {
										System.out.println("Press 1 to go back");
										stop = scn.nextInt();
									}while(stop == 0);
								}catch(Exception e) {
									System.out.println("Something went wrong!");
								}
							}
						}
					}while(choice2 != 3);
				}
				else if (correctness == 2){
					do {
						System.out.println("\n\n----------WELCOME, " + p1.get_Name() + "----------\n\n");
						System.out.println("1. Grade students.");
						System.out.println("2. Create test");
						System.out.println("3. See tests");
						System.out.println("4. Exit");
						System.out.println("\nYour choice: ");
						choice3 = scn.nextInt();
						System.out.println("\n\n");
						if (choice3 == 1) {
							System.out.println("----------STUDENTS----------");
							for (int i = 0; i < l.stud1.size(); i++) {
								System.out.println(i + 1 + ". " + p1.l.stud1.get(i).get_Name());
							}
							System.out.println("\n\nStudent: ");
							name = scn.next();
							System.out.println("Grade: ");
							grade = scn.nextInt();
							p1.change_grade(stud, name, grade);
							System.out.println("Grade changed succesfully!\n\n");
							do {
								System.out.println("Press 1 to go back.");
								stop = scn.nextInt();
							}while(stop == 0);
						}
						
						if (choice3 == 2) {
							System.out.println("----------CREATE TEST----------\n\n");
							String question;
							System.out.println("Enter the question: ");
							try {
								question = input.readLine();
								p1.create_Test(question);
								System.out.println("Test created succsefully!\n\n");
								do {
									System.out.println("Press 1 to go back.");
									stop = scn.nextInt();
								}while(stop == 0);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						if (choice3 == 3) {
							System.out.println("----------ANSWERS----------\n\n");
							if (p1.tests.size() != 0) {
								p1.see_Answers();
							}
							else {
								System.out.println("There are no tests!\n");
							}
							do {
								System.out.println("Press 1 to go back.");
								stop = scn.nextInt();
							}while(stop == 0);
						}
					}while(choice3 != 4);
				}
				else {
					System.out.println("Acces denied");
				}
				
			}
			if (choice1 == 2) {
				System.out.println("\n\nUsername: ");
				name = scn.next();
				System.out.println("Password: ");
				password = scn.next();
				System.out.println("Signed up succesfully!\n\n");
				Student s = new Student(name, password);
				
				//Adaug la fiecare curs studentul
				for (int i = 0; i < les.size(); i++) {
					s.map.put(les.get(i).get_Lesson(), 0);
					les.get(i).stud1.add(s);
				}
				stud.add(s);
			}
			if (choice1 == 3) {
				System.out.println("\n\nUsername: ");
				name = scn.next();
				System.out.println("Password: ");
				password = scn.next();
				System.out.println("Lesson you teach: ");
				lesson = scn.next();
				System.out.println("Signed up succesfully!\n\n");
				for (int i = 0; i < les.size(); i++) {
					if (lesson.equals(les.get(i).get_Lesson())) {
						l = les.get(i);//Prefer sa arate ambii spre acelasi compartiment pentru ca totusi sunt la fel, cand se schimba unul se schimba si celalalt
					}
				}
				Professor p = new Professor(name, password, l);
				prof.add(p);
			}
		}while(choice1 != 4);
		
		scn.close();
	
	}
} 