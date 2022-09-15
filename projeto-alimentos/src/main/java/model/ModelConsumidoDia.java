package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ModelConsumidoDia implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private ModelUsuario usuario;
	
	
	private Long idAlimento;
	
	
	
	@Basic
	@Temporal(TemporalType.DATE)
	private Date data;
	private double calorias;
	private double proteinas;
	private double carboidrato;
	private double gordura;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ModelUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(ModelUsuario usuario) {
		this.usuario = usuario;
	}
	public Long getIdAlimento() {
		return idAlimento;
	}
	public void setIdAlimento(Long idAlimento) {
		this.idAlimento = idAlimento;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
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
	public double getCarboidrato() {
		return carboidrato;
	}
	public void setCarboidrato(double carboidrato) {
		this.carboidrato = carboidrato;
	}
	public double getGordura() {
		return gordura;
	}
	public void setGordura(double gordura) {
		this.gordura = gordura;
	}

	
	public void adicionarAlimento(ModelAlimento ali) {
		proteinas+=ali.getProteina();
		carboidrato+=ali.getCarboidrato();
		gordura+=ali.getGordura();
		calorias+=ali.getCaloria();
		
	
	}
	
	public void retirarAlimento(ModelAlimento ali) {
		proteinas-=ali.getProteina();
		carboidrato-=ali.getCarboidrato();
		gordura-=ali.getGordura();
		calorias-=ali.getCaloria();
		
	
	}
	
	
}