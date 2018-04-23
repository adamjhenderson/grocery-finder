package en.adamh.groceryFinder;

import org.json.simple.JSONObject;

public class GroceryFinderMain {
	public static void main(String[] args) {
		String page = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
		ProductManager manager = new ProductManager();
		JSONObject productsJSON = manager.getProductJSONFromPage(page);
		System.out.println(productsJSON.toJSONString());
	}
}
