package br.com.bfmapper.model;

import java.util.List;

public class Aluno {
	
	private Long id;
	private String nome;
	private String idade;
	private String dataAniversario;
	private String tipoAluno;
	private List<String> notas; 
	
	private Aluno() { }
	
	public Aluno(Long id, String nome, String idade) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
	}

	public Aluno(Long id, String nome, String idade, String dataAniversario, String tipoAluno) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.dataAniversario = dataAniversario;
		this.tipoAluno = tipoAluno;
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

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public String getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(String dataAniversario) {
		this.dataAniversario = dataAniversario;
	}

	public String getTipoAluno() {
		return tipoAluno;
	}

	public void setTipoAluno(String tipoAluno) {
		this.tipoAluno = tipoAluno;
	}

	public List<String> getNotas() {
		return notas;
	}

	public void setNotas(List<String> notas) {
		this.notas = notas;
	}
	
}
