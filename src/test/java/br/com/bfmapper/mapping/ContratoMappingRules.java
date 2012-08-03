package br.com.bfmapper.mapping;

import static br.com.bfmapper.MappingRules.addRule;
import br.com.bfmapper.Converter;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.canonic.ContratoCanonico;
import br.com.bfmapper.model.vo.Contrato;
import br.com.bfmapper.model.vo.Dependente;

public class ContratoMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		addRule(new Converter(ContratoCanonico.class, Contrato.class));
		
		addRule(new Converter(ContratoCanonico.Dependente.class, Dependente.class));
	}
}
