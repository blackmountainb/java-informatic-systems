package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Publi implements java.io.Serializable, Comparable<Publi>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titulo, revista, doi;
	private int anoPubli, volume, numero, nCitacoes;
	private int[] paginas = new int[2];
	private ArrayList<String> listaAutores = new ArrayList<String>();
	
	public Publi(ArrayList<String> autores, String title, int anoPubli, String revista, int vol, int num, int[] pag, int cit, String doi) throws RemoteException
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
	
	public int[] getPaginas() 
	{
		return paginas;
	}
	
	public void setPaginas(int[] paginas) 
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
	
	public String getDoi() 
	{
		return doi;
	}
	
	public void setDoi(String doi) 
	{
		this.doi = doi;
	}
	
	public ArrayList<String> getListaAutores() 
	{
		return listaAutores;
	}
	
	public void setListaAutores(ArrayList<String> listaAutores) 
	{
		this.listaAutores = listaAutores;
	}
	
	@Override
	public String toString()
	{
		String publiText="";
		for(String autor: listaAutores) {
			publiText += autor;
			publiText += "; ";
		}
		
		publiText += "(" + anoPubli + "). " + titulo + "; " + revista + ", volume " + volume +
				", número " + numero + " págs. " + paginas[0] + "-" + paginas[1] + ", doi " + doi + "; número de citações: " + nCitacoes;
		return publiText;
	}
	
	@Override
	public int compareTo(Publi other) {
		return Integer.compare(this.anoPubli, other.getAnoPubli());
	}
	
}
