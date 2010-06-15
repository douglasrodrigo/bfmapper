package br.com.blackfoot.bfmapper.transformer;

public interface ComplexTransformer extends Transformer {
	
	<T>  T transform(Object source, Object target, Object value, Class<T> type);
}
