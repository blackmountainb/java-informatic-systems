package trabalho2;

/**
 * Esta classe representa as peças de dominó
 * @author Beatriz Negromonte e Rafael Molter
 *
 */
public class Domino
{
	private int x,y; // atributos das peças, valor em cada face
	/**
	 * Construtor da peça
	 * @param a é o valor da face superior
	 * @param b é o valor da face inferior
	 */
	public Domino(int a, int b)
	{
		x = a;
		y = b;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * Este método soma os valores das faces de cada peça
	 * @return um valor inteiro de x + y
	 */
	public int soma()
	{
		return this.x + this.y;
	}
	
	
	/**
	  Este método imprime as peças, sendo que se as faces tiverem igual valor, dispõe de forma horizontal
	 */
	public void imprime() {
		if(this.x==this.y) {
			System.out.println("|" + this.x + "|"+ this.y + "|");
			System.out.println();
		}
		
		else {
			System.out.println(" |" + this.x + "|");
			System.out.println(" ---");
			System.out.println(" |" + this.y + "|");
			System.out.println();
		}
	}
}
