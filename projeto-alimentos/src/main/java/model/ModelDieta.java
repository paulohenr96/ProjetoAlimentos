package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class ModelDieta implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 8904137132967527454L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id;
	
	private String nome;
	private String objetivo;
	
	@Column(precision=10, scale=2)
	private BigDecimal totalCalorias=new BigDecimal(0);;
	@Column(precision=10, scale=2)
	private BigDecimal totalProteinas=new BigDecimal(0);;
	@Column(precision=10, scale=2)
	private BigDecimal totalCarboidratos=new BigDecimal(0);;
	@Column(precision=10, scale=2)
	private BigDecimal totalGorduras=new BigDecimal(0);;
	
	
	@JsonIgnore
	@OneToMany(mappedBy ="dieta",fetch=FetchType.EAGER)
	private List<ModelRefeicao> listaRefeicoes;

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

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public BigDecimal getTotalCalorias() {
		return totalCalorias;
	}

	public void setTotalCalorias(BigDecimal totalCalorias) {
		this.totalCalorias = totalCalorias;
	}

	public BigDecimal getTotalProteinas() {
		return totalProteinas;
	}

	public void setTotalProteinas(BigDecimal totalProteinas) {
		this.totalProteinas = totalProteinas;
	}

	public BigDecimal getTotalCarboidratos() {
		return totalCarboidratos;
	}

	public void setTotalCarboidratos(BigDecimal totalCarboidratos) {
		this.totalCarboidratos = totalCarboidratos;
	}

	public BigDecimal getTotalGorduras() {
		return totalGorduras;
	}

	public void setTotalGorduras(BigDecimal totalGorduras) {
		this.totalGorduras = totalGorduras;
	}

	public List<ModelRefeicao> getListaRefeicoes() {
		return listaRefeicoes;
	}

	public void setListaRefeicoes(List<ModelRefeicao> listaRefeicoes) {
		this.listaRefeicoes = listaRefeicoes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	 
}
