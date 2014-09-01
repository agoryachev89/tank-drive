package tank;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Scene extends Canvas implements Blocked {
	private static final long serialVersionUID = 2L;
	public LinkedList<Object> objects;
	private final int NULL_X = 32;
	private final int NULL_Y = 16;
	Image imageBrick;
	String imageBrickName = "assets/brick.png";
	Image imageConcrete;
	String imageConcreteName = "assets/concrete.png";
	Image imageForest;
	String imageForestName = "assets/forest.png";
	Image imageWater;
	String imageWaterName = "assets/water.png";
	Image imagePlayerTank;
	String imagePlayerTankName = "assets/playertank.png";
	Image imageBullet;
	String imageBulletName = "assets/bullet.png";
	Image imageBulletBang;
	String imageBulletBangName = "assets/bullet_bang.png";

	public Scene() {
			
	}
	
	public void setObjects(LinkedList<Object> objects) {
		this.objects = objects;
		
		BufferedImage sourceImage;
		
		sourceImage = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(imageConcreteName);
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.imageConcrete = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
		
		sourceImage = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(imageForestName);
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageForest = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
		
		sourceImage = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(imageWaterName);
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageWater = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
		
		sourceImage = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(imageBrickName);
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageBrick = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
		
		sourceImage = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(imagePlayerTankName);
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imagePlayerTank = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
		
		sourceImage = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(imageBulletName);
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageBullet = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
		
		sourceImage = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(imageBulletBangName);
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageBulletBang = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
	}
	
	public void render(Graphics g) {		
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 896, 448);
		
		g.setColor(Color.black);
		g.fillRect(NULL_X, NULL_Y, 800, 416);
		
		/*
		g.setColor(Color.red);
		g.fillRect((int)(Math.random()*780) + NULL_X, (int)(Math.random()*396) + NULL_Y, 20, 20); */
		
		for (Object object : objects) {
			if (object instanceof PlayerTank) {
				drawPlayerTank(g, (PlayerTank) object);
			}
			if (object instanceof Brick) {
				drawBrick(g, (Brick) object);
			}
			
			if (object instanceof Water) {
				drawWater(g, (Water) object);
			}
			
			if (object instanceof Concrete) {
				drawConcrete(g, (Concrete) object);
			}
			
			if (object instanceof Bullet) {
				drawBullet(g, (Bullet) object);
			}
		}
		
		for (Object object : objects) {
			if (object instanceof Forest) {
				drawForest(g, (Forest) object);
			}
		}
		
		g.dispose();
	}
	
	private void drawConcrete(Graphics g, Concrete concrete) {		
		g.drawImage(imageConcrete,concrete.getX()+ NULL_X,concrete.getY()+ NULL_Y,null);
	}

	private void drawForest(Graphics g, Forest forest) {
		g.drawImage(imageForest,forest.getX()+ NULL_X,forest.getY()+ NULL_Y,null);
	}

	private void drawWater(Graphics g, Water water) {
		g.drawImage(imageWater,water.getX()+ NULL_X,water.getY()+ NULL_Y,null);
	}

	private void drawBrick(Graphics g, Brick brick) {
		g.drawImage(imageBrick,brick.getX()+ NULL_X,brick.getY()+ NULL_Y,null);
	}

	public void drawPlayerTank(Graphics g, PlayerTank playerTank) {
		AffineTransform at = new AffineTransform();
		Graphics2D g2d = (Graphics2D) g;
		int playerTank_X = playerTank.getX() + NULL_X;
		int playerTank_Y = playerTank.getY() + NULL_Y;
		
		switch (playerTank.getDirection()){
		case UP:
			at.translate(playerTank_X, playerTank_Y);
			break;
		case RIGHT:
			at.translate(playerTank_X+32, playerTank_Y);
			at.rotate(Math.toRadians(90));
			break;
		case DOWN:
			at.translate(playerTank_X+32, playerTank_Y+32);
			at.rotate(Math.toRadians(180));
			break;
		case LEFT:
			at.translate(playerTank_X, playerTank_Y+32);
			at.rotate(Math.toRadians(-90));
			break;
		}
		g2d.drawImage(imagePlayerTank, at, null);
	}
	
	public void drawBullet(Graphics g, Bullet bullet) {
		int bullet_X = bullet.getX() + NULL_X;
		int bullet_Y = bullet.getY() + NULL_Y;
		
		if (bullet.isLive) {
		AffineTransform at = new AffineTransform();
		Graphics2D g2d = (Graphics2D) g;
		
		switch (bullet.getDirection()){
		case UP:
			at.translate(bullet_X, bullet_Y);
			break;
		case RIGHT:
			at.translate(bullet_X+8, bullet_Y);
			at.rotate(Math.toRadians(90));
			break;
		case DOWN:
			at.translate(bullet_X+8, bullet_Y+8);
			at.rotate(Math.toRadians(180));
			break;
		case LEFT:
			at.translate(bullet_X, bullet_Y+8);
			at.rotate(Math.toRadians(-90));
			break;
		}
		g2d.drawImage(imageBullet, at, null);
		} else {
			switch (bullet.getDirection()){
			case UP:
				bullet_X -= 12;
				bullet_Y -= 16;
				break;
			case RIGHT:
				bullet_X -= 8;
				bullet_Y -= 12;
				break;
			case DOWN:
				bullet_X -= 12;
				bullet_Y -= 8;
				break;
			case LEFT:
				bullet_X -= 16;
				bullet_Y -= 12;
				break;
			}
			
			g.drawImage(imageBulletBang,bullet_X,bullet_Y,null);
		}
	}

	@Override
	public boolean destroy() {
		return false;
	}

	@Override
	public boolean isInTheArea(int x, int y, int size) {
		if (x < 0) {
			return true;
        }
        if (x > (800-size*8)) {
        	return true;
        }
        if (y < 0) {
        	return true;
        }
        if (y > (416-size*8)) {
        	return true;
        }
        
        return false;
	}
}
