package bookstore.domain;

import java.sql.Date;

public class Book {
	
	private Integer id;
	private String author;
	
	private String title;
	private float price;
	

	private int salesAmount;
	private int storeNumber;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	public int getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}
	public void setStoreNumber(int storeNumber) {
		this.storeNumber = storeNumber;
	}
	
	public int getStoreNumber() {
		// TODO Auto-generated method stub
		return storeNumber;
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", author=" + author + ", title=" + title
				+ ", price=" + price + ", salesAmount=" + salesAmount + ", storeNumber=" + storeNumber+"]\n\n";
	}
	
		
}
