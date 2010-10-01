package br.com.bfmapper;

import br.com.bfmapper.transformer.Transformer;

public class DefaultTransformer {

	private Class<?> sourcePropertyClass;
	
	private Class<?> targetPropertyClass;

	private Transformer transformer;

	public DefaultTransformer(Class<?> sourcePropertyClass, Class<?> targetPropertyClass, Transformer transformer) {
		this.sourcePropertyClass = sourcePropertyClass;
		this.targetPropertyClass = targetPropertyClass;
		this.transformer = transformer;
	}

	public Class<?> getSourcePropertyClass() {
		return sourcePropertyClass;
	}

	public void setSourcePropertyClass(Class<?> sourcePropertyClass) {
		this.sourcePropertyClass = sourcePropertyClass;
	}

	public Class<?> getTargetPropertyClass() {
		return targetPropertyClass;
	}

	public void setTargetPropertyClass(Class<?> targetPropertyClass) {
		this.targetPropertyClass = targetPropertyClass;
	}

	public Transformer getTransformer() {
		return transformer;
	}

	public void setTransformer(Transformer transformer) {
		this.transformer = transformer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.sourcePropertyClass == null) ? 0 : this.sourcePropertyClass.hashCode());
		result = prime * result + ((this.targetPropertyClass == null) ? 0 : this.targetPropertyClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DefaultTransformer other = (DefaultTransformer) obj;
		if (this.sourcePropertyClass == null) {
			if (other.getSourcePropertyClass() != null) {
				return false;
			}
		} else if (!this.sourcePropertyClass.equals(other.getSourcePropertyClass())) {
			return false;
		}
		if (this.targetPropertyClass == null) {
			if (other.getTargetPropertyClass() != null) {
				return false;
			}
		} else if (!this.targetPropertyClass.equals(other.getTargetPropertyClass())) {
			return false;
		}
		return true;
	}

}
