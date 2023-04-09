package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import common.IBiblioteca;
import server.Autor;
import server.Publi;

public class Client
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		int cont = 0;
		while (true)
		{
			try
			{
				int resp, escolha = 0, ano, vol, num, numCit, pagI, pagF;
				String email="", senha, nome, apelido, afiliacao;
				String titulo, revista, doi;
				Registry registry = LocateRegistry.getRegistry("localhost", 8000);
				IBiblioteca database = (IBiblioteca) registry.lookup("biblioteca");
				
				System.out.print("...........................................................\n");
				System.out.print(".  Seja bem-vindo ao Bibliotecomania. Escolha uma opção:  .\n");
				System.out.print(database.menuInicial());
				resp = scan.nextInt();
				scan.nextLine();
				
				while(resp==1 || resp==2) {
					if (resp == 1)
					{
						System.out.println("Insira o email: ");
						email = scan.nextLine();
						System.out.println("Insira a senha: ");
						senha = scan.nextLine();
						boolean loginValido = database.login(email, senha);
						if(!loginValido) {
							System.out.println("\nEsse autor não está cadastrado. O que deseja fazer?");
							System.out.print(database.menuInicial());
							resp = scan.nextInt();
							scan.nextLine();
						}
						else {
							resp = 4;
						}
					}
					else if (resp == 2)
					{
						System.out.print("Nome: ");
						nome = scan.nextLine();
						System.out.print("Apelido: ");
						apelido = scan.nextLine();
						System.out.print("Email: ");
						email = scan.nextLine();
						System.out.print("Senha: ");
						senha = scan.nextLine();
						System.out.print("Afiliação: ");
						afiliacao = scan.nextLine();
						boolean autorExistente = database.criaConta(nome,apelido,email,senha,afiliacao);
						
						if(!autorExistente) {
							System.out.println("Sua conta foi criada!\n");
							resp = 4;  // sai forçosamente do ciclo
						}
						else {
							System.out.println("Esse autor/email já se encontra cadastrado. O que deseja fazer?");
							database.menuInicial();
							resp = scan.nextInt();
							scan.nextLine();
						}
					}
				}
				
				if(resp==3) {
					System.exit(0);
				}
				
				System.out.print(database.menuAutor());
				escolha = scan.nextInt();
				scan.nextLine();
				
				while (escolha != 6)
				{
					if (escolha == 1)
					{
						
						System.out.println(database.listarAno(email));
					}
					
					else if(escolha == 2)
					{
						System.out.println(database.listarCitacao(email));
					}
					
					else if(escolha == 3)
					{
						int nAutor;
						ArrayList<String> autores = new ArrayList<String>();
						
						System.out.print("Quantos autores tem a publicação? ");
						nAutor = scan.nextInt();
						scan.nextLine();
						
						for(int i=1; i<=nAutor; i++)
						{	
							System.out.println("Nome do "+i+"º autor: ");
							nome = scan.nextLine();
							System.out.println("Apelido do "+i+"º autor: ");
							apelido = scan.nextLine();
							apelido += ", "+nome;
							autores.add(apelido);
						}
						
						System.out.println("Título da publicação: ");
						titulo = scan.nextLine();
						System.out.println("Ano de publicação: ");
						ano = scan.nextInt();
						scan.nextLine();
						System.out.println("Revista: ");
						revista = scan.nextLine();
						System.out.println("Volume: ");
						vol = scan.nextInt();
						scan.nextLine();
						System.out.println("Número: ");
						num = scan.nextInt();
						scan.nextLine();
						System.out.println("Página de início: ");
						pagI = scan.nextInt();
						scan.nextLine();
						System.out.println("Pagina de fim: ");
						pagF = scan.nextInt();
						scan.nextLine();
						int[] pags = {pagI,pagF};
						System.out.println("Número de citações: ");
						numCit = scan.nextInt();
						scan.nextLine();
						System.out.println("DOI: ");
						doi = scan.nextLine();
						
						boolean publiExistente = database.adicionarPublicacao(autores, titulo, ano, revista, vol, num, pags, numCit, doi);
						if(!publiExistente)
						{
							System.out.println("Publicação adicionada.\n");	
						}
						else
						{
							System.out.println("A publicação já existe no sistema.\n");
						}
					}
					
					else if (escolha == 4)
					{
						ArrayList<Publi> listaCandidata = database.procurarPubli(email);
						
						if(listaCandidata.size()>0)
						{
							System.out.println(database.imprimePubli(listaCandidata));
							System.out.println("Quantas publicações deseja adicionar?");
							int N = scan.nextInt();
							scan.nextLine();
							System.out.println();
							
							int[] add = new int[N];
							
							for(int i=1; i<=N; i++) {
								System.out.print(i + "ª publicação a adicionar: ");
								add[i-1] = scan.nextInt();
								scan.nextLine();
								
							}
							database.adicionarPubCandidatas(email, listaCandidata, add);
							System.out.println("\nOperação bem sucedida.\n");
						}
						else
						{
							System.out.println("\nSentimos muito, não existem publicações candidatas para si.\n");
						}
					}
					
					else if (escolha == 5)
					{
						
						System.out.println(database.estatisticas(email));
					}
					
					System.out.print(database.menuAutor());
					escolha = scan.nextInt();
					scan.nextLine();
				}
				System.out.println("\nLogout efetuado.\n");
				database.write();  //atualiza o arquivo da base de dados
			}
			catch (Exception a)
			{
				try
				{
					cont++;
					System.err.println("Servidor off, tentando novamente em 3 segundos. Tentativa nº " + cont);
					a.printStackTrace();
					Thread.sleep(3000);
					if (cont == 10)
					{
						System.out.println("Sessão encerrada");
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
