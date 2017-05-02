package com.p.jackson.persons;

public class Person {
	
	Person() {}
	
	Person(Person person) {
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.middleName = person.getMiddleName();
		this.age = person.getAge();
	}
	
  	private String firstName;
  	private String lastName;
  	private String middleName;
  	private int age;
	
  	public String getFirstName() {
		return this.firstName;
	}
  	public String getLastName() {
		return this.lastName;
	}
  	public String getMiddleName() {
		return this.middleName;
	}
  	public int getAge() {
		return this.age;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public void setAge(int age) {
		this.age = age;
	}
}