package pt.tecnico.cmov.bomberman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Nivel extends Activity{

	private String levelName;
	private Integer gameDuration;
	private Integer explosionTimeout;
	private Integer explosionDuration;
	private Integer explosionRange;
	private Integer robotSpeed;
	private Integer pointsRobot;
	private Integer pointsOpponent;
	private String filepath;
	
	// novo
	// falta ver grid layout
	public Nivel (String LN, Integer GD, Integer ET, Integer ED, Integer ER, Integer RS, Integer PR, Integer PO, String filepath){
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
	public void setGameDuration(Integer duracaoJ){
		this.gameDuration = duracaoJ;
	}
	public Integer getGameDuration(){
		return gameDuration;
	}
	//ExplocionTimeout
	public void setExplosionTimeout(Integer timeoutE){
		this.explosionTimeout=timeoutE;
	}
	public Integer getExplosionTimeout(){
		return explosionTimeout;
	}
	//ExplosionDuration
	public void setExplosionDuration(Integer durationE){
		this.explosionDuration=durationE;
	}
	public Integer getExplosionDuration(){
		return explosionDuration;
	}
	//ExplosionRange
	public void setExplosionRange(Integer rangeE){
		this.explosionRange=rangeE;
	}
	public Integer getExplosionRange(){
		return explosionRange;
	}
	//RobotSpeed
	public void setRobotSpeed(Integer robotSpeed){
		this.robotSpeed=robotSpeed;
	}
	public Integer getRobotSpeed(){
		return robotSpeed;
	}
	//PointsRobot
	public void setPointsRobot(Integer pointsR){
		this.pointsRobot=pointsR;
	}
	public Integer getPointsRobot(){
		return pointsRobot;
	}
	//PointsOpponent
	public void setPointsOpponent(Integer pointsO){
		this.pointsOpponent=pointsO;
	}
	public Integer getPointsOpponents(){
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
