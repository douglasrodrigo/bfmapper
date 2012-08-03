package br.com.bfmapper.model.canonic;

import java.util.List;

public class ContratoCanonico {

	private String codigo;
	private List<Dependente> dependentes;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}
	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	public class Dependente {
		private String nome;

		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
	}
}
