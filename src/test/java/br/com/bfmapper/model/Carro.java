package br.com.bfmapper.model;

import java.util.List;

public class Carro {
	private Long id;
	private String nome;
	private String tipo;
	private Pneu pneu;
	private List<Pessoa> donos;
	
	public Carro() {}
	
	public Carro(Long id, String nome, Pneu pneu) {
		this.id = id;
		this.nome = nome;
		this.pneu = pneu;
	}
	
	public Carro(Long id, String nome, String tipo, Pneu pneu) {
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.pneu = pneu;
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
	public Pneu getPneu() {
		return pneu;
	}
	public void setPneu(Pneu pneu) {
		this.pneu = pneu;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
    public List<Pessoa> getDonos() {
        return donos;
    }

    public void setDonos(List<Pessoa> donos) {
        this.donos = donos;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Carro other = (Carro) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "CARRO: " + this.nome + " " + this.tipo + " " + this.getPneu().getModelo();
	}
}
