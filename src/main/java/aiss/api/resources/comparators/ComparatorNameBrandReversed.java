package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Brand;

public class ComparatorNameBrandReversed implements Comparator<Brand> {
	
	@Override
	public int compare(Brand b1, Brand b2) {
		return b2.getName().compareTo(b1.getName());
	}
}
