package server;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Teste
{
	public void main (String agrs[]) throws RemoteException {
		
		ArrayList<Autor> dadosAutor = new ArrayList<Autor>();
		Autor a1 = new Autor("Rafael","Molter","rafaelmolter@hotmail.com","SI","Universidade de Coimbra");
		Autor a2 = new Autor("Beatriz","Santos", "negromontebs@gmail.com","SI","Universidade de Coimbra");
		dadosAutor.add(a1);
		dadosAutor.add(a2);
		int[] pags = {2,12};
		ArrayList<String> autores = new ArrayList<String>();
		autores.add(a1.getNome());
		autores.add(a2.getNome());
		a1.addPubAssociada(new Publi(autores,"blabla",2021,"haa",2,4,pags,3,"677hjkn89"));
		a1.addPubAssociada(new Publi(autores,"blabla",2020,"haa",2,4,pags,3,"677hjkn89"));
		a1.addPubAssociada(new Publi(autores,"blabla",2018,"haa",2,4,pags,3,"677hjkn89"));
		System.out.print(dadosAutor.get(0).getpubAssociadas().toString());
	}
}
