package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.ArrayList;

public class Autor implements java.io.Serializable{
	
	private String email, senha, nome, afiliacao;
	private ArrayList<Publi> pubAssociadas = new ArrayList<Publi>();
	
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
	
	public ArrayList<Publi> getpubAssociadas() {
		return pubAssociadas;
	}
	
	public void addPubAssociada(Publi publi) {
		if(!pubAssociadas.contains(publi)) {
			pubAssociadas.add(publi);
		}
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
