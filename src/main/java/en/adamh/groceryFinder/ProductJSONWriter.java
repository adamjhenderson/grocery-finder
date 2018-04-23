package en.adamh.groceryFinder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ProductJSONWriter {
	JSONObject writeProductsToJSON(Products products) {
		JSONObject output = new JSONObject();
		JSONArray results = new JSONArray();

		for (IProduct product : products) {
			results.add(product.toJSON());
		}

		output.put("results", results);
		output.put("total", products.getTotal());

		return output;
	}
}
