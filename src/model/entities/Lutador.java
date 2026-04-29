package model.entities;

import java.util.Objects;

public class Lutador {
	private Integer id;
	private String nome;
	private Double peso;
	private int vitorias;
	private int derrotas;
	private int empates;
	private Categoria categoria;
	
	public Lutador() {
		
	}
	public Lutador(Integer id, String nome, Double peso, Integer vitorias, Integer derrotas, Integer empates,
			Categoria categoria) {
		this.id = id;
		this.nome = nome;
		this.peso = peso;
		this.vitorias = vitorias;
		this.derrotas = derrotas;
		this.empates = empates;
		this.categoria = categoria;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public Integer getVitorias() {
		return vitorias;
	}
	public void setVitorias(Integer vitorias) {
		this.vitorias = vitorias;
	}
	public Integer getDerrotas() {
		return derrotas;
	}
	public void setDerrotas(Integer derrotas) {
		this.derrotas = derrotas;
	}
	public Integer getEmpates() {
		return empates;
	}
	public void setEmpates(Integer empates) {
		this.empates = empates;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	@Override
	public String toString() {
		return "Lutador [id=" + id + ", nome=" + nome + ", peso=" + peso + ", vitorias=" + vitorias + ", derrotas="
				+ derrotas + ", empates=" + empates + ", categoria=" + categoria + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lutador other = (Lutador) obj;
		return Objects.equals(id, other.id);
	}
}
