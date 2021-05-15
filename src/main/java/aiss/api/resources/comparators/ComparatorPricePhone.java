package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Phone;

public class ComparatorPricePhone implements Comparator<Phone> {
	@Override
	public int compare(Phone p1, Phone p2) {
		return p1.getPrice().compareTo(p2.getPrice());
	}
}
