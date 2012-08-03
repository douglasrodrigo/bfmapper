package br.com.bfmapper.model.vo;

import java.util.ArrayList;
import java.util.List;

public class Contrato {

	private String codigo;
	private List<Dependente> dependentes = new ArrayList<Dependente>();
	
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
	
	public Contrato adicionar(Dependente dependente) {
		dependentes.add(dependente);
		return this;
	}
}
