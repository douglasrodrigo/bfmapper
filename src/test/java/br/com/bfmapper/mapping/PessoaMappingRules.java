package br.com.bfmapper.mapping;

import br.com.bfmapper.Converter;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.Pessoa;
import br.com.bfmapper.model.PessoaCanonico;

public class PessoaMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		MappingRules.addRule(new Converter(PessoaCanonico.class, Pessoa.class));
	}

}
