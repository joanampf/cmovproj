package pt.tecnico.cmov.bomberman.telajogo;

import pt.tecnico.cmov.bomberman.JogoActivity;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Robot {


	private Bitmap bitmap; // the actual bitmap
	private int x;   // the X coordinate
	private int y;   // the Y coordinate
	private boolean touched; // if robot is touched/picked up


	public Robot(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;

	}

	public int[] getPosicao(Bitmap bitmap){
		int[] result = new int[2];
		result[1] = this.getX()/bitmap.getWidth();
		result[0] = this.getY()/bitmap.getHeight();
		return result;
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
			return true;
		}
		else
			return false;

	}

	public void moveUp(Bitmap bit) {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = this.getPosicao(bit);
		//verifica se na proxima posi�ao existe ja um item
		if (this.colisao(posicao[0]-1, posicao[1])){
			tab.setTabuleiro(posicao[0]-1, posicao[1], 'R');
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}

	public void moveDown(Bitmap bit) {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = this.getPosicao(bit);
		if (this.colisao(posicao[0]+1, posicao[1])){
			tab.setTabuleiro(posicao[0]+1, posicao[1], 'R');
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}


	}

	public void moveRight(Bitmap bit) {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = this.getPosicao(bit);
		if (this.colisao(posicao[0], posicao[1]+1)){
			tab.setTabuleiro(posicao[0], posicao[1]+1, 'R');
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}
	public void moveLeft(Bitmap bit) {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = this.getPosicao(bit);
		if (this.colisao(posicao[0], posicao[1]-1)){
			tab.setTabuleiro(posicao[0], posicao[1]-1, 'R');
			tab.setTabuleiro(posicao[0], posicao[1], '-');
		}
	}
	
	
}
