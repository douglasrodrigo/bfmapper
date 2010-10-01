package br.com.bfmapper.model;

public class EnderecoCanonico {

	private String localidade;
	
	private String nro;
	
	private String bairroCaiK1a;
	
	public EnderecoCanonico() {}
	
	public EnderecoCanonico(String localidade, String nro, String bairroCaiK1a) {
		super();
		this.localidade = localidade;
		this.nro = nro;
		this.bairroCaiK1a = bairroCaiK1a;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getNro() {
		return nro;
	}

	public void setNro(String nro) {
		this.nro = nro;
	}

	public String getBairroCaiK1a() {
		return bairroCaiK1a;
	}

	public void setBairroCaiK1a(String bairroCaiK1a) {
		this.bairroCaiK1a = bairroCaiK1a;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bairroCaiK1a == null) ? 0 : bairroCaiK1a.hashCode());
		result = prime * result
				+ ((localidade == null) ? 0 : localidade.hashCode());
		result = prime * result + ((nro == null) ? 0 : nro.hashCode());
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
		EnderecoCanonico other = (EnderecoCanonico) obj;
		if (bairroCaiK1a == null) {
			if (other.bairroCaiK1a != null) {
				return false;
			}
		} else if (!bairroCaiK1a.equals(other.bairroCaiK1a)) {
			return false;
		}
		if (localidade == null) {
			if (other.localidade != null) {
				return false;
			}
		} else if (!localidade.equals(other.localidade)) {
			return false;
		}
		if (nro == null) {
			if (other.nro != null) {
				return false;
			}
		} else if (!nro.equals(other.nro)) {
			return false;
		}
		return true;
	}

	
	
}
