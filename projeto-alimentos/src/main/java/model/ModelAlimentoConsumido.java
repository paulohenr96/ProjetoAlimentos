package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity

public class ModelAlimentoConsumido implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6438964272327665898L;

	/**
	 * 
	 */

	
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	private Long id;
	
	private Long idAlimento;
	
	@Column(precision=10, scale=2)
	private double quantidade;
	
	
    @JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ModelConsumidoDia macros;
    
    
	private String nome;
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
	public Long getId() {
		return id;
	}
	

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAlimento() {
		return idAlimento;
	}

	public void setIdAlimento(Long idAlimento) {
		this.idAlimento = idAlimento;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public ModelConsumidoDia getMacros() {
		return macros;
	}

	public void setMacros(ModelConsumidoDia macros) {
		this.macros = macros;
	}
	@Override
	public String toString() {
		return "ModelAlimentoConsumido [id=" + id + ", idAlimento=" + idAlimento + ", quantidade=" + quantidade
				+ ", macros=" + macros + ", nome=" + nome + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, idAlimento, macros, nome, quantidade);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ModelAlimentoConsumido)) {
			return false;
		}
		ModelAlimentoConsumido other = (ModelAlimentoConsumido) obj;
		return Objects.equals(id, other.id) && Objects.equals(idAlimento, other.idAlimento)
				&& Objects.equals(macros, other.macros) && Objects.equals(nome, other.nome)
				&& Double.doubleToLongBits(quantidade) == Double.doubleToLongBits(other.quantidade);
	}
	
	
	
	
}
