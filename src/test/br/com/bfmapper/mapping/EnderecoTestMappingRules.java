package br.com.bfmapper.mapping;

import br.com.bfmapper.Converter;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.Endereco;
import br.com.bfmapper.model.EnderecoCanonico;

public class EnderecoTestMappingRules implements RulesMapper {

    @Override
	public void loadRules() {
		MappingRules.addRule(new Converter(EnderecoCanonico.class, Endereco.class) {{
			add("localidade", "logradouro");
			add("nro", "numero");
			add("bairroCaiK1a", "bairro");
		}});
	}

}
