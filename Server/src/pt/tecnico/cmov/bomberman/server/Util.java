package pt.tecnico.cmov.bomberman.server;

import pt.tecnico.cmov.bomberman.JogoActivity;
import pt.tecnico.cmov.bomberman.Nivel;
import pt.tecnico.cmov.bomberman.telajogo.Tabuleiro;

public class Util {
	Server server;
	public char currentlyActivePlayer;
	public Object lock = new Object();
	int[] scores = new int[4];

	public boolean colisao(int xseguinte, int yseguinte, Tabuleiro tab){
		char nextpos = tab.getTabuleiro(xseguinte, yseguinte);
		if(nextpos =='-' || nextpos =='1' || nextpos =='2' || nextpos =='3' || nextpos =='4' ){
			return true;
		}
		else
			return false;
	}

	public int[] moveRandom(int x, int y, Tabuleiro tab){

		double aleatorio1= Math.random();
		double aleatorio2= Math.random();

		int[] result = new int[]{x,y};


		int offset;

		if(aleatorio1 > 0.5){
			offset = 1;
		}else {
			offset = -1;
		}

		if(aleatorio2 > 0.5){
			if(colisao(x + offset, y, tab)){
				result = new int[]{x + offset, y};
			}
		}else {
			if(colisao(x, y + offset, tab)){
				result = new int[]{x, y + offset};
			}
		}


		return result;

	}

	public void MoveUp(char playerId, Tabuleiro tab){
		int[] posicao = new int[2];
		posicao=tab.getPosicao(playerId);
		if (this.colisao(posicao[0]-1, posicao[1], tab)){
			tab.setTabuleiro(posicao[0]-1, posicao[1], playerId);
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}

	public void MoveDown(char playerId, Tabuleiro tab){
		int[] posicao = new int[2];
		posicao=tab.getPosicao(playerId);
		if (this.colisao(posicao[0]+1, posicao[1], tab)){
			tab.setTabuleiro(posicao[0]+1, posicao[1], playerId);
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}

	public void MoveLeft(char playerId, Tabuleiro tab){
		int[] posicao = new int[2];
		posicao=tab.getPosicao(playerId);
		if (this.colisao(posicao[0], posicao[1] - 1, tab)){
			tab.setTabuleiro(posicao[0], posicao[1] - 1, playerId);
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}

	public void MoveRight(char playerId, Tabuleiro tab){
		int[] posicao = new int[2];
		posicao=tab.getPosicao(playerId);
		if (this.colisao(posicao[0], posicao[1]+1, tab)){
			tab.setTabuleiro(posicao[0], posicao[1]+1, playerId);
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}

	public void quitOnePlayer(char playerId, Tabuleiro tab){
		int[] posicao = new int[2];
		posicao=tab.getPosicao(playerId);
		tab.setTabuleiro(posicao[0], posicao[1], '-');
	}



	public void PlaceBomb(Tabuleiro tab, Nivel nivel) throws InterruptedException{
		int [] posicao;
		synchronized(lock){
			posicao = tab.getPosicao(currentlyActivePlayer);


			if(tab.getTabuleiro(posicao[0]-1, posicao[1])=='-')
				tab.setTabuleiro(posicao[0]-1, posicao[1], currentlyActivePlayer);
			else if(tab.getTabuleiro(posicao[0]+1, posicao[1])=='-')
				tab.setTabuleiro(posicao[0]+1, posicao[1], currentlyActivePlayer);
			else if(tab.getTabuleiro(posicao[0], posicao[1]+1)=='-')
				tab.setTabuleiro(posicao[0], posicao[1]+1, currentlyActivePlayer);
			else if(tab.getTabuleiro(posicao[0], posicao[1]-1)=='-')
				tab.setTabuleiro(posicao[0], posicao[1]-1, currentlyActivePlayer);

		}	
		float range= nivel.getExplosionRange();
		int i;
		tab.setTabuleiro(posicao[0], posicao[1], 'B');

		Thread.sleep((int)(1000 * nivel.getExplosionTimeout()));

		tab.setTabuleiro(posicao[0], posicao[1], 'E');

		//range e 2 e ele so faz pa 1
		for (i=1; i<range; i++){

			PutExplosion(posicao, i, 'E', tab, nivel);

		}


		Thread.sleep((int)(1000 * nivel.getExplosionDuration()));
		tab.setTabuleiro(posicao[0], posicao[1], '-');

		for (i=1; i<range; i++){
			PutExplosion(posicao, i, '-', tab, nivel);

		}	        
	}


	public boolean PositionIsValid(int x, int y, Tabuleiro tab){
		if( x >= tab.num_linhas || x < 0)
			return false;
		if( y >= tab.num_colunas || y < 0)
			return false;
		if(tab.getTabuleiro(x, y) == 'W')
			return false;

		return true;

	}

	public int PutExplosion(int[] position, int i, char c, Tabuleiro tab, Nivel nivel){
		int score = 0;
		if(PositionIsValid(position[0] + i, position[1], tab)){
			if(!(i > 1 && tab.getTabuleiro(position[0]+ i - 1, position[1]) == 'W')){
				score+= checkForPoints(position[0] + i, position[1], tab, nivel);
				tab.setTabuleiro(position[0]+i, position[1], c);
			}
		}

		if(PositionIsValid(position[0], position[1]  + i, tab)){
			if(!(i > 1 && tab.getTabuleiro(position[0], position[1]  + i - 1) == 'W')){
				score+= checkForPoints(position[0], position[1]  + i, tab, nivel);
				tab.setTabuleiro(position[0], position[1] + i, c);
			}
		}

		if(PositionIsValid(position[0] - i, position[1], tab)){
			if(!(i > 1 && tab.getTabuleiro(position[0]- i + 1, position[1]) == 'W')){
				score+= checkForPoints(position[0] - i, position[1], tab, nivel);
				tab.setTabuleiro(position[0]-i, position[1], c);
			}
		}

		if(PositionIsValid(position[0], position[1]  - i, tab)){
			if(!(i > 1 && tab.getTabuleiro(position[0], position[1]  - i + 1) == 'W')){
				score+= checkForPoints(position[0], position[1]  - i, tab, nivel);
				tab.setTabuleiro(position[0], position[1] - i, c);
			}
		}
		return score;
	}

	public int checkForPoints(int x, int y, Tabuleiro tab, Nivel nivel){
		int score = 0;
		char c = tab.getTabuleiro(x,y);
		if( c == 'R'){
			score += nivel.getPointsRobot();
		}
		if(c == '1' || c == '2' || c == '3' || c == '4'){
			//todo : do not score if player killed himself
			score += nivel.getPointsOpponents();

		}
		return score;
	}

}
