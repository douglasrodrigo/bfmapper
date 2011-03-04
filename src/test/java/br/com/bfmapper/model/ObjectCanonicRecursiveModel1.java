package br.com.bfmapper.model;


public class ObjectCanonicRecursiveModel1 {

	private ObjectCanonicRecursiveModel2 object2;

	public ObjectCanonicRecursiveModel2 getObject2() {
		return object2;
	}

	public void setObject2(ObjectCanonicRecursiveModel2 object2) {
		this.object2 = object2;
	}
	
    public int hashCode() {
    	return System.identityHashCode(this);
    }
    
    public String toString() {
    	return this.getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
    
    public boolean equals(Object obj) {
    	return hashCode() == obj.hashCode();
    }

}
