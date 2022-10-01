package model;

import java.io.Serializable;
import java.math.BigDecimal;
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
	private BigDecimal calorias;
	
	@Column(precision=10, scale=2)
	private BigDecimal proteinas;
	
	@Column(precision=10, scale=2)
	private BigDecimal carboidrato;

	@Column(precision=10, scale=2)
	private BigDecimal gordura;
	
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
	

	
	public void adicionarAlimento(ModelAlimento ali) {
		
		if (proteinas!=null && calorias !=null && gordura!=null && carboidrato!=null) {
			proteinas=new BigDecimal(proteinas.doubleValue()+ali.getProteina().doubleValue());
			carboidrato=new BigDecimal(carboidrato.doubleValue()+ali.getCarboidrato().doubleValue());
			gordura=new BigDecimal(gordura.doubleValue()+ali.getGordura().doubleValue());
			calorias=new BigDecimal(calorias.doubleValue()+ali.getCaloria().doubleValue());
		}else {
			proteinas=new BigDecimal(ali.getProteina().doubleValue());
			carboidrato=new BigDecimal(ali.getCarboidrato().doubleValue());
			gordura=new BigDecimal(ali.getGordura().doubleValue());
			calorias=new BigDecimal(ali.getCaloria().doubleValue());
		}
		
		
	
		
	
	}
	
	
	
	public void retirarAlimento(ModelAlimento ali) {
		proteinas=new BigDecimal(proteinas.doubleValue()-ali.getProteina().doubleValue());
		carboidrato=new BigDecimal(carboidrato.doubleValue()-ali.getCarboidrato().doubleValue());
		gordura=new BigDecimal(gordura.doubleValue()-ali.getGordura().doubleValue());
		calorias=new BigDecimal(calorias.doubleValue()-ali.getCaloria().doubleValue());
		
		
	
	}
	public void adicionarRefeicao(ModelRefeicao ref) {
		proteinas=proteinas.add(ref.getProteinas());
		calorias=calorias.add(ref.getCalorias());
		carboidrato=carboidrato.add(ref.getCarboidratos());
		gordura=gordura.add(ref.getGorduras());
	}
	public void removerRefeicao(ModelRefeicao ref) {
		
		proteinas=proteinas.subtract(ref.getProteinas());
		calorias=calorias.subtract(ref.getCalorias());
		carboidrato=carboidrato.subtract(ref.getCarboidratos());
		gordura=gordura.add(ref.getGorduras());
	}
	public List<ModelAlimentoConsumido> getListaAlimentos() {
		return listaAlimentos;
	}
	public void setListaAlimentos(List<ModelAlimentoConsumido> listaAlimentos) {
		this.listaAlimentos = listaAlimentos;
	}

	
	public BigDecimal getCalorias() {
		return calorias;
	}
	public void setCalorias(BigDecimal calorias) {
		this.calorias = calorias;
	}
	public BigDecimal getProteinas() {
		return proteinas;
	}
	public void setProteinas(BigDecimal proteinas) {
		this.proteinas = proteinas;
	}
	public BigDecimal getCarboidrato() {
		return carboidrato;
	}
	public void setCarboidrato(BigDecimal carboidrato) {
		this.carboidrato = carboidrato;
	}
	public BigDecimal getGordura() {
		return gordura;
	}
	public void setGordura(BigDecimal gordura) {
		this.gordura = gordura;
	}
	
	
	
}