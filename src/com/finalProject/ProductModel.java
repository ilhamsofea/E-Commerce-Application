package com.finalProject;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {
	public List<Product> findAll(){
		List<Product> mobileProducts = new ArrayList<Product>();
		String category = "mobile";
		mobileProducts.add(new Product("Sony", "Xperia 1V", 1000.0 , "xperia.jpg", "The Sony Xperia 1V is a flagship smartphone with a stunning 6.5-inch OLED display, advanced camera capabilities, and a sleek design, catering to multimedia and photography enthusiasts.", "Mobile"));
		mobileProducts.add(new Product("Apple", "iPhone 12", 699.0 , "ip12.jpg", "The iPhone 12 offers a seamless user experience with its sleek design, powerful A14 Bionic chip, Super Retina XDR display, and advanced dual-camera system.", "Mobile"));
		mobileProducts.add(new Product("Samsung", "Galaxy S21", 799.0 , "galaxys21.jpg", "The Galaxy S21 is a high-end smartphone with a dynamic AMOLED display, fast performance, versatile triple-camera system, 5G connectivity, and a sleek design.", "Mobile"));
		mobileProducts.add(new Product("Google", "Pixel 5", 699.0 , "pixel5.jpeg", "The Pixel 5 focuses on exceptional camera capabilities, seamless integration with Google services, and a user-friendly experience in a compact and stylish package.", "Mobile"));
		mobileProducts.add(new Product("OnePlus", "8 Pro", 899.0 , "oneplus8pro.jpg", "The OnePlus 8 Pro is a flagship smartphone known for its powerful performance, high refresh rate display, versatile camera system, and sleek design, offering a premium Android experience.", "Mobile"));
		mobileProducts.add(new Product("Xiaomi", "Mi 11", 699.0 , "mi11.jpg", "The Xiaomi Mi 11 is a flagship smartphone known for its powerful performance, stunning display, and impressive camera capabilities, making it an attractive choice for tech enthusiasts seeking a premium Android device.", "Mobile"));
		mobileProducts.add(new Product("Oppo", "Find X3 Pro", 1199.0 , "findx3pro.png", "The Oppo Find X3 Pro is a premium smartphone that combines sleek design with top-of-the-line features, including a high-quality display, powerful processor, and a versatile quad-camera system.", "Mobile"));
		return mobileProducts;
	}
	
	public List<Product> findAll2(){
		List<Product> tabProducts = new ArrayList<Product>();
		tabProducts.add(new Product("Apple", "iPad Pro", 799.0 , "ipadpro11.png", "The iPad Pro is a high-end tablet known for its powerful performance, stunning Retina display, and support for the Apple Pencil and Magic Keyboard. ", "Tab"));
		tabProducts.add(new Product("Samsung", "Galaxy Tab S7", 649.0 , "galaxytabs7.jpg", "The Galaxy Tab S7 is a premium Android tablet with a high-resolution display, powerful processor, and S Pen support.", "Tab"));
		tabProducts.add(new Product("Microsoft", "Surface Pro 7", 749.0 , "surfacepro7.jpg", "The Surface Pro 7 is a versatile tablet that offers a sharp display, powerful performance, and optional keyboard attachment. ","Tab"));
		tabProducts.add(new Product("Lenovo", "Tab P11 Pro", 499.0 , "p11pro.jpg", "The Tab P11 Pro is a feature-rich Android tablet with a sleek design, sharp display, and optional keyboard cover.","Tab"));
		tabProducts.add(new Product("Huawei", "MatePad Pro", 549.0 , "huaweimatepadpro.png", "The MatePad Pro is a high-quality tablet that offers a sleek design, vibrant display, and a range of features.","Tab"));
		tabProducts.add(new Product("Amazon", "Fire HD 10", 149.0 , "firehd10.jpg", "The Amazon Fire HD 10 is a budget-friendly tablet that offers a great value for its price. It features a 10.1-inch display, fast performance, and access to a wide range of Amazon services.","Tab"));
		tabProducts.add(new Product("Google", "Pixel Slate", 799.0 , "pixelslate.jpg", "The Google Pixel Slate is a premium tablet designed for productivity and entertainment. It features a high-resolution display, powerful performance, and support for the Pixelbook Pen.","Tab"));
		return tabProducts;
	}
	
	public List<Product> findAll3(){
		List<Product> laptopProducts = new ArrayList<Product>();
		laptopProducts.add(new Product("Dell", "XPS 13", 999.0 , "dellxps13.jpg", "The Dell XPS 13 is a highly regarded ultrabook known for its sleek design, excellent display, and powerful performance. It features an InfinityEdge display, Intel Core processors, and ample RAM and storage options.", "Laptop"));
		laptopProducts.add(new Product("HP", "Spectre x360", 1099.0 , "spectrex360.png", "The HP Spectre x360 is a convertible laptop with a 360-degree hinge, allowing it to be used as a laptop or tablet. It offers a premium build quality, vibrant display, and strong performance.", "Laptop"));
		laptopProducts.add(new Product("Lenovo", "ThinkPad X1 Carbon", 1199.0 , "thinkpadx1.jpg", "The Lenovo ThinkPad X1 Carbon is a lightweight and durable business laptop with excellent keyboard and trackpad. It offers a sharp display, impressive battery life, and robust security features.","Laptop"));
		laptopProducts.add(new Product("Apple", "MacBook Pro:", 1299.0 , "macbookpro.jpg", "The MacBook Pro is Apple's flagship laptop, known for its sleek design, powerful performance, and high-resolution Retina display. It offers a seamless user experience, long battery life, and macOS operating system.","Laptop"));
		laptopProducts.add(new Product("Asus", "ROG Zephyrus G14", 1099.0 , "rog.png", "The Asus ROG Zephyrus G14 is a gaming laptop known for its compact size and powerful performance. It features an AMD Ryzen processor, dedicated NVIDIA graphics, and a high-refresh-rate display.","Laptop"));
		laptopProducts.add(new Product("Acer", "Swift 3", 599.0 , "swift3.jpg", "The Acer Swift 3 is a budget-friendly laptop that offers good performance and portability. It features an aluminum chassis, a decent display, and a variety of configuration options. ","Laptop"));
		laptopProducts.add(new Product("Microsoft", "Surface Laptop 4", 999.0 , "surface4.jpg", "The Microsoft Surface Laptop 4 is a premium laptop with a sleek design and excellent build quality. It offers a sharp display, comfortable keyboard, and a range of processor options.","Laptop"));
		return laptopProducts;
	}
	
	public String findDescription(String brand, String model) {
        // Iterate over the products and find the matching brand and model
        for (Product product : findAll()) {
            if (product.getBrand().equals(brand) && product.getModel().equals(model)) {
                return product.getDescription();
            }
        }
        for (Product product : findAll2()) {
            if (product.getBrand().equals(brand) && product.getModel().equals(model)) {
                return product.getDescription();
            }
        }
        for (Product product : findAll3()) {
            if (product.getBrand().equals(brand) && product.getModel().equals(model)) {
                return product.getDescription();
            }
        }
        return null; // Return null if no description is found
    }
}
