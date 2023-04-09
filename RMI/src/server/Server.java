package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;


public class Server {
	public static void main(String[] args)
	{
		try
		{
			BibliotecaImpl obj = new BibliotecaImpl();
			Registry registry = LocateRegistry.createRegistry(8000);
			registry.bind("biblioteca", obj);
			System.out.println("Servidor inicializado.");
			
			// adição de exemplos iniciais na base de dados
			obj.criaConta("Rafael","Molter","rafaelmolter@hotmail.com","alunoSI","Universidade de Coimbra");
			obj.criaConta("Beatriz","Santos", "negromontebs@gmail.com","alunoSI","Universidade de Coimbra");
			obj.criaConta("Mauro", "Pinto", "mauropinto@dei.uc.pt", "profSI", "Universidade de Coimbra");
			
			ArrayList<String> autores = new ArrayList<String>();
			
			autores.add("Molter, Rafael"); autores.add("Santos, Beatriz");
			int[] pag = {4,17};
			
			obj.adicionarPublicacao(autores,"A saga de um trabalho: um estudo contemporâneo sobre os efeitos psicológicos aos"
					+ " estudantes universitários",2021,"Revista O tão almejável 20",2,4,pag,3,"677hjkn89");
			obj.adicionarPublicacao(autores,"A saga de choro dos estudantes universitários",2018,"Revista O tão almejável 20",2,4,pag,10,"67kn89");

			
			ArrayList<String> autor = new ArrayList<String>();
			autor.add("Pinto, Mauro");
			pag[0] = 2; pag[1] = 8;
			
			obj.adicionarPublicacao(autor,"Java RMI: uma revisão ao trabalho desenvolvido",2021,
					"Revista Defesas de trabalho",1,2,pag,10,"677hhgnmjkn89");
			
			pag[0] = 1; pag[1] = 400;
			ArrayList<String> autores1 = new ArrayList<String>(autores);
			autores1.add("Pinto, Mauro");
			obj.adicionarPublicacao(autores1, "Um estudo de caso: alunos desesperados enchem a paciência"
					+ " de professores", 2021, "Revista Universitários também choram", 1, 5, pag, 6, "mjkn89");
			
			obj.read();
			
		}
		catch(Exception e)
		{
			System.err.println("Ocorreu um erro:");
			e.printStackTrace();
		}
	}

}
