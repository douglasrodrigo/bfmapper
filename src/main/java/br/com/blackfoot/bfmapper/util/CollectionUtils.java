package br.com.blackfoot.bfmapper.util;

import java.util.Collection;

public class CollectionUtils {

	@SuppressWarnings("unchecked")
	public static <T, COLLECTION extends Collection<T>> COLLECTION filter(COLLECTION collectionSource, Acceptable<T> acceptable) {
		COLLECTION filteredCollection = null;
		try {
			filteredCollection = (COLLECTION) collectionSource.getClass().newInstance();
			
			for (T item : collectionSource) {
				if (acceptable.accept(item)) {
					filteredCollection.add(item);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("invalid collection source");
		}
		return filteredCollection;
	}
	
}
