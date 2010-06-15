package br.com.blackfoot.bfmapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingContext {

	private Map<Object, Object> cachedObjects = new HashMap<Object, Object>(); 
	private List<AppliedObject> appliedObjects = new ArrayList<AppliedObject>();

	public Map<Object, Object> getCachedObjects() {
		return cachedObjects;
	}
	public List<AppliedObject> getAppliedObjects() {
		return appliedObjects;
	}
}
