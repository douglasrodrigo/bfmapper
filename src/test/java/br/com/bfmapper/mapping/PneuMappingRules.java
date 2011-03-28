package br.com.bfmapper.mapping;

import br.com.bfmapper.Converter;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.Pneu;
import br.com.bfmapper.model.PneuCanonico;

public class PneuMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		MappingRules.addRule(new Converter(PneuCanonico.class, Pneu.class) {{
			add("codigo", "id").withShallowCopy();
		}});
	}

}
