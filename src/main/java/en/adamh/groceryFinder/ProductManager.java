package en.adamh.groceryFinder;

import org.json.simple.JSONObject;

public class ProductManager {
	public JSONObject getProductJSONFromPage(String page) {
		ProductFinder productFinder = new ProductFinder();
		Products products = productFinder.findProductsOnPage(page);
		ProductJSONWriter jsonWriter = new ProductJSONWriter();
		JSONObject productsJSON = jsonWriter.writeProductsToJSON(products);

		return productsJSON;
	}
}
