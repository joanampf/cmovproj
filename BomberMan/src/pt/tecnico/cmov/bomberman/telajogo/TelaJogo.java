package pt.tecnico.cmov.bomberman.telajogo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class TelaJogo extends View{
	
	Paint paint;

	public TelaJogo(Context context) {
		super(context);
		setMinimumHeight(100);
		setMinimumWidth(100);
//		paint = new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.GRAY);
		super.onDraw(canvas);
	}
	

}
