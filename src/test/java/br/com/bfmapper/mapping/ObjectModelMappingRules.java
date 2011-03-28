package br.com.bfmapper.mapping;

import br.com.bfmapper.Converter;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.ObjectCanonicModel;
import br.com.bfmapper.model.ObjectModel;

public class ObjectModelMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		MappingRules.addRule(new Converter(ObjectCanonicModel.class, ObjectModel.class));
	}
	
}
