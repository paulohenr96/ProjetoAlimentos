package model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class ModelUsuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	
	private Long id;
	private String login;
	private String nome;
	private String sobreNome;
	private String email;	
	private String senha;
	
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<ModelConsumidoDia> alimentos;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String usuario) {
		this.login = usuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "ModelUsuario [id=" + id + ", login=" + login + ", nome=" + nome + ", sobreNome=" + sobreNome
				+ ", email=" + email + ", senha=" + senha + ", alimentos=" + alimentos + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(alimentos, email, id, login, nome, senha, sobreNome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ModelUsuario)) {
			return false;
		}
		ModelUsuario other = (ModelUsuario) obj;
		return Objects.equals(alimentos, other.alimentos) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(login, other.login)
				&& Objects.equals(nome, other.nome) && Objects.equals(senha, other.senha)
				&& Objects.equals(sobreNome, other.sobreNome);
	}
	
	

}
