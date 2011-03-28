package br.com.bfmapper;

public class AppliedObject {
	
	private AppliedType appliedType;
	
	private Object sourceObject;
	
	private String attribute;
	
	public AppliedObject(Object sourceObject) {
		this.appliedType = AppliedType.APPLY;
		this.sourceObject = sourceObject;
	}
	
	public AppliedObject(AppliedType appliedType, Object sourceObject, String attribute) {
		this.appliedType = appliedType;
		this.sourceObject = sourceObject;
		this.attribute = attribute;
	}

	public AppliedType getAppliedType() {
		return appliedType;
	}
	
	public String getAttribute() {
		return attribute;
	}

	public Object getSourceObject() {
		return sourceObject;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appliedType == null) ? 0 : appliedType.hashCode());
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((sourceObject == null) ? 0 : sourceObject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppliedObject other = (AppliedObject) obj;
		if (appliedType == null) {
			if (other.appliedType != null)
				return false;
		} else if (!appliedType.equals(other.appliedType))
			return false;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (sourceObject == null) {
			if (other.sourceObject != null)
				return false;
		} else if (!sourceObject.equals(other.sourceObject))
			return false;
		return true;
	}
	
}
