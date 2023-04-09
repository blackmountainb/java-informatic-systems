package trabalho2;
import java.util.Random;
import java.util.ArrayList;
/**
 * Esta classe representa cada partida dentro do jogo
 * @author Beatriz Negromonte e Rafael Molter
 *
 */
public class Partida
{
	Random random = new Random();
	private int distribuidor, pass_count, vencedor_partida, vencedor_empate;
	
	// retorna o vencedor da partida
	public int getVencedor_partida()
	{
		return vencedor_partida;
	}

	// define o vencedor da partida
	public void setVencedor_partida(int vencedor_partida)
	{
		this.vencedor_partida = vencedor_partida;
	}
	
	// retorna o 2º vencedor da partida caso seja empate
	public int getVencedor_empate()
	{
		return vencedor_empate;
	}

	// define o 2º vencedor da partida caso seja empate
	public void setVencedor_empate(int vencedor_empate)
	{
		this.vencedor_empate = vencedor_empate;
	}
	
	//define a quantidade de vezes que a jogada foi passada (usado durante o codigo para zerá-la)
	public void setPass_count(int pass_count)
	{
		this.pass_count = pass_count;
	}
	
	public int getPass_count() {
		return this.pass_count;
	}

	/**
	 * Construtor da partida
	 * No início, o jogador que distribui as peças é escolhido aleatoriamente, assim como quem começa o jogo. Depois, usa-se a variável vencedor_partida
	 * para determinar quem será o primeiro jogador para a próxima partida
	 */
	public Partida() {
		distribuidor = random.nextInt(4);
		pass_count = 0;
		vencedor_partida = random.nextInt(4);
	}
	
	public void setDistribuidor(int distribuidor)
	{
		this.distribuidor = distribuidor;
	}

	/**
	 * Método que determina o fim do jogo, começa com uma bandeira em falso que só é alterada para True se um dos jogadores atingir 50 pontos, demonstrando fim do jogo
	 * @param lista de jogadores
	 * @return boolean bandeira 
	 */
	public boolean fimJogo(ArrayList<Jogador> jogadores) {
		boolean flag = false;
		for(Jogador j: jogadores) {
			if(j.getPontos_total()>=50) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * Define o fim da partida, utilizando flag (boolean), inicialmente falso, que pode tornar-se verdadeiro caso todos os jogadores passem na mesma rodada ou caso um deles acabe as peças
	 * @param jogador
	 * @return boolean flag
	 */
	public boolean fimPartida(Jogador jogador) {
		boolean flag = false;
		
		if(jogador.getPecas().isEmpty()) {
			flag = true;    // percorre todos os jogadores para verificar se pelo menos um deles acabou com suas peças
			System.out.println("\n" + jogador.getNome() + " acabou com suas peças.");
			System.out.println("Fim da partida.");
		}
		
		else if(pass_count == 4) {   //se todos os jogadores passaram na mesma rodada (o jogo encalhou) ou um deles tiver acabado as peças, a partida acaba
			flag = true;
			System.out.println("\nO jogo encalhou.");
			System.out.println("Fim da partida.");
		}
		return flag;
	}

	/**
	 * Método para fazer a rotação entre os jogadores que irão distribuir as peças em cada partida
	 * @param lista de jogadores
	 */
	public void distribuicao(ArrayList<Jogador> jogadores, ArrayList<Domino> mesa) {
		if(distribuidor==4) {
			distribuidor = 0;
		}
		
		jogadores.get(distribuidor).distribuirPecas(jogadores,mesa);
		distribuidor++;  // passa para o jogador seguinte distribuir as peças na próxima partida
	}

	/**
	 * Método que imprime a ordem das jogadas, determinada aleatoriamente no inicio da partida
	 * @param lista de jogadores
	 */
	public void ordemJogada(ArrayList<Jogador> jogadores) {
		int cont=0, i = vencedor_partida;
		System.out.println("\nDefinindo a ordem da jogada...");
		darPausa(1000);
		System.out.println("\nOrdem de jogada: ");
		
		while(cont<4) {
			if(i==4) {
				i=0;
			}
			System.out.println(jogadores.get(i).getNome());
			i++;
			cont++;
		}
	}
	
	/**
	 * Método para pausar em determinados instantes da partida, para que o jogo pareça mais realista e o jogador consiga ler melhor as informações
	 * @param valor inteiro em milisegundos 
	 */
	public static void darPausa(int ms){
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
	
	/** 
	 * Método para aumentar o contador todas as vezes que for passada a vez
	 */
	public void passaVez() {
		this.pass_count++;
	}

	/**
	 * Retira todas as peças do tabuleiro, zera a lista de objetos do tabuleiro
	 * @param Lista tabuleiro
	 */
	public void resetaTabuleiro(ArrayList<Domino> tabuleiro) {
		tabuleiro.removeAll(tabuleiro);
	}
	
	/**
	 * Zera todos os pontos da partida, de cada jogador
	 * @param ArrayList de jogadores
	 */
	public void resetaPontos_partida(ArrayList<Jogador> jogadores){
		for(Jogador j: jogadores) {
			j.setPontos_partida(0);
		}
	}
}