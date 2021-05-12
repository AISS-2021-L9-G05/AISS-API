package aiss.model;

public class Phone {

	private String id;
	private String name;
	private String price;
	private String releaseDate;
	private String size;
	private String resolution;

	public Phone() {
	}

	public Phone(String name, String brand, String releaseDate, String size, String resolution) {
		this.name = name;
		this.price = brand;
		this.releaseDate = releaseDate;
		this.size = size;
		this.resolution = resolution;
	}
	
	public Phone(String id, String name, String brand, String releaseDate, String size, String resolution) {
		this.id=id;
		this.name = name;
		this.price = brand;
		this.releaseDate = releaseDate;
		this.size = size;
		this.resolution = resolution;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

}
