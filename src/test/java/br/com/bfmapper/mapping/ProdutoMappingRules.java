package br.com.bfmapper.mapping;

import org.apache.commons.lang.ClassUtils;

import br.com.bfmapper.Converter;
import br.com.bfmapper.MappingRules;
import br.com.bfmapper.RulesMapper;
import br.com.bfmapper.model.Produto;
import br.com.bfmapper.model.ProdutoCanonico;
import br.com.bfmapper.transformer.SimpleTransformer;

public class ProdutoMappingRules implements RulesMapper {

	@Override
	public void loadRules() {
		MappingRules.addRule(new Converter(ProdutoCanonico.class, Produto.class) {{
			add("marca", "marca").with(new LowerCaseTransformer());
		}});
	}

	public class UpperCaseTransformer implements SimpleTransformer {
		@Override
		public <T> T transform(Object value, Class<T> type) {
			Object returnValue = null;
			
			if (ClassUtils.isAssignable(type, String.class)) {
				returnValue = ((String) value).toUpperCase();
			} 
			
			return type.cast(returnValue);
		}
	}

	public class LowerCaseTransformer implements SimpleTransformer {
		@Override
		public <T> T transform(Object value, Class<T> type) {
			Object returnValue = null;
			
			if (ClassUtils.isAssignable(type, String.class)) {
				returnValue = ((String) value).toLowerCase();
			} 
			
			return type.cast(returnValue);
		}
	}
	
}
