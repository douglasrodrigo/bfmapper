package br.com.bfmapper.mapping;

import br.com.bfmapper.Converter;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.Carro;
import br.com.bfmapper.model.CarroCanonico;

public class CarroMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		MappingRules.addRule(new Converter(CarroCanonico.class, Carro.class) {{
			add("marca", "nome");
			add("modelo", "tipo");
			add("pneu", "pneu.modelo");
		}});
	}

}
