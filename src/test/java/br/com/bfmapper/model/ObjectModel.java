package br.com.bfmapper.model;

import java.util.List;

public class ObjectModel {

	private List<TipoAluno> tipoAlunos;

	public ObjectModel() { }
	
	public ObjectModel(List<TipoAluno> tipoAlunos) {
		this.tipoAlunos = tipoAlunos;
	}

	public List<TipoAluno> getTipoAlunos() {
		return tipoAlunos;
	}

	public void setTipoAlunos(List<TipoAluno> tipoAlunos) {
		this.tipoAlunos = tipoAlunos;
	}

}
