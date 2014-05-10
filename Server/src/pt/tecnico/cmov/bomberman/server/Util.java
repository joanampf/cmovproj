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
		
		int xseguinte;
		int yseguinte;
		
		if(aleatorio1 > 0.5){
			xseguinte = 1;
		}else {
			xseguinte = -1;
		}
		
		if(aleatorio2 > 0.5){
			yseguinte = 1;
		}else {
			yseguinte = -1;
		}
		
		if(colisao(xseguinte + x, yseguinte + y, tab)){
			int[] result = new int[]{xseguinte + x, yseguinte + y};
			return result;
		}else {
			int[] result = new int[]{x,y};
			return result;
		}
	
	}
}
