package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ModelAlimento implements Serializable {
	
@Id
@GeneratedValue (strategy=GenerationType.AUTO)

private Long id;
private String nome;


@OneToMany(mappedBy = "alimento",fetch=FetchType.EAGER)
private List<ModelAlimentoRefeicao> listaAlimentosRefeicao;


@Column(precision=10, scale=2)
private double porcao;

@Column(precision=10, scale=2)
private double caloria;

@Column(precision=10, scale=2)
private double proteina;

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
public double getCaloria() {
	return caloria;
}
public void setCaloria(double caloria) {
	this.caloria = caloria;
}
public double getProteina() {
	return proteina;
}
public void setProteina(double proteina) {
	this.proteina = proteina;
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
public ModelAlimento consumir(int quantidade) {
	double k=quantidade/porcao;
	
	proteina*=k;
	caloria*=k;
	gordura*=k;
	carboidrato*=k;
	return this;
}
@Override
public String toString() {
	return "ModelAlimento [id=" + id + ", nome=" + nome + ", porcao=" + porcao + ", caloria=" + caloria + ", proteina="
			+ proteina + ", carboidrato=" + carboidrato + ", gordura=" + gordura + "]";
}
	
}
