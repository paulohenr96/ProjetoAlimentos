package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ModelConsumidoDia {
	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private ModelUsuario usuario;
	
	
	private Long idAlimento;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "macros",fetch=FetchType.EAGER)
	private List<ModelAlimentoConsumido> listaAlimentos;
	
	
	@Basic
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Column(precision=10, scale=2)
	private double calorias;
	
	@Column(precision=10, scale=2)
	private double proteinas;
	
	@Column(precision=10, scale=2)
	private double carboidrato;

	@Column(precision=10, scale=2)
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
	public List<ModelAlimentoConsumido> getListaAlimentos() {
		return listaAlimentos;
	}
	public void setListaAlimentos(List<ModelAlimentoConsumido> listaAlimentos) {
		this.listaAlimentos = listaAlimentos;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ModelConsumidoDia)) {
			return false;
		}
		ModelConsumidoDia other = (ModelConsumidoDia) obj;
		return Double.doubleToLongBits(calorias) == Double.doubleToLongBits(other.calorias)
				&& Double.doubleToLongBits(carboidrato) == Double.doubleToLongBits(other.carboidrato)
				&& Objects.equals(data, other.data)
				&& Double.doubleToLongBits(gordura) == Double.doubleToLongBits(other.gordura)
				&& Objects.equals(id, other.id) && Objects.equals(idAlimento, other.idAlimento)
				&& Objects.equals(listaAlimentos, other.listaAlimentos)
				&& Double.doubleToLongBits(proteinas) == Double.doubleToLongBits(other.proteinas)
				&& Objects.equals(usuario, other.usuario);
	}
	
	
	
}