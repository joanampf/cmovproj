package pt.tecnico.cmov.bomberman.telajogo;

import pt.tecnico.cmov.bomberman.JogoActivity;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bomberman {
	private Bitmap bitmap; // the actual bitmap
	private int x;   // the X coordinate
	private int y;   // the Y coordinate
	private boolean touched; // if droid is touched/picked up

	public Bomberman(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;

	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isTouched() {
		return touched;
	}
	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public void draw(Canvas canvas) {

		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
	
	public boolean colisao(int xseguinte, int yseguinte){
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		if(tab.getTabuleiro(xseguinte, yseguinte)=='-'){
			return true;
		}
		else if (tab.getTabuleiro(xseguinte, yseguinte)=='1'){
			//lançar pop de gameover
			return true;
		}
		else
			return false;

	}

	public void moveUp() {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = new int[2];
		posicao=tab.getPosicao('1');
		//verifica se na proxima posiçao existe ja um item
		if (this.colisao(posicao[0]-1, posicao[1])){
			tab.setTabuleiro(posicao[0]-1, posicao[1], '1');
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}

	public void moveDown() {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = new int[2];
		posicao=tab.getPosicao('1');
		if (this.colisao(posicao[0]+1, posicao[1])){
			tab.setTabuleiro(posicao[0]+1, posicao[1], '1');
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}


	}

	public void moveRight() {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = new int[2];
		posicao=tab.getPosicao('1');
		if (this.colisao(posicao[0], posicao[1]+1)){
			tab.setTabuleiro(posicao[0], posicao[1]+1, '1');
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}
	public void moveLeft() {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = new int[2];
		posicao=tab.getPosicao('1');
		if (this.colisao(posicao[0], posicao[1]-1)){
			tab.setTabuleiro(posicao[0], posicao[1]-1, '1');
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}


}
