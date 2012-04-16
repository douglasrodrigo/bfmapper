package br.com.bfmapper;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MappingRules {
	
	private static Set<Converter> converters = new HashSet<Converter>();
	
	public static void addRule(Converter converter) {
		if (converters.contains(converter)) {
		    Logger.getLogger(MappingRules.class.getName()).log(
	            Level.WARNING,
	            String.format("Skipping duplicated converter (sourceClass: %s, targetClass: %s) " +
	                    "declared in %s. Was the RulesMapper.loadRules() method called multiple times?",
                    converter.getSourceClass().getName(), converter.getTargetClass().getName(), 
                    Thread.currentThread().getStackTrace()[2]  
                )
		    );
		} else {
		    converters.add(converter);
		}
	}
	
	protected static Set<Converter> getConverters() {
		return converters;
	}
}
