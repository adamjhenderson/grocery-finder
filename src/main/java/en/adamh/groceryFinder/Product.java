package en.adamh.groceryFinder;

import org.json.simple.JSONObject;

public class Product implements IProduct {

	private String title;
	private int kCalPer100g;
	private double unitPrice;
	private String description;

	public Product(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	public int getKCalPer100g() {
		return this.kCalPer100g;
	}

	public void setKCalPer100g(int kCalPer100g) {
		this.kCalPer100g = kCalPer100g;

	}

	public double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;

	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;

	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("title", this.getTitle());
		if (this.getKCalPer100g() > 0) {
			json.put("kcal_per_100g", this.getKCalPer100g());
		}
		json.put("unit_price", this.getUnitPrice());
		json.put("description", this.getDescription());
		return json;
	}

}
