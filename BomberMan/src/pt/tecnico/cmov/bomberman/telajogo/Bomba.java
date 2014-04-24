package pt.tecnico.cmov.bomberman.telajogo;

import android.graphics.Bitmap;

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
	
	
//	quando bomba explode tem de ser retirada e tratada a explosao
	public void explode(){
		
	}
}
