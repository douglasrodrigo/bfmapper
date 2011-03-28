package br.com.bfmapper.model;

public class Livro {

	private String nome;
	private String editora;
	private String autor;

	public Livro() { }
	
	public Livro(String nome, String editora, String autor) {
		this.nome = nome;
		this.editora = editora;
		this.autor = autor;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
}
