package uk.co.benskin.graphql_spring_boot_tutorial.entities;
 
import uk.co.benskin.graphql_spring_boot_tutorial.enums.Animal;
 
public class Pet {
    private long id;

    private String name;

    private Animal type;

    private int age;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Animal getType() {
		return type;
	}

	public void setType(Animal type) {
		this.type = type;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}