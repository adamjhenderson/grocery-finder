package en.adamh.groceryFinder;

public class ProductFactory {
	public static IProduct getProduct(String title) {
		if (title == null) {
			return null;
		}

		return new Product(title);
	}
}