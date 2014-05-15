package pt.tecnico.cmov.bomberman;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Nivel implements Serializable {

	private String levelName;
	private float gameDuration;
	private float explosionTimeout;
	private float explosionDuration;
	private float explosionRange;
	private float robotSpeed;
	private float pointsRobot;
	private float pointsOpponent;
	private String filepath;
	
	// novo
	// falta ver grid layout
	public Nivel (String LN, float GD, float ET, float ED, float ER, float RS, float PR, float PO, String filepath){
		this.levelName=LN;
		this.gameDuration=GD;
		this.explosionTimeout=ET;
		this.explosionDuration=ED;
		this.explosionRange=ER;
		this.robotSpeed=RS;
		this.pointsRobot=PR;
		this.pointsOpponent=PO;
		this.filepath=filepath;
		
	}
	public Nivel (){
		
	}

	public Nivel getNivel(){
		return this;
	}
	
	//LevelName
	public void setLevelName(String name){
		this.levelName=name;
	}
	public String getLevelName(){
		return levelName;
	}
	//GameDuration
	public void setGameDuration(float duracaoJ){
		this.gameDuration = duracaoJ;
	}
	public float getGameDuration(){
		return gameDuration;
	}
	//ExplocionTimeout
	public void setExplosionTimeout(float timeoutE){
		this.explosionTimeout=timeoutE;
	}
	public float getExplosionTimeout(){
		return explosionTimeout;
	}
	//ExplosionDuration
	public void setExplosionDuration(float durationE){
		this.explosionDuration=durationE;
	}
	public float getExplosionDuration(){
		return explosionDuration;
	}
	//ExplosionRange
	public void setExplosionRange(float rangeE){
		this.explosionRange=rangeE;
	}
	public float getExplosionRange(){
		return explosionRange;
	}
	//RobotSpeed
	public void setRobotSpeed(float robotSpeed){
		this.robotSpeed=robotSpeed;
	}
	public float getRobotSpeed(){
		return robotSpeed;
	}
	//PointsRobot
	public void setPointsRobot(float pointsR){
		this.pointsRobot=pointsR;
	}
	public float getPointsRobot(){
		return pointsRobot;
	}
	//PointsOpponent
	public void setPointsOpponent(float pointsO){
		this.pointsOpponent=pointsO;
	}
	public float getPointsOpponents(){
		return pointsOpponent;
	}
	//filepath
	public String getFilePath(){
		return filepath;
	}
	
	
	
	
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_jogo);
//		Intent intent = getIntent();
//		
//		
//		String tempo = intent.getStringExtra(MainActivity.NOME);
//		TextView txTempo = (TextView) findViewById(R.id.timeLeft);
//		txTempo.setText(tempo);
//	}
	
	
}
