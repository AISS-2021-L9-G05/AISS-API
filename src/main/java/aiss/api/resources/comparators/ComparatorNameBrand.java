package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Brand;

public class ComparatorNameBrand implements Comparator<Brand> {
	
	@Override
	public int compare(Brand b1, Brand b2) {
		return b1.getName().compareTo(b2.getName());
	}
}
