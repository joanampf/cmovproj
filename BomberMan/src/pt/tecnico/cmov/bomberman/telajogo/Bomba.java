package pt.tecnico.cmov.bomberman.telajogo;

import pt.tecnico.cmov.bomberman.JogoActivity;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bomba {

	private Bitmap bitmap; // the actual bitmap
	private int x;   // the X coordinate
	private int y;   // the Y coordinate
	private boolean touched; // if droid is touched/picked up

	public Bomba(Bitmap bitmap, int x, int y) {
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



	//	quando bomba explode tem de ser retirada e tratada a explosao
	//colocar pontos
	public void explode(int [] posicao) throws InterruptedException{
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int range= JogoActivity.nivel.getExplosionRange();
		int explosion_timeout= JogoActivity.nivel.getExplosionTimeout();
		int i;
		
		System.out.println("range: "+range);
		System.out.println("x y: "+ posicao[0] + " "+ posicao[1]);

		Thread.sleep(100/explosion_timeout);

		tab.setTabuleiro(posicao[0], posicao[1], 'E');

		//range e 2 e ele so faz pa 1
		for (i=0; i<range; i++){

			if(tab.getTabuleiro(posicao[0]+i, posicao[1]) != 'W')
				tab.setTabuleiro(posicao[0]+i, posicao[1], 'E');

			if(tab.getTabuleiro(posicao[0], posicao[1]+i) != 'W')
				tab.setTabuleiro(posicao[0], posicao[1]+i, 'E');

			if(tab.getTabuleiro(posicao[0]-i, posicao[1]) != 'W')
				tab.setTabuleiro(posicao[0]-i, posicao[1], 'E');

			if(tab.getTabuleiro(posicao[0], posicao[1]-i) != 'W')
				tab.setTabuleiro(posicao[0], posicao[1]-i, 'E');

		}



	}

	public void acabaExplosao(int[] posicao) throws InterruptedException {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int explosion_duration = JogoActivity.nivel.getExplosionDuration();

		Thread.sleep(100/explosion_duration);
		tab.setTabuleiro(posicao[0], posicao[1], '-');

	}
}
