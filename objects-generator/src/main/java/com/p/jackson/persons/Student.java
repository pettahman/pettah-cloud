package com.p.jackson.persons;

public class Student {
	
	Student() {}
	
	Student(Student student) {
		this.firstName = student.getFirstName();
		this.lastName = student.getLastName();
		this.age = student.getAge();
		this.year = student.getYear();
	}
	
  	private String firstName;
  	private String lastName;
  	private int age;
  	private String year;
	
  	public String getFirstName() {
		return this.firstName;
	}
  	public String getLastName() {
		return this.lastName;
	}
  	public int getAge() {
		return this.age;
	}
  	public String getYear() {
		return this.year;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setYear(String year) {
		this.year = year;
	}
}