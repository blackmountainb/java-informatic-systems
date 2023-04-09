package trabalho3;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Arrays;
import java.lang.NumberFormatException;
public class Servidor
{
public static void main(String args[]) throws IOException{
	
	ServerSocket s = new ServerSocket(1234);
	Hashtable<String,String> credenciais = new Hashtable<String,String>();
	String usuario,senha;
	credenciais.put("1", "1");
	int rodadas = 0, somaTotal= 0;
	
	while(true) {
		System.out.println("Poxa, me sinto tão sozinho :( Estou esperando alguém me dar bola...");
		
		Socket s1 = s.accept();
		
		InputStream in = s1.getInputStream();
		DataInputStream dataIn = new DataInputStream(in);
		
		OutputStream out = s1.getOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		
		System.out.println("Uhul, tenho um novo amigo para conversar!");
		
		boolean respostaInvalida;
		do {
			respostaInvalida=false;
			String resposta = dataIn.readUTF();
			if(resposta.equals("N")) {
				somaTotal = 0; 
				rodadas = 0;
				dataOut.writeBoolean(respostaInvalida);
				dataOut.flush();
				dataOut.writeUTF("");
				dataOut.flush();
				System.out.println("O cliente aparentemente não tem credenciais, tadinho. Vou ser bonzinho e criar uma nova para ele.");
				usuario = dataIn.readUTF();
				boolean usuarioExistente;
				while(credenciais.contains(usuario)) {
					System.out.println("Ops, esse usuário já existe, vou avisar ao cliente.");
					usuarioExistente = true;
					dataOut.writeBoolean(usuarioExistente);
					dataOut.flush();
					dataOut.writeUTF("Esse usuário já existe! Tente outro.");
					dataOut.flush();
					usuario = dataIn.readUTF();
				}
	
				usuarioExistente = false;
				dataOut.writeBoolean(usuarioExistente);
				dataOut.flush();
				senha = dataIn.readUTF();
				credenciais.put(usuario,senha);
				System.out.println("Nova credencial criada com sucesso.");
				dataOut.writeUTF("Usuário e senha criados com sucesso! Agora vamos entrar na sua conta.");
				dataOut.flush();
		
			}
			
			if(resposta.equals("Y") || resposta.equals("N")) {
				// se a resposta foi N, o servidor ja disse ao cliente se era valida ou nao, se foi Y, deve dizer agora
				if(resposta.equals("Y")) {  
					System.out.println("O cliente diz que tem credencial. Vamos ver se não está mentindo...");
					dataOut.writeBoolean(respostaInvalida);
					dataOut.flush();
					dataOut.writeUTF("");
					dataOut.flush();
				}
				usuario = dataIn.readUTF();
				senha = dataIn.readUTF();
				if(!credenciais.containsKey(usuario) || !credenciais.get(usuario).equals(senha)) {
					boolean credencialInvalida = true;
					dataOut.writeBoolean(credencialInvalida);
					dataOut.flush();
					System.out.println("O clubinho é fechado, tem segurança. Sem credencial válida não entra, po.");
					s1.close();
				}
				
				else {
					boolean credencialInvalida = false;
					dataOut.writeBoolean(credencialInvalida);
					dataOut.flush();
					System.out.println("Que alegria, deu match! Fomos feitos um pro outro :)");
					int[] A, B, C, discos = { 3, 5, 7, 9, 11, 13, 15, 17, 19, 21 };
					int N = 0, min_mov, i, contPinD = 0, j = 0, escolhaMenu=1;
					Hashtable <Integer, Integer[]> discosEstatisticas = new Hashtable <Integer, Integer[]>();
					Integer[] jogadas = {0,0,0,0,0,0,0,0,0,0,0};
					Integer[] rodadastotal = {0,0,0,0,0,0,0,0,0,0,0};
					String[] str = { "         ***         ", "        *****        ", "       *******       ",
							"      *********      ", "     ***********     ", "    *************    ", "   ***************   ",
							"  *****************  ", " ******************* ", "*********************" };
					String[] torre = { "          |          ", "          |          ", "          |          ",
							"          |          ", "          |          ", "          |          ", "          |          ",
							"          |          ", "          |          ", "          |          " };
					String menu = "---------- Selecione uma opção ----------\n";
					menu += "1: Jogar novamente\n";
					menu += "2: Ver estatísticas\n";
					menu += "3: Encerrar sessão\n";
					do {
						System.out.println("Estou esperando pelo número de discos.");
						N = dataIn.readInt();
						int n_jogadas = 0;
						boolean invalidDisc;
						while(N > 10 || N < 3) {
							invalidDisc = true;
							dataOut.writeBoolean(invalidDisc);
							dataOut.flush();
							dataOut.writeUTF("O intervalo permitido é de 3 a 10 discos! Digite novamente.");
							dataOut.flush();
							N = dataIn.readInt();
						}
						invalidDisc = false;
						dataOut.writeBoolean(invalidDisc);
						dataOut.flush();
						dataOut.writeUTF("");
						dataOut.flush();
						
						System.out.println("Sucesso. Esperando pela informação sobre origem e destino dos discos.");
						String pinOrigem = dataIn.readUTF();
						boolean invalidPin;
						while(!pinOrigem.equals("A") && !pinOrigem.equals("B") && !pinOrigem.equals("C")) {
							invalidPin = true;
							dataOut.writeBoolean(invalidPin);
							dataOut.writeUTF("PIN inválido! A, B ou C");
							pinOrigem = dataIn.readUTF();
						}
						invalidPin = false;
						dataOut.writeBoolean(invalidPin);
						dataOut.writeUTF("");
						dataOut.flush();
						System.out.println("Recebi a informação sobre PIN de origem.");
						String pinDestino = dataIn.readUTF();
						while((!pinDestino.equals("A") && !pinDestino.equals("B") && !pinDestino.equals("C")) || pinDestino.equals(pinOrigem)) {
							if(pinDestino.equals(pinOrigem)) {
								invalidPin = true;
								dataOut.writeBoolean(invalidPin);
								dataOut.writeUTF("Os PINS não podem ser iguais!");
								pinDestino = dataIn.readUTF();
							}
							else {
								invalidPin = true;
								dataOut.writeBoolean(invalidPin);
								dataOut.writeUTF("PIN inválido! A, B ou C");
								pinDestino = dataIn.readUTF();
							}
						}
						invalidPin = false;
						dataOut.writeBoolean(invalidPin);
						dataOut.writeUTF("");
						dataOut.flush();
						System.out.println("Recebi a informação sobre PIN de destino.");
						min_mov = (int) (Math.pow(2, N) - 1);
						System.out.println("Informando o jogador sobre número mínimo de jogadas");
						dataOut.writeUTF("O numero minimo de jogadas para vencer com " + N + " discos é " + min_mov + "\n");
						
						A = new int[N];
						B = new int[N];
						C = new int[N];
						// preenchendo todos os elementos com zero
						Arrays.fill(A, 0);
						Arrays.fill(B, 0);
						Arrays.fill(C, 0);
						int[][] colunas = {A,B,C};
						int pinO, pinD;
						if(pinOrigem.equals("A")) {
							pinO = 0;
						}
						else if(pinOrigem.equals("B")) {
							pinO = 1;
						}
						else {
							pinO = 2;
						}
						if(pinDestino.equals("A")) {
							pinD = 0;
						}
						else if(pinDestino.equals("B")) {
							pinD = 1;
						}
						else {
							pinD = 2;
						}
						String[] IA = new String[N];
						String[] IB = new String[N];
						String[] IC = new String[N];
						
						for (i = 0; i < N; i++)
						{
							colunas[pinO][i] = discos[N - 1 - i];  // preenchendo somente a coluna de origem com todos os discos
							IA[i] = torre[N - 1 - i];
							IB[i] = torre[N - 1 - i];  // preenchendo todas as colunas de print sem discos
							IC[i] = torre[N - 1 - i];
						}
						String[][] colunasPrint = {IA,IB,IC};
						for (i = 0; i < N; i++)
						{
							// preenchendo os discos de print somente na coluna de origem
							colunasPrint[pinO][i] = str[N - 1 - i];
						}
						int a=1;
					
						while (a==1 || a==2 || a==3 || a==4 || a==5 || a==6)
						{
							String jogo;
							jogo = "         [A]                  [B]                  [C]         \n";
							jogo += "          |                    |                    |         \n";
							for (i = N - 1; i >= 0; i--)
							{
								jogo+= colunasPrint[0][i] + colunasPrint[1][i] + colunasPrint[2][i] + "\n";
							}
							jogo+= "################################################################\n";
							dataOut.writeUTF(jogo);
							dataOut.flush();
							System.out.println("Informando do número de movimentos");
							dataOut.writeUTF("Número de movimentos: " + n_jogadas);
							dataOut.flush();
							System.out.println("Esperando pela jogada.");
							String escolha = dataIn.readUTF();
							boolean jogadorVenceu;
							try {
								
								a=Integer.parseInt(escolha);
								if (a == 1)
								{
									i = N - 1;
									j = 0;
									while (i >= 0)
									{
										if (colunas[0][i] == 0)
										{
											if (i == 0)
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Não há discos na coluna para serem movidos! Tente outra jogada.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
											else
											{
												i--;
												continue;
											}
										}
										else if (colunas[1][j] == 0)
										{
											if (j == 0)
											{
												colunas[1][j] = colunas[0][i];
												colunasPrint[1][j] = colunasPrint[0][i];
												colunas[0][i] = 0;
												colunasPrint[0][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;    
											}
											else if (colunas[0][i] < colunas[1][j - 1])
											{
												colunas[1][j] = colunas[0][i];
												colunasPrint[1][j] = colunasPrint[0][i];
												colunas[0][i] = 0;
												colunasPrint[0][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Você não pode colocar um disco maior em cima de um menor! Tente novamente.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
										}
										else
										{
											j++;
										}
									}
								}
								
								else if (a == 2)
								{
									i = N - 1;
									j = 0;
									while (i >= 0)
									{
										if (colunas[0][i] == 0)
										{
											if (i == 0)
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Não há discos na coluna para serem movidos! Tente outra jogada.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
											else
											{
												i--;
												continue;
											}
										}
										else if (colunas[2][j] == 0)
										{
											if (j == 0)
											{
												colunas[2][j] = colunas[0][i];
												colunasPrint[2][j] = colunasPrint[0][i];
												colunas[0][i] = 0;
												colunasPrint[0][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else if (colunas[0][i] < colunas[2][j - 1])
											{
												colunas[2][j] = colunas[0][i];
												colunasPrint[2][j] = colunasPrint[0][i];
												colunas[0][i] = 0;
												colunasPrint[0][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Você não pode colocar um disco maior em cima de um menor! Tente novamente.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
										}
										else
										{
											j++;
										}
									}
								}
								
								else if (a == 3)
								{
									i = N - 1;
									j = 0;
									while (i >= 0)
									{
										if (colunas[1][i] == 0)
										{
											if (i == 0)
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Não há discos na coluna para serem movidos! Tente outra jogada.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
											else
											{
												i--;
												continue;
											}
										}
										else if (colunas[0][j] == 0)
										{
											if (j == 0)
											{
												colunas[0][j] = colunas[1][i];
												colunasPrint[0][j] = colunasPrint[1][i];
												colunas[1][i] = 0;
												colunasPrint[1][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else if (colunas[1][i] < colunas[0][j - 1])
											{
												colunas[0][j] = colunas[1][i];
												colunasPrint[0][j] = colunasPrint[1][i];
												colunas[1][i] = 0;
												colunasPrint[1][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Você não pode colocar um disco maior em cima de um menor! Tente novamente.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
										}
										else
										{
											j++;
										}
									}
								}
								
								else if (a == 4)
								{
									i = N - 1;
									j = 0;
									while (i >= 0)
									{
										if (colunas[1][i] == 0)
										{
											if (i == 0)
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Não há discos na coluna para serem movidos! Tente outra jogada.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
											else
											{
												i--;
												continue;
											}
										}
										else if (colunas[2][j] == 0)
										{
											if (j == 0)
											{
												colunas[2][j] = colunas[1][i];
												colunasPrint[2][j] = colunasPrint[1][i];
												colunas[1][i] = 0;
												colunasPrint[1][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else if (colunas[1][i] < colunas[2][j - 1])
											{
												colunas[2][j] = colunas[1][i];
												colunasPrint[2][j] = colunasPrint[1][i];
												colunas[1][i] = 0;
												colunasPrint[1][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Você não pode colocar um disco maior em cima de um menor! Tente novamente.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
										}
										else
										{
											j++;
										}
									}
								}
								
								else if (a == 5)
								{
									i = N - 1;
									j = 0;
									while (i >= 0)
									{
										if (colunas[2][i] == 0)
										{
											if (i == 0)
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Não há discos na coluna para serem movidos! Tente outra jogada.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
											else
											{
												i--;
												continue;
											}
										}
										else if (colunas[0][j] == 0)
										{
											if (j == 0)
											{
												colunas[0][j] = colunas[2][i];
												colunasPrint[0][j] = colunasPrint[2][i];
												colunasPrint[2][i] = torre[i];
												colunas[2][i] = 0;
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else if (colunas[2][i] < colunas[0][j - 1])
											{
												colunas[0][j] = colunas[2][i];
												colunasPrint[0][j] = colunasPrint[2][i];
												colunas[2][i] = 0;
												colunasPrint[2][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Você não pode colocar um disco maior em cima de um menor! Tente novamente.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
										}
										else
										{
											j++;
										}
									}
								}
								
								else if (a == 6)
								{
									i = N - 1;
									j = 0;
									while (i >= 0)
									{
										if (colunas[2][i] == 0)
										{
											if (i == 0)
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Não há discos na coluna para serem movidos! Tente outra jogada.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
											else
											{
												i--;
												continue;
											}
										}
										else if (colunas[1][j] == 0)
										{
											if (j == 0)
											{
												colunas[1][j] = colunas[2][i];
												colunasPrint[1][j] = colunasPrint[2][i];
												colunas[2][i] = 0;
												colunasPrint[2][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else if (colunas[2][i] < colunas[1][j - 1])
											{
												colunas[1][j] = colunas[2][i];
												colunasPrint[1][j] = colunasPrint[2][i];
												colunas[2][i] = 0;
												colunasPrint[2][i] = torre[i];
												dataOut.writeUTF("Jogada bem sucedida.");
												dataOut.flush();
												break;
											}
											else
											{
												System.out.println("Jogada inválida.");
												dataOut.writeUTF("Você não pode colocar um disco maior em cima de um menor! Tente novamente.");
												dataOut.flush();
												n_jogadas--;
												break;
											}
										}
										else
										{
											j++;
										}
									}
								}
								else {
									System.out.println("O jogador escolheu sair do jogo.");
									dataOut.writeUTF("Que pena que desistiu.");
									dataOut.flush();
									jogadorVenceu = true;
									dataOut.writeBoolean(jogadorVenceu);
									dataOut.flush();
									dataOut.writeUTF("");
									dataOut.flush();
									dataOut.writeUTF(menu);
									System.out.println("Enviei o menu.");
									dataOut.flush();
									escolhaMenu = dataIn.readInt();
									System.out.println("Recebi a escolha.");
									a = 0;  // sairá do while do jogo
								}
								
								if(a>0 && a<=6) {  // só verifica se o jogador ganhou se ele nao tiver desistido
									n_jogadas++;
									for (int disco : colunas[pinD])  // verifica quantos discos na coluna de destino não são zero
									{                                
										if (disco != 0)
										{
											contPinD++;
										}
									}
				
									if(contPinD == N) {  //se todos forem diferentes de zero, a pessoa ganhou o jogo
										System.out.println("O jogador ganhou o jogo!");
										dataOut.flush();
										jogadorVenceu = true;
										dataOut.writeBoolean(jogadorVenceu);
										dataOut.flush();
										dataOut.writeUTF("Parabéns, você ganhou o jogo!");
										dataOut.flush();
										
										a = 10;  // sairá do while do jogo
										contPinD = 0;
									
									}
									else {
										jogadorVenceu = false;
										dataOut.writeBoolean(jogadorVenceu);
										dataOut.flush();
										contPinD = 0;
									}
								}
							}
						
							catch (NumberFormatException e) {
								System.out.println("O jogador escolheu sair do jogo.");
								jogadorVenceu = true;
								// em toda jogada o cliente fica a espera do boolean a dizer se o jogador venceu ou nao, indepentemente se ele escolheu desistir ou nao
								// apesar de nesse caso o jogador nao ter vencido, jogadorVenceu = true para obrigar que se saia do ciclo das jogadas no cliente
								dataOut.writeUTF("Que pena que desistiu.");
								dataOut.flush();
								dataOut.writeBoolean(jogadorVenceu);
								dataOut.flush();
								dataOut.writeUTF("");
								dataOut.flush();
								contPinD = 0;
								a = 0;  // sairá do while do jogo
							}
						}
						dataOut.writeUTF(menu);
						System.out.println("Enviei o menu.");
						dataOut.flush();
						escolhaMenu = dataIn.readInt();
						System.out.println("Recebi a escolha.");
						if(escolhaMenu==1) {
							System.out.println("O jogador escolheu jogar novamente.");
						}
						
						somaTotal = somaTotal + n_jogadas;
						if(escolhaMenu == 2) {
							int media;
							jogadas[N] = somaTotal;
							rodadas++;
							rodadastotal[N] = rodadas;
							
							for (int k=3;k<=10;k++) {
								if(rodadastotal[k]!=0) {
									media = jogadas[k]/rodadastotal[k];
									dataOut.writeUTF("O número médio de jogadas para "+k+" pinos foi "+media);
									dataOut.flush();
								}
								
							}
						}
					}
					while(escolhaMenu==1);
					
					if(escolhaMenu==3) {
						System.out.println("O jogador escolheu encerrar a sessão.");
						s1.close();
					}
				}
			}
			else {
				respostaInvalida = true;
				dataOut.writeBoolean(respostaInvalida);
				dataOut.flush();
				dataOut.writeUTF("Digite Y ou N!");
				dataOut.flush();
			}
		}
		while(respostaInvalida);
	}
}
}
