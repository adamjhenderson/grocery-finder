package en.adamh.groceryFinder;

import java.util.ArrayList;

public class Products extends ArrayList<IProduct> {
	public double getTotal() {
		double total = 0;
		for (IProduct product : this) {
			total += product.getUnitPrice();
		}
		return total;
	}
}
