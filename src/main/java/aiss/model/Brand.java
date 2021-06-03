package aiss.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Brand {

	private String id;
	private String name;
	private LocalDate foundationDate;
	private String phonesSold;
	private List<Phone> phones;
	
	public Brand() {}
	
	public Brand(String name) {
		this.name = name;
	}
	
	protected void setPhones(List<Phone> phones) {
		this.phones = phones;
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

	public LocalDate getFoundationDate() {
		return foundationDate;
	}

	public void setFoundationDate(LocalDate foundationDate) {
		this.foundationDate = foundationDate;
	}
	
	public String getPhonesSold() {
		return this.phonesSold;
	}

	public void setPhonesSold(String phonesSold) {
		this.phonesSold = phonesSold;
	}
	
	public List<Phone> getPhones() {
		return phones;
	}
	
	public Phone getPhone(String id) {
		if (phones==null)
			return null;
		
		Phone phone =null;
		for(Phone p: phones)
			if (p.getId().equals(id))
			{
				phone=p;
				break;
			}
		
		return phone;
	}
	
	public void addPhone(Phone p) {
		if (phones==null)
			phones = new ArrayList<Phone>();
		phones.add(p);
	}
	
	public void deletePhone(Phone p) {
		phones.remove(p);
	}
	
	public void deletePhone(String id) {
		Phone s = getPhone(id);
		if (s!=null)
			phones.remove(s);
	}

}
