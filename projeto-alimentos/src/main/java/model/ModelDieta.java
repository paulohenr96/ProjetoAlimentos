package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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
	@Column
	private Long idUsuario;
	private String nome;
	private String objetivo;
	
	@Column(precision=10, scale=2)
	private BigDecimal totalCalorias=new BigDecimal(0);
	@Column(precision=10, scale=2)
	private BigDecimal totalProteinas=new BigDecimal(0);
	@Column(precision=10, scale=2)
	private BigDecimal totalCarboidratos=new BigDecimal(0);
	@Column(precision=10, scale=2)
	private BigDecimal totalGorduras=new BigDecimal(0);
	
	
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
	
	public void adicionarRefeicao(ModelRefeicao ref) {
		totalCalorias=totalCalorias.add(ref.getCalorias());
		totalProteinas=totalProteinas.add(ref.getProteinas());
		totalCarboidratos=totalCarboidratos.add(ref.getCarboidratos());
		totalGorduras=totalGorduras.add(ref.getGorduras());

	}
	
	
	public void removerRefeicao(ModelRefeicao  ref) {
			
				totalCalorias=totalCalorias.subtract(ref.getCalorias());
				totalProteinas=totalProteinas.subtract(ref.getProteinas());
				totalCarboidratos=totalCarboidratos.subtract(ref.getCarboidratos());
				totalGorduras=totalGorduras.subtract(ref.getGorduras());
				System.out.println("REMOVERREFEICAO + ID="+ref.getId());
			
		

	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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
		ModelDieta other = (ModelDieta) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "ModelDieta [id=" + id + ", idUsuario=" + idUsuario + ", nome=" + nome + ", objetivo=" + objetivo
				+ ", totalCalorias=" + totalCalorias + ", totalProteinas=" + totalProteinas + ", totalCarboidratos="
				+ totalCarboidratos + ", totalGorduras=" + totalGorduras + ", Refeições=" + listaRefeicoes.size() + "]";
	}

	
	
	
	
	 
}
