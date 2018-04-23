package en.adamh.groceryFinder;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class ProductFinder {

	public Products findProductsOnPage(String page) {
		Products products = new Products();
		try {
			products = tryToFindProductsOnPage(page);
		} catch (IOException e) {
			System.out.println("Unable to connect to page " + page);
			e.printStackTrace();
		}

		return products;
	}
	
	private Products tryToFindProductsOnPage(String page) throws IOException {
		Products products = new Products();
		Document doc = Jsoup.connect(page).get();
		Elements productContainers = doc.getElementsByClass("product");
		for (Element productContainer : productContainers) {
			IProduct product = getProductFromContainer(productContainer);
			products.add(product);
		}
		
		return products;
	}

	private IProduct getProductFromContainer(Element productContainer) {
		Element productName = productContainer.getElementsByClass("productNameAndPromotions").get(0);
		Element titleLink = productName.getElementsByTag("a").get(0);
		String title = getTitleFromLink(titleLink);
		IProduct product = ProductFactory.getProduct(title);
		double unitPrice = getUnitPriceFromContainer(productContainer);

		product.setUnitPrice(unitPrice);
		product = getAdditionalInformation(product, titleLink);

		return product;
	}

	private String getTitleFromLink(Element titleLink) {
		Element titleLinkElement = titleLink.getAllElements().get(0);
		String title = "";
		
		for (Node node : titleLinkElement.childNodes()) {
			if (!(node instanceof TextNode)) {
				continue;
			}
			title = ((TextNode) node).getWholeText().trim();
			break;
		}

		return title;
	}

	private double getUnitPriceFromContainer(Element productContainer) {
		Element unitPriceContainer = productContainer.getElementsByClass("pricePerUnit").get(0);
		TextNode unitPriceNode = (TextNode) unitPriceContainer.childNodes().get(0);
		String unitPriceString = unitPriceNode.getWholeText();
		unitPriceString = unitPriceString.replace("£", "");
		
		return Double.parseDouble(unitPriceString);
	}

	private IProduct getAdditionalInformation(IProduct product, Element titleLink) {
		try {
			product = tryToGetAdditionalInformation(product, titleLink);
		} catch (IOException e) {
			System.out.println("Unable to find additional information for " + product.getTitle());
			e.printStackTrace();
		} 

		return product;
	}
	
	private IProduct tryToGetAdditionalInformation(IProduct product, Element titleLink) throws IOException {
		String relativePath = titleLink.attr("href");
		String page = titleLink.baseUri();
		URL originalURL = new URL(page);
		URL fullPath = new URL(originalURL, relativePath);
		Document moreInfoPage = Jsoup.connect(fullPath.toString()).get();
		String description = getDescription(moreInfoPage);
		int calories = getCalories(moreInfoPage);

		product.setDescription(description);
		product.setKCalPer100g(calories);
		
		return product;
	}

	private String getDescription(Element moreInfoPage) {
		Element descriptionContainer = moreInfoPage.getElementsByClass("productText").get(0);
		Element descriptionParagraph = descriptionContainer.getElementsByTag("p").get(0);
		TextNode descriptionNode = (TextNode) descriptionParagraph.childNodes().get(0);

		return descriptionNode.getWholeText();
	}

	private int getCalories(Element moreInfoPage) {
		int calories = 0;
		Elements nutritionTables = moreInfoPage.getElementsByClass("nutritionTable");
		if (!nutritionTables.isEmpty()) {
			Element nutritionTable = moreInfoPage.getElementsByClass("nutritionTable").get(0);
			Element caloriesRow = nutritionTable.getElementsByTag("tr").get(2);
			Element caloriesCell = caloriesRow.getElementsByTag("td").get(0);
			String caloriesString = caloriesCell.html();
			caloriesString = caloriesString.replace("kcal", "");
			calories = Integer.parseInt(caloriesString);
		}

		return calories;
	}
}
