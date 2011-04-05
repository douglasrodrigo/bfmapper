package br.com.bfmapper;

import java.util.HashSet;
import java.util.Set;

public class MappingRules {
	
	private static Set<Converter> converters = new HashSet<Converter>();
	
	public static void addRule(Converter converter) {
		converters.add(converter);
	}
	
	protected static Set<Converter> getConverters() {
		return converters;
	}
	
}
