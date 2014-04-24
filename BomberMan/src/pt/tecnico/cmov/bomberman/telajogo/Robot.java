package pt.tecnico.cmov.bomberman.telajogo;

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
	
	


	public void moveRobot(int x, int y, int imageHeight, int imageWidth, int canvasHeight, int canvasWidth) throws InterruptedException{
		//		while (JogoActivity.tempoActivo){
		int aleatorio=(int) (Math.random() * 4);
		

		switch (aleatorio){

		case 0: moveUp(y, imageHeight);
		Thread.sleep(500);
		break;
		case 1: moveDown(y, canvasHeight, imageHeight);
		Thread.sleep(500);
		break;
		case 2: moveRight(x, canvasWidth, imageWidth);
		Thread.sleep(500);
		break;
		case 3: moveLeft(x, imageWidth);
		Thread.sleep(500);
		break;
		default: break;

		}
		//		}

	}


	public void moveUp(int y, int imagelength) {
		if(y>=imagelength){
			this.setY(y-10);
		}
	}

	public void moveDown(int y, int canvaslength,int imagelength) {
		if(y<=(canvaslength-imagelength)){
			this.setY(y+10);
		}
	}

	public void moveRight(int x, int canvaslength,int imagelength) {
		if(x<=(canvaslength-imagelength)){
			this.setX(x+10);
		}
	}
	public void moveLeft(int x, int imagelength) {
		if(x>=imagelength){
			this.setX(x-10);
		}
	}
}
