package trabalho2;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
		ArrayList<Domino> mesa = geraPecas();
		
		System.out.print("Nome do utilizador: ");
		jogadores.add(new Jogador(sc.nextLine(),false));
		jogadores.add(new Jogador("Robô 1",true));
		jogadores.add(new Jogador("Robô 2",true));
		jogadores.add(new Jogador("Robô 3",true));
		
		
		partida.ordemJogada(jogadores);
		partida.distribuicao(jogadores,mesa);
		
		while(!partida.fimJogo(jogadores)) {
				int vez = partida.getVencedor_partida();
				while(vez<=4) {
					if(vez==4) {
						vez=0;   // se tiver chegado ao quarto jogador e ainda faltar alguem a jogar, volta ao inicio
					}
					jogadores.get(vez).jogada(mesa, partida);
					
					if(partida.fimPartida(jogadores.get(vez))) {
						partida.setPass_count(0);  // o contador de jogadas passadas é zerado para a próxima partida
						//partida.resetaTabuleiro(mesa);
						Jogador.calculaPontos(jogadores, false, partida);
						if(!partida.fimJogo(jogadores)) { 
							partida.resetaPontos_partida(jogadores);
							partida.ordemJogada(jogadores);
							partida.distribuicao(jogadores,mesa);
						}
						break;
					}
					vez++;
				}
		}
		System.out.println("\nFim do jogo.");
		Jogador.calculaPontos(jogadores, true, partida);
		sc.close();
	}
	
	/**
	 * Este método gera todas as peças do jogo 
	 * @return uma lista com todas as peças
	 */
	public static ArrayList<Domino> geraPecas() {
		ArrayList<Domino> pecas = new ArrayList<Domino>();
		for(int i=0;i<=6;i++) {
			for(int j=0; j<=i;j++) {
				Domino peca = new Domino(i,j);
				pecas.add(peca);
			}
		}
		return pecas;
	}
}