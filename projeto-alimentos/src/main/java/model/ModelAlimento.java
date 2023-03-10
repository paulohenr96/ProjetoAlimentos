package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class ModelAlimento implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 7051274823496948935L;
@Id
@GeneratedValue (strategy=GenerationType.AUTO)

private Long id;
private String nome;

@JsonIgnore
@OneToMany(mappedBy = "alimento",fetch=FetchType.LAZY)
private List<ModelAlimentoRefeicao> listaAlimentosRefeicao;


@Column(precision=10, scale=2)
private double porcao;

@Column(precision=10, scale=2)
private BigDecimal caloria;

@Column(precision=10, scale=2)
private BigDecimal proteina;

@Column(precision=10, scale=2)
private BigDecimal carboidrato;

@Column(precision=10, scale=2)
private BigDecimal gordura;

private Long idUser;


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
public double getPorcao() {
	return porcao;
}
public void setPorcao(double porcao) {
	this.porcao = porcao;
}

public ModelAlimento consumir(double quantidade) {
	BigDecimal k=new BigDecimal((quantidade/porcao)).setScale(2,RoundingMode.UP);
	
	proteina=new BigDecimal(converterDouble(k.doubleValue()* ((proteina).doubleValue())));
	caloria=new BigDecimal((k.doubleValue()* ((caloria).doubleValue())));
	gordura=new BigDecimal(converterDouble(k.doubleValue()* ((gordura).doubleValue())));
	carboidrato=new BigDecimal(converterDouble(k.doubleValue()* ((carboidrato).doubleValue())));
	return this;
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

@Override
public String toString() {
	return "ModelAlimento [id=" + id + ", nome=" + nome + ", porcao=" + porcao + ", caloria=" + caloria + ", proteina="
			+ proteina + ", carboidrato=" + carboidrato + ", gordura=" + gordura + "]";
}
public List<ModelAlimentoRefeicao> getListaAlimentosRefeicao() {
	return listaAlimentosRefeicao;
}
public void setListaAlimentosRefeicao(List<ModelAlimentoRefeicao> listaAlimentosRefeicao) {
	this.listaAlimentosRefeicao = listaAlimentosRefeicao;
}
public BigDecimal getCaloria() {
	return caloria;
}
public void setCaloria(BigDecimal caloria) {
	this.caloria = caloria;
}
public BigDecimal getProteina() {
	return proteina;
}
public void setProteina(BigDecimal proteina) {
	this.proteina = proteina;
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
public Long getIdUser() {
	return idUser;
}
public void setIdUser(Long idUser) {
	this.idUser = idUser;
}
	
}
