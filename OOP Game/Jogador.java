package trabalho2;

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Esta classe representa o jogador do dominó
 * @author Beatriz Negromonte e Rafael Molter
 *
 */
public class Jogador
{
	private String nome;
	private int pontos_partida, pontos_total;
	private ArrayList<Domino> pecas;
	boolean robo;
	
	public String getNome()
	{
		return nome;
	}

	/**
	 * Construtor do objeto
	 * Tem como atributos os pontos de cada partida, os pontos no jogo inteiro, o nome e se é um bot (computador a jogar) ou humano
	 * @param String nome
	 * @param boolean robo, se o jogador é um robo ou não
	 */
	public Jogador(String nome, boolean robo) {
		this.nome = nome;
		pontos_partida = 0;
		pontos_total = 0;
		pecas = new ArrayList<Domino>();
		this.robo = robo;
	}
	
	public ArrayList<Domino> getPecas()
	{
		return pecas;
	}
	
	public int getPontos_total()
	{
		return pontos_total;
	}
	
	public void setPontos_partida(int pontos_partida)
	{
		this.pontos_partida = pontos_partida;
	}

	/**
	 * Método para calcular os pontos ao fim da partida e ao fim do jogo, assim como definir o(s) vencedor(es) de cada partida e vencedor(es) do jogo ao fim
	 * @param Lista com os jogadores
	 * @param boolean fimJogo, para definir se o jogo terminou (True) ou não (False)
	 * @param partida
	 */
	public static void calculaPontos(ArrayList<Jogador> jogadores, boolean fimJogo, Partida partida) {
		int vencedor_partida=0, vencedor_jogo=0, vencedor2_empate = 0, minimo_partida = 50, minimo_jogo = 50, minimo2_partida=50, minimo2_jogo = 49, vencedor_empate=0;		
		if(!fimJogo) {  //só imprime os pontos da partida se o jogo não tiver acabado
			for(Jogador j: jogadores) {
				if (j.pecas.size()==1 && j.pecas.get(0).soma()==0) {
					j.pontos_partida = 10;
					j.pontos_total += j.pontos_partida;
				}
				else {
					for (Domino peca: j.pecas) {
						j.pontos_partida += peca.soma();
					}
					j.pontos_total += j.pontos_partida;
				}
			}
			
			System.out.println("\nPontos da partida: \n");
			for(Jogador j: jogadores) {
				System.out.println(j.nome + ": " + j.pontos_partida + " pontos.");
				if(j.pontos_partida<=minimo_partida) {
					if(j.pontos_partida==minimo_partida) {
						minimo2_partida = j.pontos_partida;
						vencedor_empate = jogadores.indexOf(j);
					}
					else {
						minimo_partida = j.pontos_partida;
						vencedor_partida = jogadores.indexOf(j);
						partida.setVencedor_partida(vencedor_partida);
					}
				}
			}
			if(minimo2_partida == minimo_partida) { // verifica que os pontos de empate foram iguais, para jogadores diferentes
				// para evitar que imprima um valor por 2 jogadores terem igual pontuação, mas esta pontuação não ser a menor
				partida.setVencedor_empate(vencedor_empate);
				System.out.println("\nEmpate declarado!");
				System.out.println("\nVencedores da partida: " + jogadores.get(vencedor_empate).nome + 
						" e " + jogadores.get(vencedor_partida).nome + " com " + minimo_partida + " pontos.");
			}
			else {
				System.out.println("\nVencedor da partida: " + jogadores.get(vencedor_partida).nome + " com " + minimo_partida + " pontos.");
			}
			Partida.darPausa(3000);
			System.out.println("\nPontos acumulados:\n");
			
			for(Jogador j: jogadores) {
				System.out.println(j.nome + ": " + j.pontos_total + " pontos.");
			}
		}
		
		// se for o fim do jogo, verifica quem foi o vencedor
		else if(fimJogo) {
			for(Jogador j: jogadores) {
				if(j.pontos_total<=minimo_jogo) {
					if(j.pontos_total==minimo_jogo) {
						minimo2_jogo = j.pontos_total;
						vencedor2_empate = jogadores.indexOf(j);
					}
					else {
						minimo_jogo = j.pontos_total;
						vencedor_jogo = jogadores.indexOf(j);
					}
				}
			}
			if(minimo2_jogo == minimo_jogo) {
				partida.setVencedor_empate(vencedor2_empate);
				System.out.println("\nEmpate declarado: ");
				System.out.println("\nVencedor da jogo: " + jogadores.get(vencedor2_empate).nome + " com " + minimo2_jogo + " pontos.");
			}
			System.out.println("\nVencedor do jogo: " + jogadores.get(vencedor_jogo).nome + " com " + minimo_jogo + " pontos.");
		}
		Partida.darPausa(3000);
	}
	
	/**
	 * Método que imprime o nome de todos os objetos da lista de jogadores
	 */
	public static void toString(ArrayList<Jogador> jogadores) {
		for (Jogador j: jogadores){
			System.out.println(j.nome);
		}
	}

	/**
	 * Método que distribui as peças aleatoriamente entre os jogadores
	 * @param lista de jogadores
	 */
	public void distribuirPecas(ArrayList<Jogador> jogadores, ArrayList<Domino> mesa) {
		
		Random random = new Random();
		ArrayList<Jogador> jogadores_ordem = new ArrayList<Jogador>();
		int pos,cont=0, i = jogadores.indexOf(this) + 1;
		
		// ciclo que coloca em ordem de distribuição correta
		while(cont<4) {
			if(i==4) {  //se o indice é 4, ja chegou ao fim da lista e deve voltar ao inicio para adicionar os jogadores que faltaram
				i = 0;   
			}
			jogadores_ordem.add(jogadores.get(i));
			cont++;
			i++;
		}
		
		//transferindo todas as peças do jogo ao distribuidor
		for(int j=0; j<3; j++) {
			jogadores_ordem.get(j).transferePecas(this);
		}
		
		this.getPecas().addAll(mesa);
		mesa.removeAll(mesa);
		
		Partida.darPausa(2000);
		System.out.println("\nOrdem de distribuição:");
		toString(jogadores_ordem);
		System.out.println("\n" + this.nome + " está distribuindo as peças...\n");
	
		// adicionando as peças aos outros jogadores
		for(int j=0; j<3; j++) {
			Partida.darPausa(1000);
			System.out.println(jogadores_ordem.get(j).nome + " está recebendo as peças...");
			
			for (int k=0; k<7; k++) {
				pos = random.nextInt(this.pecas.size()); 
				jogadores_ordem.get(j).pecas.add(this.pecas.get(pos));
				this.pecas.remove(pos);
			}
		}
		System.out.println();
	}
	
	/**
	 * Método que exclui todas as peças remanescentes dos jogadores, ao final de uma partida
	 * @param jogadores
	 */
	public void transferePecas(Jogador distribuidor){
		distribuidor.getPecas().addAll(this.pecas);
		this.pecas.removeAll(pecas);
	}
	
	/**
	 * Método que define a jogada, tanto para o jogador humano quanto para os bots dentro do jogo
	 * @param Lista de dominós na mesa
	 * @param partida
	 */
	public void jogada(ArrayList<Domino> mesa, Partida partida) {
		int aux_troca, cont=0, jogada = 0, encaixe = 0;
		Domino pecaJogada;
		Scanner scan = new Scanner(System.in);
		if(robo) {
			System.out.println("\n" + this.nome + " está jogando.");
			Partida.darPausa(700);
			for(Domino peca: pecas) {
				// escrevemos o código de forma que o robô joga na mesa a primeira peça que for possível
				// nao foi feita uma escolha de forma aleatoria para evitar que uma mesma peça invalida de se jogar seja escolhida
				// por acaso mais de uma vez. Assim, evitamos um desperdicio de tempo e recurso computacional
				if(mesa.size()==0) {
					mesa.add(peca);
					pecas.remove(peca);
					cont = 1; //variavel que servira para verificar se uma peça foi adicionada
					partida.setPass_count(0); // toda vez que um jogador joga, zeramos o contador de vezes passadas (pois a partida só encalha quando os 4 passam consecutivamente)
					break;
				}
				
				// da forma como a mesa é impressa, o atributo y é sempre o que fica livre e portanto, ao adicionar a peça ao final da mesa
				// devemos comparar com ele
				else if(peca.getX()==mesa.get(mesa.size()-1).getY()) {  // 
					mesa.add(peca);
					pecas.remove(peca);
					cont = 1;
					partida.setPass_count(0);
					break;
				}
				else if(peca.getY()==mesa.get(mesa.size()-1).getY()) {
					// os passos seguintes são para trocar os atributos x e y entre si
					// da forma como imprimimos a mesa, o x é sempre impresso antes e por isso precisamos fazer essa troca
					aux_troca = peca.getY();
					peca.setY(peca.getX());
					peca.setX(aux_troca);
					mesa.add(peca);
					pecas.remove(peca);
					cont = 1;
					partida.setPass_count(0);
					break;
				}
				
				// agora tambem devemos considerar que as peças podem ser adicionadas ao inicio
				// nesse caso, devemos comparar com o atributo X da peça da mesa
				else if(peca.getX()==mesa.get(0).getX()) {
					aux_troca = peca.getY();
					peca.setY(peca.getX());
					peca.setX(aux_troca);
					mesa.add(0, peca);
					pecas.remove(peca);
					cont = 1;
					partida.setPass_count(0);
					break;
				}
				
				else if(peca.getY()==mesa.get(0).getX()) {
					mesa.add(0, peca);  
					pecas.remove(peca);
					cont = 1;
					partida.setPass_count(0);
					break;
				}
			}
			// se nenhuma peça puder ser adicionada, só resta ao robo passar sua vez 
			if(cont==0) {
				System.out.println(this.nome + " passou sua vez.");
				partida.passaVez();
			}
		}
		
		else {
			if (mesa.size()==0){
				System.out.println("A mesa está vazia, escolha a peça para começar.");
			}
			/*
			//retire este comentário acima para jogar no modo preguiça
			// modo preguiça: mostra só a primeira e ultima peça (facilita a escolha da peça pelo utilizador)
			 else if (mesa.size()>2) { //momento que perdi a paciencia de ver tanta peça
				System.out.println("\nMesa de jogo:\n");
				mesa.get(0).imprime();
				System.out.println("...");
				mesa.get(mesa.size()-1).imprime();
				partida.darPausa(300);
			}
			*/
			else {
				Partida.darPausa(1000);
				System.out.println("\nMesa de jogo:\n");
				for(Domino peca: mesa) {
				peca.imprime();
				Partida.darPausa(300);
				}
				Partida.darPausa(700);
				
			}
			
			System.out.println("\nPeças disponíveis:");
			for(Domino peca: pecas) {
				System.out.println("\n" + pecas.indexOf(peca) + "):");
				peca.imprime();
			}
			System.out.println(pecas.size() + "): Passar vez");

			while(true) {
				System.out.print("\nSelecione sua jogada: ");
				jogada = scan.nextInt();
				if(jogada<0 || jogada>pecas.size()) {
					System.out.println("Essa jogada não existe. Escolha novamente.");
					continue;
				}
				
				System.out.println();
				if(jogada==pecas.size()) { //se o jogador escolher passar, verifica se ele ainda tem peças que encaixem no jogo e zera o contador, caso tenha
					for (int i=0;i<pecas.size();i++) {
						pecaJogada = pecas.get(i);
						if (pecaJogada.getX()==mesa.get(mesa.size()-1).getY() || pecaJogada.getY()==mesa.get(mesa.size()-1).getY() || pecaJogada.getX()==mesa.get(0).getX() || pecaJogada.getY()==mesa.get(0).getX()) {
							partida.setPass_count(0);
							break;
						}
					}
					if (partida.getPass_count()==0) { 
						break;
					}	
					else {
						partida.passaVez();
						break;
					}
				
				}
			
				else {
					partida.setPass_count(0);
					pecaJogada = pecas.get(jogada);
					if(mesa.size()==0) {
						mesa.add(pecaJogada);
						pecas.remove(pecaJogada);
						break;
					}
					else if(pecaJogada.getX()==mesa.get(0).getX() && pecaJogada.getY()==mesa.get(mesa.size()-1).getY()) {
						// para o caso da peça encaixar em dois lugares, sendo a face X da peça com a peça do topo e a face Y da peça com a de baixo
						while(true) {
							System.out.println("Sua peça encaixa em dois lugares. Onde deseja encaixar? Em cima (1) ou em baixo (2)");
							encaixe = scan.nextInt();
							if (encaixe==1) {
								aux_troca = pecaJogada.getY();
								pecaJogada.setY(pecaJogada.getX());
								pecaJogada.setX(aux_troca);
								mesa.add(0, pecaJogada);  
								pecas.remove(pecaJogada);
								break;
							}
							else if (encaixe==2) {
								aux_troca = pecaJogada.getY();
								pecaJogada.setY(pecaJogada.getX());
								pecaJogada.setX(aux_troca);
								mesa.add(pecaJogada);
								pecas.remove(pecaJogada);
								break;
							}
							else {
								System.out.println("Escolhe 1 ou 2 aí ou, zé mané");
							}
						}
						break;
					}
					else if(pecaJogada.getY()==mesa.get(0).getX() && pecaJogada.getX()==mesa.get(mesa.size()-1).getY()) {
						// para o caso da peça encaixar em dois lugares, sendo a face Y da peça com a peça do topo e a face X da peça com a peça de baixo
						while (true) {
							System.out.println("Sua peça encaixa em dois lugares. Onde deseja encaixar? Em cima (1) ou em baixo (2)");
							encaixe = scan.nextInt();
							if(encaixe==1) {
								mesa.add(0, pecaJogada);
								pecas.remove(pecaJogada);
								break;
							}
							else if (encaixe==2) {
								mesa.add(pecaJogada);
								pecas.remove(pecaJogada);
								break;
							}
							else {
								System.out.println("Escolhe 1 ou 2 aí ou, zé mané");
							}
						}
						break;
					}
					
					else if(pecaJogada.getY()==mesa.get(0).getX() && pecaJogada.getY()==mesa.get(mesa.size()-1).getY()) {
						// para o caso da peça encaixar em dois lugares, sendo a face Y da peça compativel com a peça do topo e com a de baixo
						while (true) {
							System.out.println("Sua peça encaixa em dois lugares. Onde deseja encaixar? Em cima (1) ou em baixo (2)");
							encaixe = scan.nextInt();
							if(encaixe==1) {
								mesa.add(0, pecaJogada);
								pecas.remove(pecaJogada);
								break;
							}
							else if (encaixe==2) {
								aux_troca = pecaJogada.getY();
								pecaJogada.setY(pecaJogada.getX());
								pecaJogada.setX(aux_troca);
								mesa.add(pecaJogada);
								pecas.remove(pecaJogada);
								break;
							}
							else {
								System.out.println("Escolhe 1 ou 2 aí ou, zé mané");
							}
						}
						break;
					}
					else if(pecaJogada.getX()==mesa.get(0).getX() && pecaJogada.getX()==mesa.get(mesa.size()-1).getY()) {
						// para o caso da peça encaixar em dois lugares, sendo a face X da peça compativel com a peça do topo e com a de baixo
						while (true) {
							System.out.println("Sua peça encaixa em dois lugares. Onde deseja encaixar? Em cima (1) ou em baixo (2)");
							encaixe = scan.nextInt();
							if(encaixe==1) {
								aux_troca = pecaJogada.getY();
								pecaJogada.setY(pecaJogada.getX());
								pecaJogada.setX(aux_troca);
								mesa.add(0, pecaJogada);  
								pecas.remove(pecaJogada);
								break;
							}
							else if (encaixe==2) {
								mesa.add(0, pecaJogada);
								pecas.remove(pecaJogada);
								break;
							}
							else {
								System.out.println("Escolhe 1 ou 2 aí ou, zé mané");
							}
						}
						break;
					}
						
					
					else if(pecaJogada.getX()==mesa.get(mesa.size()-1).getY()) { 
						mesa.add(pecaJogada);
						pecas.remove(pecaJogada);
						break;
					}
					else if(pecaJogada.getY()==mesa.get(mesa.size()-1).getY()) {
						aux_troca = pecaJogada.getY();
						pecaJogada.setY(pecaJogada.getX());
						pecaJogada.setX(aux_troca);
						mesa.add(pecaJogada);
						pecas.remove(pecaJogada);
						break;
					}
					else if(pecaJogada.getX()==mesa.get(0).getX()) {
						aux_troca = pecaJogada.getY();
						pecaJogada.setY(pecaJogada.getX());
						pecaJogada.setX(aux_troca);
						mesa.add(0, pecaJogada);  
						pecas.remove(pecaJogada);
						break;
					}
						
					else if(pecaJogada.getY()==mesa.get(0).getX()) {
						mesa.add(0, pecaJogada);
						pecas.remove(pecaJogada);
						break;
					}
					else {
						System.out.println("Essa peça não pode ser adicionada! Escolha novamente.");
					}
				}
			}
		}
	//scan.close();   //deixamos o Scanner aberto pois, do contrário, dava erro no código	
	}
}