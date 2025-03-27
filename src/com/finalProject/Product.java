package com.finalProject;


public class Product {
	private String brand;
	private String model;
	private double price;
	private String photo;
	private String description;
	private String category;
	private String selectedColor;
	private String selectedMemory;
	private String selectedSize;

	public Product(String brand, String model, double price, String photo, String description, String category) {
		super();
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.photo = photo;
		this.description = description;
		this.category = category;
	}
	
	public Product(String brand, String model, double price, String category) {
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.category = category;
	}
	
	public Product(String brand, String model, double price, String category, String selectedColor, String selectedMemory, String selectedSize) {
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.category = category;
		this.selectedColor = selectedColor;
		this.selectedMemory = selectedMemory;
		this.selectedSize = selectedSize;
	}
	public Product() {
		super();
	}

	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getSelectedFeature1() {
		return selectedColor;
	}

	public void setSelectedColor(String selectedColor) {
		this.selectedColor = selectedColor;
	}

	public String getSelectedFeature2() {
		return selectedMemory;
	}

	public void setSelectedMemory(String selectedMemory) {
		this.selectedMemory = selectedMemory;
	}

	public String getSelectedFeature3() {
		return selectedSize;
	}

	public void setSelectedSize(String selectedSize) {
		this.selectedSize = selectedSize;
	}
	
	
}
