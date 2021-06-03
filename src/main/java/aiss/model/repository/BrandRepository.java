package aiss.model.repository;

import java.util.Collection;

import aiss.model.Brand;
import aiss.model.Phone;

public interface BrandRepository {
	
	
	// Phones
	public void addPhone(Phone p);
	public Collection<Phone> getAllPhones();
	public Phone getPhone(String phoneId);
	public void updatePhone(Phone p);
	public void deletePhone(String phoneId);
	
	// Brands
	public void addBrand(Brand b);
	public Collection<Brand> getAllBrands();
	public Brand getBrand(String brandId);
	public void updateBrand(Brand b);
	public void deleteBrand(String brandId);
	public Collection<Phone> getAll(String brandId);
	public void addPhone(String brandId, String phoneId);
	public void removePhone(String brandId, String phoneId); 

	
	
	
	

}
