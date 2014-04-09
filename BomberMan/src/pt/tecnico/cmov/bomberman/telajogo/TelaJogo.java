package pt.tecnico.cmov.bomberman.telajogo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class TelaJogo extends View{
	
	

	public TelaJogo(Context context) {
		super(context);
		setMinimumHeight(100);
		setMinimumWidth(100);
	}
	
	@Override
	protected void onDraw(Canvas cv) {
		cv.drawColor(Color.WHITE);
        Paint p = new Paint();
        p.setColor(Color.GREEN);
        p.setStrokeWidth(5);
        cv.drawLine(20, 0, 20, cv.getHeight(), p);
	}
	

}
