package br.com.bfmapper.model;

public class ProdutoCanonico {

	private String marca;
	
	private Double preco;
	
	private String fabricante;

	public ProdutoCanonico() { }
	
	public ProdutoCanonico(String marca, Double preco, String fabricante) {
		this.marca = marca;
		this.preco = preco;
		this.fabricante = fabricante;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

}
