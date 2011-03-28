package br.com.bfmapper.util;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils {

	@SuppressWarnings("unchecked")
	public static <T, COLLECTION extends Collection<T>> COLLECTION filter(COLLECTION collectionSource, Acceptable<T> acceptable) {
		COLLECTION filteredCollection = null;
		try {
		    try {
		        filteredCollection = (COLLECTION) collectionSource.getClass().newInstance();    
            } catch (InstantiationException e) {
                filteredCollection = (COLLECTION) new ArrayList<T>();
            }
			
			for (T item : collectionSource) {
				if (acceptable.accept(item)) {
					filteredCollection.add(item);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid collection source");
		}
		return filteredCollection;
	}
	
	public static <T, COLLECTION extends Collection<T>> T first(COLLECTION collectionSource, Acceptable<T> acceptable) {
	    return filter(collectionSource, acceptable).iterator().next();
	}
	
    public static <T, COLLECTION extends Collection<T>> T unique(COLLECTION collectionSource, Acceptable<T> acceptable) {
        COLLECTION filteredCollection = filter(collectionSource, acceptable);
        
        if (filteredCollection.size() > 1) {
            throw new RuntimeException("Unique element exception on collection");
        }
        
        return filteredCollection.size() > 0 ? filteredCollection.iterator().next() : null;
    }
	
}
