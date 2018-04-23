package en.adamh.groceryFinder;

import org.json.simple.JSONObject;

public interface IProduct {
	JSONObject toJSON();

	String getTitle();

	void setTitle(String title);
	
	int getKCalPer100g();
	
	void setKCalPer100g(int kCalPer100g);

	double getUnitPrice();
	
	void setUnitPrice(double unitPrice);
	
	String getDescription();
	
	void setDescription(String description);

	
}
