package br.com.bfmapper.model;

public class CarroCanonico {
	private String marca;
	private String modelo;
	private String pneu;

	public CarroCanonico() {}
	
	public CarroCanonico(String marca, String modelo) {
		this.marca = marca;
		this.modelo = modelo;
	}
	
	public CarroCanonico(String marca, String modelo, String pneu) {
		this.marca = marca;
		this.modelo = modelo;
		this.pneu = pneu;
	}

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getPneu() {
		return pneu;
	}
	public void setPneu(String pneu) {
		this.pneu = pneu;
	}

	@Override
	public String toString() {
		return "CARRO CANONICO: " + this.marca + " " + this.modelo + " " + this.pneu;
	}
}