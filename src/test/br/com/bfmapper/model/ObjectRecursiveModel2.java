package br.com.bfmapper.model;


public class ObjectRecursiveModel2 {

	private ObjectRecursiveModel1 object1;

	public ObjectRecursiveModel1 getObject1() {
		return object1;
	}

	public void setObject1(ObjectRecursiveModel1 object1) {
		this.object1 = object1;
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
