package pt.tecnico.cmov.bomberman.telajogo;

import javax.crypto.spec.PSource;

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


	public boolean PositionIsValid(int x, int y){
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		if( x >= tab.num_linhas || x < 0)
			return false;
		if( y >= tab.num_colunas || y < 0)
			return false;
		if(tab.getTabuleiro(x, y) == 'W')
			return false;
		
		return true;

	}
	
	public void PutExplosion(int[] position, int i, char c){
		Tabuleiro tab = JogoActivity.tabuleiroInit;

		if(PositionIsValid(position[0] + i, position[1])){
			if(!(i > 1 && tab.getTabuleiro(position[0]+ i - 1, position[1]) == 'W')){
				checkForPoints(position[0] + i, position[1]);
				tab.setTabuleiro(position[0]+i, position[1], c);
			}
		}
		
		if(PositionIsValid(position[0], position[1]  + i)){
			if(!(i > 1 && tab.getTabuleiro(position[0], position[1]  + i - 1) == 'W')){
				checkForPoints(position[0], position[1]  + i);
				tab.setTabuleiro(position[0], position[1] + i, c);
			}
		}
		
		if(PositionIsValid(position[0] - i, position[1])){
			if(!(i > 1 && tab.getTabuleiro(position[0]- i + 1, position[1]) == 'W')){
				checkForPoints(position[0] - i, position[1]);
				tab.setTabuleiro(position[0]-i, position[1], c);
			}
		}
		
		if(PositionIsValid(position[0], position[1]  - i)){
			if(!(i > 1 && tab.getTabuleiro(position[0], position[1]  - i + 1) == 'W')){
				checkForPoints(position[0], position[1]  - i);
				tab.setTabuleiro(position[0], position[1] - i, c);
			}
		}
		
	}
	
	public void checkForPoints(int x, int y){
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		char c = tab.getTabuleiro(x,y);
			if( c == 'R'){
				JogoActivity.score += JogoActivity.nivel.getPointsRobot();
			}
			if(c == '1' || c == '2' || c == '3' || c == '4'){
				//todo : do not score if player killed himself
				JogoActivity.score += JogoActivity.nivel.getPointsOpponents();

			}
	}

	

	//	quando bomba explode tem de ser retirada e tratada a explosao
	//colocar pontos
	public void explode(int [] posicao) throws InterruptedException{
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		float range= JogoActivity.nivel.getExplosionRange();
		float explosion_timeout= JogoActivity.nivel.getExplosionTimeout();
		int i;
		
		System.out.println("range: "+range);
		System.out.println("x y: "+ posicao[0] + " "+ posicao[1]);
		
		Thread.sleep((int)(1000 * explosion_timeout));
		
		tab.setTabuleiro(posicao[0], posicao[1], 'E');

		//range e 2 e ele so faz pa 1
		for (i=1; i<range; i++){

			PutExplosion(posicao, i, 'E');
			
		/*	if(tab.getTabuleiro(posicao[0]+i, posicao[1]) != 'W')
				tab.setTabuleiro(posicao[0]+i, posicao[1], 'E');

			if(tab.getTabuleiro(posicao[0], posicao[1]+i) != 'W')
				tab.setTabuleiro(posicao[0], posicao[1]+i, 'E');

			if(tab.getTabuleiro(posicao[0]-i, posicao[1]) != 'W')
				tab.setTabuleiro(posicao[0]-i, posicao[1], 'E');

			if(tab.getTabuleiro(posicao[0], posicao[1]-i) != 'W')
				tab.setTabuleiro(posicao[0], posicao[1]-i, 'E');
*/
		}
		
		float explosion_duration = JogoActivity.nivel.getExplosionDuration();

		Thread.sleep((int)(1000 * explosion_duration));
		tab.setTabuleiro(posicao[0], posicao[1], '-');

		for (i=1; i<range; i++){
			PutExplosion(posicao, i, '-');
		
			/*if(tab.getTabuleiro(posicao[0]+i, posicao[1]) == 'E')
				tab.setTabuleiro(posicao[0]+i, posicao[1], '-');

			if(tab.getTabuleiro(posicao[0], posicao[1]+i) == 'E')
				tab.setTabuleiro(posicao[0], posicao[1]+i, '-');

			if(tab.getTabuleiro(posicao[0]-i, posicao[1]) == 'E')
				tab.setTabuleiro(posicao[0]-i, posicao[1], '-');

			if(tab.getTabuleiro(posicao[0], posicao[1]-i) == 'E')
				tab.setTabuleiro(posicao[0], posicao[1]-i, '-');
*/
			
		}

        

	}

/*	public void acabaExplosao(int[] posicao) throws InterruptedException {
		Tabuleiro tab = JogoActivity.tabuleiroInit;
		int explosion_duration = JogoActivity.nivel.getExplosionDuration();

		Thread.sleep(100 * explosion_duration);
        synchronized (lock) {
            System.out.println("exploding");

		tab.setTabuleiro(posicao[0], posicao[1], '-');
        }

	}*/
}
