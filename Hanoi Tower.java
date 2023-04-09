package Trabalho1;

import java.util.Scanner;

public class torre_hanoi
{
	public static void main(String[] args)
	{
		// inicializacao das variaveis
		int[] A, B, C, discos = { 3, 5, 7, 9, 11, 13, 15, 17, 19, 21 }; // os numeros representam a quantidade de
																		// asteriscos (o "diametro") de cada disco
																		// possivel no jogo
		Scanner sc = new Scanner(System.in);
		int N = 0, n_jogadas = 0, min_mov, i, contA_0 = 0, contB_0 = 0, j = 0, escolha = 0;
		String abort = "n";
		String[] str = { "         ***         ", "        *****        ", "       *******       ",
				"      *********      ", "     ***********     ", "    *************    ", "   ***************   ",
				"  *****************  ", " ******************* ", "*********************" };
		String[] torre = { "          |          ", "          |          ", "          |          ",
				"          |          ", "          |          ", "          |          ", "          |          ",
				"          |          ", "          |          ", "          |          " };

		// ciclo para que se introduza um valor valido (numero inteiro e dentro do intervalo) de numero de discos para
		// jogar

		while (N == 0)
		{
			System.out.println("Com quantos discos voce vai jogar? Insira um número entre 3 e 10");

			if (sc.hasNextInt()) // a entrada só é atribuida a variavel se for um numero inteiro
			{
				N = sc.nextInt();
				sc.nextLine();      // limpa o \n do scanner
				if (N > 10 || N < 3)    // se o numero introduzido estiver fora do intervalo, o programa pedirá outra entrada
				{ 
					System.out.println("O numero introduzido esta fora do intervalo permitido! Digite novamente.");
					N = 0; // atribui-se 0 novamente ao N para que o ciclo rode mais uma vez
				}
				else
				{
					System.out.println("Numero de discos: " + N);    // a entrada foi correta, entao imprime o que foi introduzido
				}
			}

			else
			{
				sc.nextLine();    // limpa o \n do scanner para permitir que o utilizador introduza outro valor
				System.out.println("Tipo de entrada invalida! Digite um numero inteiro.");
			}
		}

		min_mov = (int) (Math.pow(2, N) - 1); // calculo do numero minimo de jogadas
		System.out.println("O numero minimo de jogadas para vencer com " + N + " discos é " + min_mov);

		// criaçao das colunas que guardarao os discos, com o tamanho N para que possam guardar todos os discos
		A = new int[N];
		B = new int[N];
		C = new int[N];

		// criaçao das arrays de string que imprimem as torres
		String[] IA = new String[N];
		String[] IB = new String[N];
		String[] IC = new String[N];

		// inicializando a coluna A com os discos, colunas B e C vazias
		for (i = 0; i < N; i++)
		{
			A[i] = discos[N - 1 - i];   // N-1-i para atribuir os valores em ordem decrescente
			B[i] = 0;
			C[i] = 0;
			IA[i] = str[N - 1 - i];
			IB[i] = torre[N - 1 - i];
			IC[i] = torre[N - 1 - i];

		}

		while (!abort.equals("y") && !abort.equals("Y")) // ciclo para definir o fim do jogo: quando o jogador apertar y ou Y
															
		{
			escolha = 0;    // apos cada iteraçao, a variavel deve ser zerada para que entre novamente no ciclo while seguinte
			while (escolha == 0) // ciclo para obrigar que o tipo da entrada introduzida seja correto
			{
				// imprime as torres antes de cada jogada
				System.out.println("         [A]                  [B]                  [C]         ");
				System.out.println("          |                    |                    |         ");
				for (i = N - 1; i >= 0; i--)
				{
					System.out.println(IA[i] + IB[i] + IC[i]);
				}
				System.out.println("################################################################");
				System.out.println("Escolha sua jogada:");
				System.out.printf("1: A->B \t 2: A->C \t 3: B->A \t 4: B->C \t 5: C->A \t 6: C->B\n");
				
				if (sc.hasNextInt()) // só atribui valor a variavel se for um numero inteiro
				{
					escolha = sc.nextInt();
					if (escolha <= 0 || escolha > 6) // verifica se o valor esta fora do intervalo pedido
					{
						System.out.println("Jogada inexistente!");
						escolha = 0; // se o valor introduzido estiver fora do intervalo, obriga o jogador a introduzir novamente
					}
					sc.nextLine();
				}
				else // se a resposta do operador nao for um numero inteiro, informa o erro e volta a pedir a jogada
				{
					System.out.println("Entrada inválida!");
					sc.nextLine();
				}
			}

			//verificaçao das condiçoes para as jogadas
			if (escolha == 1)
			{
				i = N - 1; // iterador sobre o array do qual sairá o disco, começando de cima para baixo (N-1 é o indice maximo)
				j = 0;   // iterador sobre o array que receberá o disco, começando de baixo para cima
				while (i >= 0) // evita que o ciclo saia do indice limite do array "doador"
				{
					if (A[i] == 0)
						// se o elemento for zero, nao ha discos para mover. Nessa situaçao, se o ciclo ja tiver chegado na base
						// do array (indice 0), quer dizer que ja foi toda percorrida e nao encontrou nenhum disco, portanto,
						// avisa ao jogador que naquela coluna nao há jogada possivel.
					{
						if (i == 0)
						{
							System.out.println("Nao ha discos na coluna para serem movidos! Tente outra jogada.");
							n_jogadas--;
							// da forma como o programa foi escrito, a contabilizaçao das jogadas é feita sempre após as jogadas
							// (independente se foi valida ou nao), portanto n_jogadas-- contrabalanceia esse efeito,
							// resultando entao no que se pretende: nao contabilizar as jogadas invalidas
							break;   // sai do ciclo while para que o jogador possa escolher outra jogada
						}
						else
							// se o indice nao é zero (o ciclo nao percorreu o array todo ainda), simplesmente passa para a posiçao
							// abaixo do array doador 
						{
							i--;
							continue;
						}
					}
					// o elemento em A já nao é zero (há um disco para mover), se o elemento em B verificado for zero, há ali um 
					// espaço livre para se colocar um disco
					else if (B[j] == 0)
					{
						if (j == 0)
							// se estiver na base de B e o elemento for 0, obviamente não há discos acima e portanto já se
							// pode mover o disco de A para B sem verificar outras condiçoes
						{
							B[j] = A[i];   // o elemento em B recebe o que estava em A
							IB[j] = IA[i];
							A[i] = 0;    // o elemento em A fica vazio
							IA[i] = torre[i];
							break;    // término da jogada, volta ao ciclo while para uma nova escolha
						}
						
						// se nao estiver na base de B, é possivel que haja um disco menor em baixo, portanto verifica essa condiçao
						// se for o caso, o programa nao deixa que a jogada se realize
						else if (A[i] < B[j - 1])
						{
							B[j] = A[i];
							IB[j] = IA[i];
							A[i] = 0;
							IA[i] = torre[i];
							break;
						}
						else
						{
							System.out.println("Voce nao pode colocar um disco maior em cima de um menor! Tente novamente.");
							n_jogadas--;
							break;
						}
					}
					else
						// se o elemento em B nao for 0 (há um disco naquela posiçao), entao j é complementado para que
						// se passe para a posiçao acima
					{
						j++;
					}
				}
			}
			
			// a partir daqui, o mesmo raciocinio se segue para todas as outras jogadas
			if (escolha == 2)
			{
				i = N - 1;
				j = 0;
				while (i >= 0)
				{
					if (A[i] == 0)
					{
						if (i == 0)
						{
							System.out.println("Nao ha discos na coluna para serem movidos! Tente outra jogada.");
							n_jogadas--;
							break;
						}
						else
						{
							i--;
							continue;
						}
					}
					else if (C[j] == 0)
					{
						if (j == 0)
						{
							C[j] = A[i];
							IC[j] = IA[i];
							A[i] = 0;
							IA[i] = torre[i];
							break;
						}
						else if (A[i] < C[j - 1])
						{
							C[j] = A[i];
							IC[j] = IA[i];
							A[i] = 0;
							IA[i] = torre[i];
							break;
						}
						else
						{
							System.out.println("Voce nao pode colocar um disco maior em cima de um menor! Tente novamente.");
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

			if (escolha == 3)
			{
				i = N - 1;
				j = 0;
				while (i >= 0)
				{
					if (B[i] == 0)
					{
						if (i == 0)
						{
							System.out.println("Nao ha discos na coluna para serem movidos! Tente outra jogada.");
							n_jogadas--;
							break;
						}
						else
						{
							i--;
							continue;
						}
					}
					else if (A[j] == 0)
					{
						if (j == 0)
						{
							A[j] = B[i];
							IA[j] = IB[i];
							B[i] = 0;
							IB[i] = torre[i];
							break;
						}
						else if (B[i] < A[j - 1])
						{
							A[j] = B[i];
							IA[j] = IB[i];
							B[i] = 0;
							IB[i] = torre[i];
							break;
						}
						else
						{
							System.out.println("Voce nao pode colocar um disco maior em cima de um menor! Tente novamente.");
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

			if (escolha == 4)
			{
				i = N - 1;
				j = 0;
				while (i >= 0)
				{
					if (B[i] == 0)
					{
						if (i == 0)
						{
							System.out.println("Nao ha discos na coluna para serem movidos! Tente outra jogada.");
							n_jogadas--;
							break;
						}
						else
						{
							i--;
							continue;
						}
					}
					else if (C[j] == 0)
					{
						if (j == 0)
						{
							C[j] = B[i];
							IC[j] = IB[i];
							B[i] = 0;
							IB[i] = torre[i];
							break;
						}
						else if (B[i] < C[j - 1])
						{
							C[j] = B[i];
							IC[j] = IB[i];
							B[i] = 0;
							IB[i] = torre[i];
							break;
						}
						else
						{
							System.out.println("Voce nao pode colocar um disco maior em cima de um menor! Tente novamente.");
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

			if (escolha == 5)
			{
				i = N - 1;
				j = 0;
				while (i >= 0)
				{
					if (C[i] == 0)
					{
						if (i == 0)
						{
							System.out.println("Nao ha discos na coluna para serem movidos! Tente outra jogada.");
							n_jogadas--;
							break;
						}
						else
						{
							i--;
							continue;
						}
					}
					else if (A[j] == 0)
					{
						if (j == 0)
						{
							A[j] = C[i];
							IA[j] = IC[i];
							IC[i] = torre[i];
							C[i] = 0;
							break;
						}
						else if (C[i] < A[j - 1])
						{
							A[j] = C[i];
							IA[j] = IC[i];
							C[i] = 0;
							IC[i] = torre[i];
							break;
						}
						else
						{
							System.out.println("Voce nao pode colocar um disco maior em cima de um menor! Tente novamente.");
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

			if (escolha == 6)
			{
				i = N - 1;
				j = 0;
				while (i >= 0)
				{
					if (C[i] == 0)
					{
						if (i == 0)
						{
							System.out.println("Nao ha discos na coluna para serem movidos! Tente outra jogada.");
							n_jogadas--;
							break;
						}
						else
						{
							i--;
							continue;
						}
					}
					else if (B[j] == 0)
					{
						if (j == 0)
						{
							B[j] = C[i];
							IB[j] = IC[i];
							C[i] = 0;
							IC[i] = torre[i];
							break;
						}
						else if (C[i] < B[j - 1])
						{
							B[j] = C[i];
							IB[j] = IC[i];
							C[i] = 0;
							IC[i] = torre[i];
							break;
						}
						else
						{
							System.out.println("Voce nao pode colocar um disco maior em cima de um menor! Tente novamente.");
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

			n_jogadas++;

			for (int disco : A)  // verifica quantos discos na coluna A sao zero (o que na verdade significa que nao ha disco)
			{ 
				if (disco == 0)
				{
					contA_0++;
				}
			}

			for (int disco : B)   // verifica quantos discos na coluna B sao zero
			{
				if (disco == 0)
				{
					contB_0++;
				}
			}

			if (contA_0 == N && contB_0 == N)   // se todos os discos na coluna A e B forem zero, a pessoa ganhou o jogo
			{   // o codigo abaixo imprime o jogo no final, com todos os discos na coluna C
				System.out.println("         [A]                  [B]                  [C]         ");
				System.out.println("          |                    |                    |         ");
				for (i = N - 1; i >= 0; i--)
				{
					System.out.println(IA[i] + IB[i] + IC[i]);
				}
				System.out.println("################################################################");
				System.out.println("Voce ganhou o jogo!");
				System.out.println("Numero de jogadas efetuadas: " + n_jogadas);
				System.out.printf("Você ganhou com %d jogadas a mais que as mínimas necessárias", n_jogadas - min_mov);
				System.exit(0);
			}
			else    // a pessoa nao ganhou o jogo e pergunta se ela quer continuar
			{
				// os contadores devem ser reinicializados com zero em todo ciclo, caso contrario nunca se identificaria
				// corretamente se a pessoa ganhou o jogo (cont ultrapassariao o valor de N)
				contA_0 = 0;   
				contB_0 = 0;
				System.out.println("Número de jogadas: " + n_jogadas);
				System.out.println("Abortar o jogo? [y] ou [Y] para sim, qualquer outra tecla para continuar: ");
				abort = sc.nextLine();
			}
		}
		sc.close();
		// se a pessoa escolheu por [y] ou [Y], saiu do ciclo while e imprime a mensagem de desistencia
		System.out.print("Fim do jogo. Voce desistiu :(");
	}
}