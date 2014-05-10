package pt.tecnico.cmov.bomberman.server;

import pt.tecnico.cmov.bomberman.JogoActivity;
import pt.tecnico.cmov.bomberman.telajogo.Tabuleiro;

public class Util {
	
	public boolean colisao(int xseguinte, int yseguinte, Tabuleiro tab){
		if(tab.getTabuleiro(xseguinte, yseguinte)=='-'){
			return true;
		}
		else if (tab.getTabuleiro(xseguinte, yseguinte)=='1'){
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
}
