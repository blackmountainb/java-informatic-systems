package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Hashtable;

public class Autor implements java.io.Serializable{
	
	private String email, senha, nome, afiliacao;
	
	public Autor(String nome, String apelido, String email, String senha, String afiliacao) throws RemoteException{
		this.nome = apelido + ", " + nome;
		this.email = email;
		this.senha = senha;
		this.afiliacao = afiliacao;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getAfiliacao() {
		return afiliacao;
	}
	
	public void setAfiliacao(String afiliacao) {
		this.afiliacao = afiliacao;
	}

}
