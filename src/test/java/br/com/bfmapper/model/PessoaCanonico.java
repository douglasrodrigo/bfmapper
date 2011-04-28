package br.com.bfmapper.model;

import java.util.List;

public class PessoaCanonico {

	private Long id;
	private String nome;
	private List<EnderecoCanonico> enderecos;
	
	public PessoaCanonico() { }
	
	public PessoaCanonico(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<EnderecoCanonico> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoCanonico> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PessoaCanonico other = (PessoaCanonico) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}

