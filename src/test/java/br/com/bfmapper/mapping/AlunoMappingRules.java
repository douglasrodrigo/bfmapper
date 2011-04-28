package br.com.bfmapper.mapping;

import br.com.bfmapper.Converter;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.Aluno;
import br.com.bfmapper.model.AlunoCanonico;
import br.com.bfmapper.transformer.DateTimeTransformer;

public class AlunoMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		MappingRules.addRule(new Converter(AlunoCanonico.class, Aluno.class) {{
			add("idade", "idade");
			add("dataAniversario", "dataAniversario").with(new DateTimeTransformer("ddMMyyyy"));
			add("tipoAluno", "tipoAluno");
			addDefault(AlunoCanonico.class, "sexo", "F");
		}});
	}

}
