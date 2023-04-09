package server;

import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Hashtable;

public class Publi implements java.io.Serializable {
	
	private String titulo, revista;
	private int anoPubli, volume, numero, paginas, nCitacoes, doi;
	private ArrayList<Autor> listaAutores = new ArrayList<Autor>();
	
	public Publi(ArrayList<Autor> autores, String title, int anoPubli, String revista, int vol, int num, int pag, int cit, int doi)
	{
		this.listaAutores = autores;
		this.titulo = title;
		this.anoPubli = anoPubli;
		this.revista = revista;
		this.volume = vol;
		this.numero = num;
		this.paginas = pag;
		this.nCitacoes = cit;
		this.doi = doi;
	}
	
	public String getTitulo() 
	{
		return titulo;
	}
	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}
	public String getRevista() 
	{
		return revista;
	}
	public void setRevista(String revista) 
	{
		this.revista = revista;
	}
	public int getAnoPubli() 
	{
		return anoPubli;
	}
	public void setAnoPubli(int anoPubli) 
	{
		this.anoPubli = anoPubli;
	}
	
	public int getVolume() 
	{
		return volume;
	}
	
	public void setVolume(int volume) 
	{
		this.volume = volume;
	}
	
	public int getNumero() 
	{
		return numero;
	}
	
	public void setNumero(int numero) 
	{
		this.numero = numero;
	}
	
	public int getPaginas() 
	{
		return paginas;
	}
	
	public void setPaginas(int paginas) 
	{
		this.paginas = paginas;
	}
	
	public int getnCitacoes() 
	{
		return nCitacoes;
	}
	
	public void setnCitacoes(int nCitacoes) 
	{
		this.nCitacoes = nCitacoes;
	}
	
	public int getDoi() 
	{
		return doi;
	}
	
	public void setDoi(int doi) 
	{
		this.doi = doi;
	}
	
	public ArrayList<Autor> getListaAutores() 
	{
		return listaAutores;
	}
	
	public void setListaAutores(ArrayList<Autor> listaAutores) 
	{
		this.listaAutores = listaAutores;
	}
	
	
}
