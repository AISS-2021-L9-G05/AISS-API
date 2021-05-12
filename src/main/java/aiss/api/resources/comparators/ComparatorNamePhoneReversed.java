package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Phone;

public class ComparatorNamePhoneReversed implements Comparator<Phone> {
	@Override
	public int compare(Phone p1, Phone p2) {
		return p2.getName().compareTo(p1.getName());
	}
}
