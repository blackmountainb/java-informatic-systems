package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import common.IBiblioteca;

public class Client
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		int i = 0;
		while (true)
		{
			try
			{
				int resp, escolha = 0;
				String email, senha, nome, apelido, afiliacao, e, s;

				Registry registry = LocateRegistry.getRegistry("localhost", 8000);
				IBiblioteca database = (IBiblioteca) registry.lookup("biblioteca");
				/*
				FileOutputStream fileOut = new FileOutputStream("file.txt");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				FileInputStream fileIn = new FileInputStream("file.txt");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				*/
				System.out.print("...........................................................\n");
				System.out.print(".  Seja bem-vindo ao Bibliotecomania. Escolha uma opção:  .\n");
				System.out.print(database.menuInicial());
				resp = scan.nextInt();
				scan.next();
				
				while(resp==1 || resp==2) {
					if (resp == 1)
					{
						System.out.println("Insira o email: ");
						email = scan.next();
						System.out.println("Insira a senha: ");
						senha = scan.next();
						boolean loginValido = database.login(email, senha);
						if(!loginValido) {
							System.out.println("Esse autor não está cadastrado. O que deseja fazer?");
							database.menuInicial();
							resp = scan.nextInt();
							scan.next();
						}
						else {
							resp = 4;
						}
					}
					else if (resp == 2)
					{
						System.out.println("Nome: ");
						nome = scan.next();
						System.out.println("Apelido");
						apelido = scan.next();
						System.out.println("Email: ");
						email = scan.next();
						System.out.println("Senha: ");
						senha = scan.next();
						System.out.println("Afiliação: ");
						afiliacao = scan.next();
						boolean autorExistente = database.criaConta(nome,apelido,email,senha,afiliacao);
						
						if(!autorExistente) {
							System.out.println("Sua conta foi criada!");
							resp = 4;  // sai forçosamente do ciclo
						}
						else {
							System.out.println("Esse autor/email já se encontra cadastrado. O que deseja fazer?");
							database.menuInicial();
							resp = scan.nextInt();
							scan.next();
						}
					}
				}
				
				if(resp==3) {
					System.exit(0);
				}
				
				System.out.print(database.menuAutor());
				escolha = scan.nextInt();
				
				while (escolha != 7)
				{
					if (escolha == 1)
					{

					}

				}
			}
			catch (Exception a)
			{
				try
				{
					i++;
					System.err.println("Servidor off, tentando novamente em 3 segundos. Tentativa nº " + i);
					a.printStackTrace();
					Thread.sleep(3000);
					if (i == 10)
					{
						System.exit(1);
					}
				}
				catch (InterruptedException e)
				{

				}
			}
		}
	}

}
