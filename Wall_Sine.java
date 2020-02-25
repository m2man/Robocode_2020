package MyBot_v0;
import java.awt.Color;

import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Wall_Spin - a robot by (your name here)
 */
public class Wall_Spin extends Robot
{
	boolean peek; // Don't turn if there's a robot there
	double moveAmount; // How much to move

	public void run() {

		setBodyColor(Color.pink);
		setGunColor(Color.pink);
		setRadarColor(Color.pink);
		setBulletColor(Color.pink);
		setScanColor(Color.pink);

		moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		double myX = getX();
		double myY = getY();


		MovetoEdge(2.0);
		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			peek = true;
			MovealongEdge(2.0, 0.3);
			WavingMove(moveAmount/5, 2*5);
			CalibrateGun();
			MovetoEdge(2.0);
			CalibrateGun();
			MovealongEdge(2.0, 1);
			CalibrateGun();
			peek = false;
			turnRight(90);
		}
	}

	public void WavingMove(double distance, double numb_wave){
		for (int i = 0; i < numb_wave; i++){
			if (i == 0){
				turnRight(45);
			}else{
				if (i % 2 == 0){
					turnRight(90);
				}else{
					turnLeft(90);
				}
			}
			ahead(distance/(numb_wave/2));
		}
	}

	public void MovealongEdge(double pad, double ratio){
		double myX = getX();
		double myY = getY();
		double heading = getHeading();
		double distance = 0;
		double gunDirection = 0;
		if (heading >= 0 && heading < 90){ // on left edge
			distance = getBattleFieldHeight() - myY - getWidth() - pad;
			gunDirection = 90; 
		}
		else if (heading >= 90 && heading < 180){ // on top edge
			distance = getBattleFieldWidth() - myX - getWidth() - pad;
			gunDirection = 180;
		}
		else if (heading >= 180 && heading < 270){ // on right edge
			distance = myY - getWidth() - pad;
			gunDirection = 270;
		}
		else { // bottom edge
			distance = myX - getWidth() - pad;
			gunDirection = 0;
		}
		ahead(distance*ratio);	
	}

	public void CalibrateGun(){
		double heading = getHeading();
		double gunheading = getGunHeading();
		double expectgunheading = heading + 90;
		if (expectgunheading >= 360){
			expectgunheading = 0;
		}
		if (gunheading != expectgunheading){
			if (expectgunheading > gunheading){
				if (Math.abs(expectgunheading - gunheading) > 180){
					turnGunLeft(360-Math.abs(expectgunheading-gunheading));
				}else{
					turnGunRight(Math.abs(expectgunheading-gunheading));
				}
			}else{
				if (Math.abs(expectgunheading - gunheading) > 180){
					turnGunRight(360-Math.abs(expectgunheading-gunheading));
				}else{
					turnGunLeft(Math.abs(expectgunheading-gunheading));
				}
			}
		}
		
	}
	
	public void MovetoEdge(double pad){
		double myX = getX();
		double myY = getY();
		double remainX = Math.min(myX, getBattleFieldWidth() - myX);
		double remainY = Math.min(myY, getBattleFieldHeight() - myY);
		double distance = 0;
		double heading = getHeading();

		if (myX >= getBattleFieldWidth()/2 && myY >= getBattleFieldHeight()/2){
			if (remainX < remainY){
				if (heading > 90.0){
					turnLeft(heading - 90);
				}else{
					turnRight(90 - heading);
				}
				distance = remainX - getWidth() - pad;
			}else{
				turnLeft(heading);
				distance = remainY - getWidth() - pad;
			}
		}else{
			if (myX >= getBattleFieldWidth()/2 && myY < getBattleFieldHeight()/2){
				if (remainX < remainY){
					if (heading > 90.0){
						turnLeft(heading - 90);
					}else{
						turnRight(90 - heading);
					}
					distance = remainX - getWidth() - pad;
				}else{
					if (heading > 180.0){
						turnLeft(heading - 180);
					}else{
						turnRight(180 - heading);
					}
					distance = remainY - getWidth() - pad;
				}
			}else{
				if (myX < getBattleFieldWidth()/2 && myY >= getBattleFieldHeight()/2){
					if (remainX < remainY){
						if (heading > 270){
							turnLeft(heading - 270);
						}else{
							turnRight(270 - heading);
						}
						distance = remainX - getWidth() - pad;
					}else{
						turnLeft(heading);
						distance = remainY - getWidth() - pad;
					}
				}else{
					if (remainX < remainY){
						if (heading > 270){
							turnLeft(heading - 270);
						}else{
							turnRight(270 - heading);
						}
						distance = remainX - getWidth() - pad;
					}else{
						turnLeft(heading);
						distance = remainY - getWidth() - pad;
					}
				}
			}
		}
		peek = false;
		ahead(distance);
		peek = true;
		CalibrateGun();
		turnRight(90);
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		fire(2);
		if (peek) {
			scan();
		}
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
		MovetoEdge(2.0);
		CalibrateGun();
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	// public void onHitWall(HitWallEvent e) {
	// 	// Replace the next line with any behavior you would like
	// 	back(20);
	// }
}
