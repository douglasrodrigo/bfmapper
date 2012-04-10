package br.com.bfmapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MappingRules {
	
	private static Set<Converter> converters = new HashSet<Converter>();
	
	private static Map<String, Converter> templates = new HashMap<String, Converter>();
	
	public static Converter addRule(Converter converter) {
		converters.add(converter);
		return converter;
	}

	public static void addTemplate(Converter converter, String alias) {
		templates.put(alias, converter);
	}
	
	protected static Set<Converter> getConverters() {
		return converters;
	}

	public static Converter getTemplate(String alias) {
		return templates.get(alias);
	}
	
}
