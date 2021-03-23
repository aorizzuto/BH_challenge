package bh.challenge;

public class Marca {

		private Double price = 0.0;
		private int counter = 0;
		private String name="";
		
		public Marca(String marca, Double price) {
			this.name = marca;
			this.price = price;
			this.counter = 1;
		}
		
		public void increaseCounter() {
			this.counter++;
		}
		
		public void increasePrice(Double price) {
			this.price+=price;
		}
		
		public Double getPrice() {
			return this.price;
		}

		public void setName(String name) {
			this.name=name;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getCounter() {
			return this.counter;
		}

}
