package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import common.IBiblioteca;
import java.util.Hashtable;

public class BibliotecaImpl extends UnicastRemoteObject implements IBiblioteca {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Publi> dadosPubli = new ArrayList<Publi>();
	private ArrayList<Autor> dadosAutor = new ArrayList<Autor>();
	
	public void setDadosPubli(ArrayList<Publi> dadosPubli)
	{
		this.dadosPubli = dadosPubli;
	}

	public void setDadosAutor(ArrayList<Autor> dadosAutor)
	{
		this.dadosAutor = dadosAutor;
	}

	public BibliotecaImpl() throws RemoteException
	{
		
	}
	
	public boolean criaConta(String nome, String apelido, String email, String senha, String afiliacao) throws RemoteException
	{
		Autor novoAutor = new Autor(nome,apelido,email,senha,afiliacao);
		boolean autorExistente=false;
		
		for(Autor autor: dadosAutor) {
			if(autor.getEmail().equals(email)) {   // o email é o identificador unívoco do autor, por isso basta para saber se ja esta cadastrado
				autorExistente = true;
				return autorExistente;
			}
		}
		
		dadosAutor.add(novoAutor);
		return autorExistente;
	}
	
	public boolean login(String email, String senha) throws RemoteException
	{
		for(Autor autor: dadosAutor) {
			if(autor.getEmail().equals(email)) {
				if(autor.getSenha().equals(senha)) {
					System.out.println("Login efetuado com sucesso.");
					return true;
				}
			}
		}
		System.out.println("Esse autor não está registado na base de dados.");
		return false;
	}
	
	public boolean adicionarPublicacao(ArrayList<String> autores, String title, int anoPubli, String revista, int vol, int num, int[] pag, int cit, String doi) throws RemoteException 
	{
		Publi novaPubli = new Publi(autores, title, anoPubli, revista, vol, num, pag, cit, doi);
		boolean publiExistente = false;
		
		for(Publi publi: dadosPubli)
		{
			if(publi.getDoi().equals(doi))
			{
				//À semelhança do email do autor, o DOI é um identificador único para a publicação, então, se já estiver cadastrado, a publicação já está na base de dados
				publiExistente = true;
				return publiExistente;
			}
		}
		System.out.println("A publicação foi cadastrada.");
		dadosPubli.add(novaPubli);
		return publiExistente;
	}
	
	public String imprimePubli(ArrayList<Publi> p) throws RemoteException{
		String str="\n";
		for(int i = 0; i<p.size(); i++) {
			str += i + " - " + p.get(i).toString();
			str += "\n\n";
		}
		return str;
	}
	
	public String listarAno(String email) throws RemoteException
	{
		String str="";
		for (Autor autor: dadosAutor) {
			if(autor.getEmail().equals(email)){   // encontrou o autor em questao
				ArrayList<Publi> publiAssociadas = new ArrayList<Publi>(autor.getpubAssociadas());  // cria copia das publicaçoes do autor
				Collections.sort(publiAssociadas);
				Collections.reverse(publiAssociadas);
				str = imprimePubli(publiAssociadas);
				if(str.isBlank()) {
					return "Não há publicações cadastradas em seu nome!";
				}
				return str;
			}
		}
		return str;
	}
	
	public String listarCitacao(String email) throws RemoteException {
		String str="";
		 
		for(Autor autor: dadosAutor) {
			if(autor.getEmail().equals(email)) {
				ArrayList<Publi> publiAssociadas = new ArrayList<Publi>(autor.getpubAssociadas());
				Integer[] citacoes = new Integer[publiAssociadas.size()];
				for(int i=0; i<citacoes.length; i++) {
					citacoes[i] = publiAssociadas.get(i).getnCitacoes();
				}
				Arrays.sort(citacoes,Collections.reverseOrder());  //ordena em ordem decrescente de citações
				for(int i=0; i<citacoes.length; i++) {
					for(Publi p: publiAssociadas) {
						if(p.getnCitacoes()==citacoes[i]) {
							str += i + " - " + p.toString();
							str += "\n\n";
							publiAssociadas.remove(p);
							break;
						}
					}
				}
				if(str.isEmpty()) {
					return "Não há publicações cadastradas em seu nome!";
				}
				return str;
			}
		}
		return str;
	}
	
	public String estatisticas(String email) throws RemoteException
	{
		int citTotal = 0, i10 = 0;
		
		for(Autor autor: dadosAutor) 
		{
			if(autor.getEmail().equals(email)) {
				Hashtable<Integer,Integer> citArtigos = new Hashtable<Integer,Integer>();   // ira guardar o numero de citacoes e a frequencia em que aparece
				for(Publi publi: autor.getpubAssociadas())
				{	
					int N = publi.getnCitacoes();
					citTotal += N;
					if (N >= 10)
					{
						i10++;
					}
				}
				
				int iH, i;
				for(iH=0; iH<autor.getpubAssociadas().size();iH++) {
					int cont=0;
					for(i=0; i<autor.getpubAssociadas().size(); i++) {
						if(autor.getpubAssociadas().get(i).getnCitacoes()>=iH) {
							cont++;
							if(cont>=iH) {
								break;
							}
						}
					}
					if(i==autor.getpubAssociadas().size()-1) {  // se chegou ao final do for sem passar pelo break, encontramos o iH
						break;
					}
					
				}
			
				String est =  "\n********** Suas estatísticas: **********\n";
				       est += "****************************************\n";
				       est += "Número total de citações: " + citTotal + "\nÍndice i10: " + i10 + "\nÍndice H: " + iH + "\n";
				       est += "****************************************\n";
				       
				return est;
			}
		}
		return "";
	}
	
	public ArrayList<Publi> procurarPubli(String email) throws RemoteException
	{
		ArrayList<Publi> pubCandidatas = new ArrayList<Publi>();
		for (Autor autor: dadosAutor) {
			if(autor.getEmail().equals(email)) {
				String nome = autor.getNome();
				
				for(Publi publi: dadosPubli)
				{
					if(publi.getListaAutores().contains(nome) && !autor.getpubAssociadas().contains(publi)) {
						System.out.println("Adicionei uma publicação candidata à lista do autor.\n");
						pubCandidatas.add(publi);
					}	
				}
				return pubCandidatas;
			}
		}
		return pubCandidatas;
	}
	
	public void adicionarPubCandidatas(String email, ArrayList<Publi> pubCandidatas, int[] addPub) throws RemoteException
	{
		for(Autor autor: dadosAutor)
		{
			if(autor.getEmail().equals(email)) {
				for(int publi: addPub) {
					autor.addPubAssociada(pubCandidatas.get(publi));
				}
			}
		}
	}
	
	public String menuInicial() throws RemoteException
	{
		String menu;
		menu =  "...........................................................\n";
		menu += ". 1 - Fazer login                                         .\n";
		menu += ". 2 - Registar novo autor                                 .\n";
		menu += ". 3 - Sair                                                .\n";
		menu += "...........................................................\n";
		return menu;
	}
	
	public String menuAutor() throws RemoteException
	{	
		String menu="";
		menu += "\n**************************** MENU *****************************\n";
		menu += "***************************************************************\n";
		menu += "* 1 - Listar publicações por ano (mais recentes primeiro)     *\n";
		menu += "* 2 - Listar publicações por citações (mais citadas primeiro) *\n";
		menu += "* 3 - Adicionar publicação                                    *\n";
		menu += "* 4 - Procurar e adicionar publicações candidatas             *\n";
		menu += "* 5 - Estatísticas do autor                                   *\n";
		menu += "* 6 - Logout                                                  *\n";
		menu += "***************************************************************\n";
		return menu;
	}
	
	public void write() throws RemoteException {
		//File file = new File("baseDados.txt");
		try {
			FileOutputStream fileOut = new FileOutputStream("baseDados.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(dadosPubli);
			out.writeObject(dadosAutor);

			fileOut.close();
			out.close();
		}
		catch(Exception e) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void read() throws RemoteException {
		try {
			FileInputStream fileIn = new FileInputStream("baseDados.txt");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			this.dadosPubli = (ArrayList<Publi>) in.readObject();
			this.dadosAutor = (ArrayList<Autor>) in.readObject();
			fileIn.close();
			in.close();
		}
		catch(Exception e) {
			
		}
	}
}
