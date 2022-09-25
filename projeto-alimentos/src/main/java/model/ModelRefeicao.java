package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class ModelRefeicao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	private String nome;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "refeicao",fetch=FetchType.EAGER)
	private List <ModelAlimentoRefeicao> listaAlimentos;
	private Long idUserLogado;
	
	private double calorias;
	private double proteinas;
	private double carboidratos;
	private double gorduras;
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
	public List<ModelAlimentoRefeicao> getListaAlimentos() {
		return listaAlimentos;
	}
	public void setListaAlimentos(List<ModelAlimentoRefeicao> listaAlimentos) {
		this.listaAlimentos = listaAlimentos;
	}
	public Long getIdUserLogado() {
		return idUserLogado;
	}
	public void setIdUserLogado(Long idUserLogado) {
		this.idUserLogado = idUserLogado;
	}
	public double getCalorias() {
		return calorias;
	}
	public void setCalorias(double calorias) {
		this.calorias = calorias;
	}
	public double getProteinas() {
		return proteinas;
	}
	public void setProteinas(double proteinas) {
		this.proteinas = proteinas;
	}
	public double getCarboidratos() {
		return carboidratos;
	}
	public void setCarboidratos(double carboidratos) {
		this.carboidratos = carboidratos;
	}
	public double getGorduras() {
		return gorduras;
	}
	public void setGorduras(double gorduras) {
		this.gorduras = gorduras;
	}
	
	
	public void addAlimento(ModelAlimentoRefeicao ali) {
		double quantidade = ali.getQuantidade();
		ali.getAlimento().consumir(ali.getQuantidade());
		
		calorias+=(ali.getAlimento().getCaloria());
		proteinas+=(ali.getAlimento().getProteina());
		carboidratos+=(ali.getAlimento().getCarboidrato());
		gorduras+=(ali.getAlimento().getGordura());

		
	}
	public void removeralimento(ModelAlimentoRefeicao ali) {
		// TODO Auto-generated method stub
		ali.getAlimento().consumir(ali.getQuantidade());

		calorias-=(ali.getAlimento().getCaloria());
		proteinas-=(ali.getAlimento().getProteina());
		carboidratos-=(ali.getAlimento().getCarboidrato());
		gorduras-=(ali.getAlimento().getGordura());		
	}
}
