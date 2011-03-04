package br.com.bfmapper.model;

import java.util.List;

public class ObjectCanonicModel {

	private List<TipoAluno> tipoAlunos;

	public ObjectCanonicModel() { }
	
	public ObjectCanonicModel(List<TipoAluno> tipoAlunos) {
		this.tipoAlunos = tipoAlunos;
	}

	public List<TipoAluno> getTipoAlunos() {
		return tipoAlunos;
	}

	public void setTipoAlunos(List<TipoAluno> tipoAlunos) {
		this.tipoAlunos = tipoAlunos;
	}

}
