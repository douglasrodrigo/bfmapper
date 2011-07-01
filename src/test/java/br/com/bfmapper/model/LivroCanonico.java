package br.com.bfmapper.model;


public class LivroCanonico {

	private String nome;
	private String editora;
	private String autor;
	private Long anoPublicacao;
	
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
    public Long getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(Long anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
}
