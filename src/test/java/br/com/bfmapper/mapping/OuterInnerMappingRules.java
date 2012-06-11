package br.com.bfmapper.mapping;

import br.com.bfmapper.Converter;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.Outer;
import br.com.bfmapper.model.OuterCanonico;

public class OuterInnerMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		MappingRules.addRule(new Converter(OuterCanonico.class, Outer.class) {{ 
			add("innerCanonico", "inner");
		}});
		MappingRules.addRule(new Converter(OuterCanonico.InnerCanonico.class, Outer.Inner.class));
	}
}
