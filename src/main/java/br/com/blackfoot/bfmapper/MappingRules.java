package br.com.blackfoot.bfmapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class MappingRules {
	
	private Set<Converter> converters;
	
	public MappingRules() {
		this.converters = new HashSet<Converter>();
	}
	
	public void addRule(Converter converter) {
		this.converters.add(converter);
	}
	
	protected Set<Converter> getConverters() {
		return this.converters;
	}
	
}
