package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ModelAlimento implements Serializable {
	
@Id
@GeneratedValue (strategy=GenerationType.AUTO)

private Long id;
private String nome;
private double porcao;
private double caloria;
private double proteina;
private double carboidrato;
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
