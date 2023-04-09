package trabalho3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente
{
public static void main(String args[]) throws IOException{

	while(true) {//permite que outro cliente se conecte após o anterior terminar sua sessão
		Scanner sc = new Scanner(System.in);
		String resposta, usuario, senha;
		
		do {
			System.out.println("Deseja se conectar ao servidor? Digite Y para sim ou N para não");
			resposta = sc.nextLine();
		
		}
		while(!resposta.equals("Y") && !resposta.equals("N"));
		
		if(resposta.equals("N")) {
			System.out.println("Que pena. Até a próxima.");
			System.exit(0);
		}
		
		System.out.println("Eba, vamos começar!");
		int escolhaMenu=1;
		do {
			Socket socket = new Socket("localhost",1234);
			
			InputStream in = socket.getInputStream();
			DataInputStream dataIn = new DataInputStream(in);
			
			OutputStream out = socket.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			boolean respostaInvalida;
			do {
				System.out.println("Você já tem credenciais salvas no servidor? [Y/N]");
				resposta = sc.nextLine();
				dataOut.writeUTF(resposta);
				dataOut.flush();
				respostaInvalida = dataIn.readBoolean();
				System.out.println(dataIn.readUTF());
				boolean usuarioExistente;
				if(resposta.equals("N")) {
					System.out.println("Não tem problema! Vamos criar uma nova conta para você.");
					do {
						System.out.println("Digite um novo nome de usuário: ");
						usuario = sc.nextLine();
						dataOut.writeUTF(usuario);
						dataOut.flush();
						usuarioExistente = dataIn.readBoolean();
						if(usuarioExistente) {
							System.out.println(dataIn.readUTF());
						}
					}
					while(usuarioExistente);
					
					System.out.println("Digite uma nova senha:");
					senha = sc.nextLine();
					dataOut.writeUTF(senha);
					dataOut.flush();
					// mensagem a dizer que a credencial foi criada
					System.out.println(dataIn.readUTF()); 
				}
				if(resposta.equals("Y") || resposta.equals("N")){
					System.out.print("Usuário: ");
					usuario = sc.nextLine();
					dataOut.writeUTF(usuario);
					dataOut.flush();
					System.out.print("Senha: ");
					senha = sc.nextLine();
					dataOut.writeUTF(senha);
					dataOut.flush();
					boolean credencialInvalida = dataIn.readBoolean();
					if(!credencialInvalida) {
						System.out.println("Oba! Usuário e senha corretos. Vamos começar o jogo.");
						do {
							boolean invalidDisc;
						
							do {
								System.out.println("Com quantos discos você vai jogar?");
								int N = sc.nextInt();
								sc.nextLine();
								dataOut.writeInt(N);
								dataOut.flush();
								invalidDisc = dataIn.readBoolean();
								String mensagem = dataIn.readUTF();
								System.out.println(mensagem);
							}
							while(invalidDisc);
							
							boolean invalidPIN;
							do {
								System.out.println("Insira o PIN de origem: ");
								String pinOrigem = sc.nextLine();
								dataOut.writeUTF(pinOrigem);
								dataOut.flush();
								invalidPIN = dataIn.readBoolean();
								System.out.println(dataIn.readUTF());
							}
							while(invalidPIN);
							do {
								System.out.println("Insira o PIN de destino: ");
								String pinDestino = sc.nextLine();
								dataOut.writeUTF(pinDestino);
								dataOut.flush();
								invalidPIN = dataIn.readBoolean();
								System.out.println(dataIn.readUTF());
							}
							while(invalidPIN);
							//imprime a mensagem do numero minimo de jogadas
							System.out.println(dataIn.readUTF());
							String escolha;
							boolean jogadorVenceu, jogadorDesistiu;
							do {
								//imprime o estado atual do jogo
								System.out.println(dataIn.readUTF());
								//imprime o numero atual de jogadas
								System.out.println(dataIn.readUTF());
								System.out.println("Escolha sua jogada:");
								System.out.printf("1: A->B \t 2: A->C \t 3: B->A \t 4: B->C \t 5: C->A \t 6: C->B \t Outro: Sair do jogo\n");
								escolha = sc.nextLine();
								dataOut.writeUTF(escolha);
								dataOut.flush();
								// independente da escolha, há sempre uma mensagem ao final (mesmo que vazia)
								// a mensagem refere-se ao estado da jogada: se foi bem sucedida, a mensagem é vazia; se foi mal-sucedida, mostra o motivo
								System.out.println(dataIn.readUTF());
								jogadorVenceu = dataIn.readBoolean(); //ao final de cada jogada verifica se o jogador venceu
								System.out.println("Jogador venceu: " + jogadorVenceu);
							}
							while((escolha.equals("1") || escolha.equals("2") || escolha.equals("3") || escolha.equals("4") || escolha.equals("5") || escolha.equals("6")) && !jogadorVenceu);
							// mostra a mensagem se o jogador venceu
							System.out.println(dataIn.readUTF());
							// o cliente escolheu outra opção ou venceu e então o servidor mostrará o menu
							String menu = dataIn.readUTF();
							System.out.println(menu);
							escolhaMenu = sc.nextInt();
							sc.nextLine();
							dataOut.writeInt(escolhaMenu);
							dataOut.flush();
						}
						while(escolhaMenu==1);
						System.out.println(dataIn.readUTF());
						System.out.println(dataIn.readUTF());
					}
						
					else {
						System.out.println("Usuário ou senha incorretos! A comunicação com o servidor foi encerrada. Quer tentar novamente? [Y/N]");
						resposta = sc.nextLine();
						if(resposta.equals("N")) {
							System.exit(0);
						}
					}
					}
				}
				while(respostaInvalida);
			}
			while(escolhaMenu!=3);
}
}
}
