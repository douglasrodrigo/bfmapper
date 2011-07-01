package br.com.bfmapper.model;

import java.util.Date;

public class Produto {

	private String marca;
	
	private Float preco;
	
	private String fabricante;

	private Date dataValidade;
	
	public Produto() { }
	
	public Produto(String marca, Float preco, String fabricante) {
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

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }	
}
