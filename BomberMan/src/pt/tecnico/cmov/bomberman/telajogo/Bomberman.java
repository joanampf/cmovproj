package pt.tecnico.cmov.bomberman.telajogo;

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
	public void moveUp(int y, int imagelength) {
		if(y>=imagelength){
			System.out.println("VALOR Da Imagem: " + imagelength);
			this.setY(y-10);
		}
	}

	public void moveDown(int y, int canvaslength,int imagelength) {
		if(y<=(canvaslength-imagelength)){
			System.out.println("VALOR DO canvas: " + canvaslength);
			this.setY(y+10);
		}
	}

	public void moveRight(int x, int canvaslength,int imagelength) {
		if(x<=(canvaslength-imagelength)){
			System.out.println("VALOR DO X: " + x);
			this.setX(x+10);
		}
	}
	public void moveLeft(int x, int imagelength) {
		if(x>=imagelength){
			System.out.println("VALOR DO X: " + x);
			this.setX(x-10);
		}
	}


}
