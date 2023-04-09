package server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import common.IBiblioteca;

public class BibliotecaImpl extends UnicastRemoteObject implements IBiblioteca{
	
	//private String email, senha, nome, afiliacao;
	private ArrayList<Autor> baseDados = new ArrayList<Autor>();
	
	public BibliotecaImpl() throws RemoteException
	{
		
	}
	
	public boolean criaConta(String nome, String apelido, String email, String senha, String afiliacao) throws RemoteException
	{
		Autor novoAutor = new Autor(nome,apelido,email,senha,afiliacao);
		boolean autorExistente=false;
		
		for(Autor autor: baseDados) {
			if(autor.getEmail().equals(email)) {   // o email é o identificador unívoco do autor, por isso basta para saber se ja esta cadastrado
				autorExistente = true;
				return autorExistente;
			}
		}
		
		baseDados.add(novoAutor);
		return autorExistente;
	}
	
	public boolean login(String email, String senha) throws RemoteException
	{
		for(Autor autor: baseDados) {
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
	
	public String menuInicial() throws RemoteException{
		String menu;
		menu =  "...........................................................\n";
		menu += ". 1 - Fazer login                                         .\n";
		menu += ". 2 - Registar novo autor                                 .\n";
		menu += ". 3 - Sair                                                .\n";
		menu += "...........................................................\n";
		return menu;
	}
	
	public String menuAutor() throws RemoteException{
		String menu = "******** Bem vindo! ********\n";
		menu += "1 - Listar publicações por ano (mais recente primeiro)\n";
		menu += "2 - Listar publicações por citações (mais citadas primeiro)\n";
		menu += "3 - Adicionar publicação\n";
		menu += "4 - Procurar publicações\n";
		menu += "5 - Remover publicação\n";
		menu += "6 - Estatísticas do autor\n";
		menu += "7 - Logout\n";
		return menu;
	}
	/*
	public Publi[] listaAno(Publi publi) throws RemoteException
	{
		
	}
	*/
	
	
}
