package aiss.model.repository;

import java.util.Collection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import aiss.model.Brand;
import aiss.model.Phone;


public class MapBrandRepository implements BrandRepository{

	Map<String, Brand> brandMap;
	Map<String, Phone> phoneMap;
	private static MapBrandRepository instance=null;
	private int indexBrand=0;			// Index to create brands and phones' identifiers.
	private int indexPhone=0;
	
	
	public static MapBrandRepository getInstance() {
		
		if (instance==null) {
			instance = new MapBrandRepository();
			instance.init();
		}
		
		return instance;
	}
	
	public void init() {
		brandMap = new HashMap<String,Brand>();
		phoneMap = new HashMap<String,Phone>();
	   
		// Create phones
		Phone phone1=new Phone();
		phone1.setName("Samsung Galaxy A52");
		phone1.setPrice(349.90);
		phone1.setReleaseDate(LocalDate.of(2021, 3, 17));
		phone1.setSize("159,9 x 75,1 x 8,4 mm");
		phone1.setResolution("2.400 x 1.080 px");
		addPhone(phone1);
		
		Phone phone2=new Phone();
		phone2.setName("Samsung Galaxy S21 ultra 5G");
		phone2.setPrice(1040.0);
		phone2.setReleaseDate(LocalDate.of(2021, 1, 29));
		phone2.setSize("165,1 x 75,6 x 8,9 mm");
		phone2.setResolution("3200 x 1440");
		addPhone(phone2);
	
		Phone phone3=new Phone();
		phone3.setName("Samsung Galaxy Note 20 ultra 5g");
		phone3.setPrice(949.90);
		phone3.setReleaseDate(LocalDate.of(2020, 8, 21));
		phone3.setSize("164,8 x 77,2 x 8,1 mm");
		phone3.setResolution("3088 x 1440");
		addPhone(phone3);
	
		Phone phone4=new Phone();
		phone4.setName("Iphone XR");
		phone4.setPrice(559.90);
		phone4.setReleaseDate(LocalDate.of(2018, 10, 26));
		phone4.setSize("150,9 x 75,7 x 8,3 mm");
		phone4.setResolution("1792 x 828");
		addPhone(phone4);
		
		Phone phone5=new Phone();
		phone5.setName("Iphone 12 Pro");
		phone5.setPrice(1159.0);
		phone5.setReleaseDate(LocalDate.of(2020, 10, 23));
		phone5.setSize("146,7 x 71,5 x 7,4 mm");
		phone5.setResolution("2532 x 1170");
		addPhone(phone5);
	
		Phone phone6=new Phone();
		phone6.setName("Iphone 11");
		phone6.setPrice(689.0);
		phone6.setReleaseDate(LocalDate.of(2019, 9, 12));
		phone6.setSize("150.9 x 75.7 x 8.3 mm");
		phone6.setResolution("1.792 x 828");
		addPhone(phone6);
	
	
		// Create brands
		Brand brand1=new Brand();
		brand1.setName("Samsung");
		brand1.setFoundationDate(LocalDate.of(1938, 3, 1));
		brand1.setPhonesSold("270 millones, Año 2020");
		addBrand(brand1);
	
		Brand brand2 = new Brand();
		brand2.setName("Apple");
		brand2.setFoundationDate(LocalDate.of(1976, 4, 1));
		brand2.setPhonesSold("199.8 millones, Año 2020");
		addBrand(brand2);
	
		// Add phones to brands
		addPhone(brand1.getId(), phone1.getId());
		addPhone(brand1.getId(), phone2.getId());
		addPhone(brand1.getId(), phone3.getId());
	   
		addPhone(brand2.getId(), phone4.getId());
		addPhone(brand2.getId(), phone5.getId());
		addPhone(brand2.getId(), phone6.getId());
	}
	
	// Brand related operations
	@Override
	public void addBrand(Brand b) {
		String id = "b" + indexBrand++;	
		b.setId(id);
		brandMap.put(id,b);
	}
	
	@Override
	public Collection<Brand> getAllBrands() {
			return brandMap.values();
	}

	@Override
	public Brand getBrand(String id) {
		return brandMap.get(id);
	}
	
	@Override
	public void updateBrand(Brand b) {
		brandMap.put(b.getId(),b);
	}

	@Override
	public void deleteBrand(String id) {	
		brandMap.remove(id);
	}
	

	@Override
	public void addPhone(String brandId, String phoneId) {
		Brand brand = getBrand(brandId);
		brand.addPhone(phoneMap.get(phoneId));
	}

	@Override
	public Collection<Phone> getAll(String brandId) {
		return getBrand(brandId).getPhones();
	}

	@Override
	public void removePhone(String brandId, String phoneId) {
		getBrand(brandId).deletePhone(phoneId);
	}

	
	// Phone related operations
	
	@Override
	public void addPhone(Phone p) {
		String id = "p" + indexPhone++;
		p.setId(id);
		phoneMap.put(id, p);
	}
	
	@Override
	public Collection<Phone> getAllPhones() {
			return phoneMap.values();
	}

	@Override
	public Phone getPhone(String phoneId) {
		return phoneMap.get(phoneId);
	}

	@Override
	public void updatePhone(Phone p) {
		Phone phone = phoneMap.get(p.getId());
		phone.setName(p.getName());
		phone.setPrice(p.getPrice());
		phone.setReleaseDate(p.getReleaseDate());
		phone.setSize(p.getSize());
		phone.setResolution(p.getResolution());
	}

	@Override
	public void deletePhone(String phoneId) {
		phoneMap.remove(phoneId);
	}
	
}
