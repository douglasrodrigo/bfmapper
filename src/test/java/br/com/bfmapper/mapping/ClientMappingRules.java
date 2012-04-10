package br.com.bfmapper.mapping;

import static br.com.bfmapper.MappingRules.addRule;
import static br.com.bfmapper.MappingRules.addTemplate;
import br.com.bfmapper.Converter;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.canonic.Client;
import br.com.bfmapper.model.canonic.CoolClient;
import br.com.bfmapper.model.canonic.CorporateClient;
import br.com.bfmapper.transformer.DateTimeTransformer;

public class ClientMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		addTemplate(new Converter(Client.class, br.com.bfmapper.model.xml.Client.class) {{
			add("name", "xmlName");
			add("birthday", "xmlBirthday").with(new DateTimeTransformer());
		}}, "client");

		addRule(new Converter(CoolClient.class, br.com.bfmapper.model.xml.Client.class) {{
			add("twitter", "xmlTwitter");
		}}).extendsOf("client");
		
		addRule(new Converter(CorporateClient.class, br.com.bfmapper.model.xml.Client.class) {{
			add("email", "xmlEmail");
			add("phone", "xmlPhone");
		}}).extendsOf("client");
	}

}
