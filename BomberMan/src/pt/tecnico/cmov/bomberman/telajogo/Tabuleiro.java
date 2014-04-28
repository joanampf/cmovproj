package pt.tecnico.cmov.bomberman.telajogo;

public class Tabuleiro {

	public int num_linhas;
	public int num_colunas;
	public char[][] tabuleiro;
	
	
	public Tabuleiro(int num_linhas, int num_colunas) {
		super();
		this.num_linhas = num_linhas;
		this.num_colunas = num_colunas;
		tabuleiro=new char[num_linhas][num_colunas];
	}


	public int getNum_linhas() {
		return num_linhas;
	}
	
	


	public char getTabuleiro(int linha, int coluna) {
		return tabuleiro[linha][coluna];
	}


	public void setTabuleiro(int linha, int coluna, char item) {
		tabuleiro[linha][coluna]= item;
	}


	public void setNum_linhas(int num_linhas) {
		this.num_linhas = num_linhas;
	}


	public int getNum_colunas() {
		return num_colunas;
	}


	public void setNum_colunas(int num_colunas) {
		this.num_colunas = num_colunas;
	}
	
	public int[] getPosicao(char item){
		int[] result = new int[2];
		for (int linha = 0; linha < num_linhas; linha++) {
			for (int coluna = 0; coluna < num_colunas; coluna++) {
				if(item==getTabuleiro(linha, coluna)){
					result[0]=linha;
					result[1]=coluna;
					return result;
				}
					
					
			}
		}
		
		return null;
		
		
	}
}
