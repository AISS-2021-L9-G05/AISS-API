package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Phone;

public class ComparatorPricePhoneReversed implements Comparator<Phone> {
	@Override
	public int compare(Phone p1, Phone p2) {
		return p2.getPrice().compareTo(p1.getPrice());
	}
}
