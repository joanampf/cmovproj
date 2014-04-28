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
	
	


	public void moveRobot() throws InterruptedException{
		//		while (JogoActivity.tempoActivo){
		int aleatorio=(int) (Math.random() * 4);
		

		switch (aleatorio){

		case 0: moveUp();
		Thread.sleep(500);
		break;
//		case 1: moveDown(y, canvasHeight, imageHeight);
		case 1: moveDown();
		Thread.sleep(500);
		break;
//		case 2: moveRight(x, canvasWidth, imageWidth);
		case 2: moveRight();
		Thread.sleep(500);
		break;
//		case 3: moveLeft(x, imageWidth);
		case 3: moveLeft();
		Thread.sleep(500);
		break;
		default: break;

		}
		//		}

	}


	public void moveUp() {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = new int[2];
		posicao=tab.getPosicao('R');
		tab.setTabuleiro(posicao[0]-1, posicao[1], 'R');
		tab.setTabuleiro(posicao[0], posicao[1], '-');
		
	}

	public void moveDown() {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = new int[2];
		posicao=tab.getPosicao('R');
		tab.setTabuleiro(posicao[0]+1, posicao[1], 'R');
		tab.setTabuleiro(posicao[0], posicao[1], '-');
		
	}

	public void moveRight() {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = new int[2];
		posicao=tab.getPosicao('R');
		tab.setTabuleiro(posicao[0], posicao[1]+1, 'R');
		tab.setTabuleiro(posicao[0], posicao[1], '-');
		
	}
	public void moveLeft() {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int[] posicao = new int[2];
		posicao=tab.getPosicao('R');
		tab.setTabuleiro(posicao[0], posicao[1]-1, 'R');
		tab.setTabuleiro(posicao[0], posicao[1], '-');
	}
}
