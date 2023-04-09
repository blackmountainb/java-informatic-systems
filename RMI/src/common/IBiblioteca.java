package common;
import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import server.Autor;
import server.Publi;

public interface IBiblioteca extends Remote 
{
	public boolean criaConta(String nome, String apelido, String email, String senha, String afiliacao) throws RemoteException;
	public boolean login(String email, String senha) throws RemoteException;
	public boolean adicionarPublicacao(ArrayList<String> autores, String title, int anoPubli, String revista, int vol, int num, int[] pag, int cit, String doi) throws RemoteException;
	public ArrayList<Publi> procurarPubli(String email) throws RemoteException;
	public void adicionarPubCandidatas(String email, ArrayList<Publi> pubCandidatas, int[] addPub) throws RemoteException;
	public String estatisticas(String email) throws RemoteException;
	public String listarAno(String email) throws RemoteException;
	public String listarCitacao(String email) throws RemoteException;
	public String menuInicial() throws RemoteException;
	public String menuAutor() throws RemoteException;
	public String imprimePubli(ArrayList<Publi> p) throws RemoteException;
	public void write() throws RemoteException;
	public void read() throws RemoteException;
}
