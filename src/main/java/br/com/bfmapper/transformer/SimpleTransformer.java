package br.com.bfmapper.transformer;

public interface SimpleTransformer extends Transformer {

	
	<T>  T transform(Object value, Class<T> type);
	
}
