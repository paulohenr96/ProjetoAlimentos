package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ModelRefeicao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "refeicao", fetch = FetchType.EAGER)
	private List<ModelAlimentoRefeicao> listaAlimentos;
	private Long idUserLogado;

	@Column(precision = 10, scale = 2)
	private BigDecimal calorias = new BigDecimal(0);

	@Column(precision = 10, scale = 2)
	private BigDecimal proteinas = new BigDecimal(0);

	@Column(precision = 10, scale = 2)
	private BigDecimal carboidratos = new BigDecimal(0);

	@Column(precision = 10, scale = 2)
	private BigDecimal gorduras = new BigDecimal(0);

	@ManyToOne(optional = true)
	private ModelDieta dieta;

	@ManyToOne(optional = true)
	private ModelConsumidoDia macros;

	public void setDieta(ModelDieta dieta) {
		this.dieta = dieta;
	}

	public ModelDieta getDieta() {
		return dieta;
	}

	@Temporal(TemporalType.TIME)
	private Date horario;

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

	public void addAlimento(ModelAlimentoRefeicao ali) {
	
		System.out.println(ali.getAlimento());

		calorias = new BigDecimal(converterDouble(calorias.doubleValue() + (ali.getAlimento().getCaloria().doubleValue())));


		
		proteinas = new BigDecimal(converterDouble(proteinas.doubleValue() + (ali.getAlimento().getProteina().doubleValue())));
		carboidratos = new BigDecimal(converterDouble(carboidratos.doubleValue() + (ali.getAlimento().getCarboidrato().doubleValue())));
		gorduras = new BigDecimal(converterDouble(gorduras.doubleValue() + ali.getAlimento().getGordura().doubleValue()));

	}
	public double converterDouble (double valor) {
		
		String str=valor+"";
		if (str.contains(".")) {
			String[] split = str.split("\\.");
			String inteiro=split[0];
			String decimal="";
			if (split[1].length()>1) {
				decimal=split[1].substring(0, 2);

			}else {
				decimal=split[1];
			}
			double novovalor=Double.parseDouble(inteiro+"."+decimal);
			valor=novovalor;
		}
		return valor;

	}
	public void removeralimento(ModelAlimentoRefeicao ali) {
		// TODO Auto-generated method stub
		ali.getAlimento().consumir(ali.getQuantidade());

		calorias = new BigDecimal(calorias.doubleValue() - (ali.getAlimento().getCaloria().doubleValue()));
		proteinas = new BigDecimal(proteinas.doubleValue() - (ali.getAlimento().getProteina().doubleValue()));
		carboidratos = new BigDecimal(carboidratos.doubleValue() - (ali.getAlimento().getCarboidrato().doubleValue()));
		gorduras = new BigDecimal(gorduras.doubleValue() - (ali.getAlimento().getGordura().doubleValue()));

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

	public BigDecimal getCarboidratos() {
		return carboidratos;
	}

	public void setCarboidratos(BigDecimal carboidratos) {
		this.carboidratos = carboidratos;
	}

	public BigDecimal getGorduras() {
		return gorduras;
	}

	public void setGorduras(BigDecimal gorduras) {
		this.gorduras = gorduras;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public ModelConsumidoDia getMacros() {
		return macros;
	}

	public void setMacros(ModelConsumidoDia macros) {
		this.macros = macros;
	}
}
