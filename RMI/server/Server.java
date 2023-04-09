package server;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Server {
	public static void main(String[] args)
	{
		/*
		FileOutputStream fileOut = new FileOutputStream("file.txt");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		FileInputStream fileIn = new FileInputStream("file.txt");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		*/
		Autor autor = new Autor("c","g","g","k","u");
		try
		{
			BibliotecaImpl obj = new BibliotecaImpl();
			Registry registry = LocateRegistry.createRegistry(8000);
			registry.rebind("biblioteca", obj);
			System.out.println("Servidor inicializado.");
			
		}
		catch(Exception e)
		{
			System.err.println("Ocorreu um erro:");
			//e.printStackTrace();
		}
	}

}
