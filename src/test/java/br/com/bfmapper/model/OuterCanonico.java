package br.com.bfmapper.model;

public class OuterCanonico {

	private String name;

	private InnerCanonico innerCanonico;
	
	public InnerCanonico getInnerCanonico() {
		return innerCanonico;
	}

	public void setInnerCanonico(InnerCanonico innerCanonico) {
		this.innerCanonico = innerCanonico;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((innerCanonico == null) ? 0 : innerCanonico.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		OuterCanonico other = (OuterCanonico) obj;
		if (innerCanonico == null) {
			if (other.innerCanonico != null)
				return false;
		} else if (!innerCanonico.equals(other.innerCanonico))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public class InnerCanonico {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
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
			InnerCanonico other = (InnerCanonico) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		private OuterCanonico getOuterType() {
			return OuterCanonico.this;
		}
	}
}
