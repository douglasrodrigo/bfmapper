package br.com.bfmapper.model;

import java.util.Date;
import java.util.List;

public class AlunoCanonico {
	
	private Long id;
	private String nome;
	private Long idade;
	private Date dataAniversario;
	private TipoAluno tipoAluno;
	private String sexo;
	private List<Double> notas;
	
	public AlunoCanonico() { }

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

	public Long getIdade() {
		return idade;
	}

	public void setIdade(Long idade) {
		this.idade = idade;
	}
	
	public Date getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(Date dataAniversario) {
		this.dataAniversario = dataAniversario;
	}
	
	public TipoAluno getTipoAluno() {
		return tipoAluno;
	}

	public void setTipoAluno(TipoAluno tipoAluno) {
		this.tipoAluno = tipoAluno;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public List<Double> getNotas() {
		return notas;
	}

	public void setNotas(List<Double> notas) {
		this.notas = notas;
	}

	@Override
	public String toString() {
		return this.id + " " + this.nome + " " + this.idade + " " + this.dataAniversario + " " + this.tipoAluno;
	}
}
