package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ModelAlimentoRefeicao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	@Column(precision=10, scale=2)
	private double quantidade;

	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private ModelRefeicao refeicao;
	
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private ModelAlimento alimento;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public double getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}


	public ModelRefeicao getRefeicao() {
		return refeicao;
	}


	public void setRefeicao(ModelRefeicao refeicao) {
		this.refeicao = refeicao;
	}


	public ModelAlimento getAlimento() {
		return alimento;
	}


	public void setAlimento(ModelAlimento alimento) {
		this.alimento = alimento;
	}
	
	
	
}
