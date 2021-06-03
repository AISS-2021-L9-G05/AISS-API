package aiss.model;

import java.time.LocalDate;

public class Phone {

	private String id;
	private String name;
	private Double price;
	private LocalDate releaseDate;
	private String size;
	private String resolution;

	public Phone() {
	}

	public Phone(String name, Double price, LocalDate releaseDate, String size, String resolution) {
		this.name = name;
		this.price = price;
		this.releaseDate = releaseDate;
		this.size = size;
		this.resolution = resolution;
	}
	
	public Phone(String id, String name, Double price, LocalDate releaseDate, String size, String resolution) {
		this.id=id;
		this.name = name;
		this.price = price;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
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
