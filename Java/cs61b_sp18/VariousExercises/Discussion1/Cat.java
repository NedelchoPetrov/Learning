public class Cat extends Animal{
	


	public Cat(String name, int age){
		super(name, age);
		this.noise = "Meow";
	}


	@Override
	public void greet(){
		System.out.println("Cat " + name + " says " + makeNoise()); 
	}

	public static void main(String[] args){
		Cat cat = new Cat("Blecky", 3);
		Cat cat2 = new Cat("Whity", 5);

		cat.greet();
		cat2.greet();
	}
}