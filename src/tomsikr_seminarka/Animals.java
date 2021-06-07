package tomsikr_seminarka;

public class Animals {
	int id;
	String species;
	String category;
	String name;
	String age;
	String health;
	String weight;
	String sex;
	String notes;
	
	public Animals(int id, String species, String category, String name, String age, String health, String weight, String sex, String notes) {
		this.id = id;
		this.species = species;
		this.category = category;
		this.name = name;
		this.age = age;
		this.health = health;
		this.weight = weight;
		this.sex = sex;
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
