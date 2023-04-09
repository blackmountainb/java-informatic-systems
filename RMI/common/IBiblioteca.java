package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBiblioteca extends Remote 
{
	public boolean criaConta(String nome, String apelido, String email, String senha, String afiliacao) throws RemoteException;
	public boolean login(String email, String senha) throws RemoteException;
	public String menuInicial() throws RemoteException;
	public String menuAutor() throws RemoteException;
}
