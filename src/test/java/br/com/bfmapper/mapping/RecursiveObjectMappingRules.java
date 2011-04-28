package br.com.bfmapper.mapping;

import javax.annotation.PostConstruct;

import br.com.bfmapper.Converter;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.ObjectCanonicRecursiveModel1;
import br.com.bfmapper.model.ObjectCanonicRecursiveModel2;
import br.com.bfmapper.model.ObjectRecursiveModel1;
import br.com.bfmapper.model.ObjectRecursiveModel2;

public class RecursiveObjectMappingRules implements RulesMapper {

	@PostConstruct
	public void loadRules() {
		MappingRules.addRule(new Converter(ObjectRecursiveModel1.class, ObjectCanonicRecursiveModel1.class));
		MappingRules.addRule(new Converter(ObjectRecursiveModel2.class, ObjectCanonicRecursiveModel2.class));
	}
}
