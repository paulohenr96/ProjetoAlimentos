package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class ModelRefeicaoConsumida implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5787781620229893292L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )

	private Long id;
	
	@ManyToOne
	private ModelRefeicao refeicao;
	
	@ManyToOne
	private ModelConsumidoDia macros;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ModelRefeicao getRefeicao() {
		return refeicao;
	}

	public void setRefeicao(ModelRefeicao refeicao) {
		this.refeicao = refeicao;
	}

	public ModelConsumidoDia getMacros() {
		return macros;
	}

	public void setMacros(ModelConsumidoDia macros) {
		this.macros = macros;
	}

	
}
