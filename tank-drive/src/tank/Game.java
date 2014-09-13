package tank;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private boolean running;
	
	public static int WIDTH = 896;
	public static int HEIGHT = 448;
	public static String NAME = "Battle City Driver";
	
	public static PlayerTank tank;
	public KeyInputHandler keyInputHandler;
	
	private Delay delay = new Delay(10);
	
	public LinkedList<Object> objects;
	
	private int level;
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void run() {
		long lastTime = System.currentTimeMillis();
		long delta;
		objects = new LinkedList<Object>();
		Scene scene = new Scene();
		objects.add(scene);
		
		init();
		
		try {
			loadMap(this.level);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		scene.setObjects(objects);
		
		while(running) {
			delta = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();	
			
			render(scene);
			update(delta);
		}
	}
	
	private void loadMap(int level) throws FileNotFoundException, IOException {
		//N - nothing
		//F - forest
		//W - water
		//B - brick
		//C - concrete
		
		BufferedReader br = new BufferedReader(new FileReader("src\\maps\\level" + level + ".map"));
        
        String line = null;
        int j = 0;
        while ((line = br.readLine()) != null) {
            char[] currentLandscapes = line.toCharArray();
            for (int i=0; i < line.length(); i++) {
            	switch (currentLandscapes[i]) {
            	case 'N':
            		break;
            	case 'W':
            		objects.add(new Water(i*16, j*16));
            		break;
            	case 'F':
            		objects.add(new Forest(i*16, j*16));
            		break;
            	case 'B':
            		objects.add(new Brick(i*16, j*16));
            		break;
            	case 'C':
            		objects.add(new Concrete(i*16, j*16));
            		break;
            	}
            }
            
            j++;
        }
        
        br.close();
	}

	public void init() {
		keyInputHandler = new KeyInputHandler();
		addKeyListener(keyInputHandler);
		tank = new PlayerTank(3*16-1,7*16-1);
		PlayerTank block = new PlayerTank(0,0);
		
		objects.add(tank);
		objects.add(block);
		
		level = 1;
	}
	
	public void render(Scene scene) {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		scene.render(g);
		
		bs.show();
	}
	
	public void update(long delta) {
		//Передвигаем PlayerTank
		if (delay.updateAndCheck(delta)){
			if ((keyInputHandler.leftPressed 		== true)&
					(keyInputHandler.rightPressed 	== false)&
					(keyInputHandler.upPressed 		== false)&
					(keyInputHandler.downPressed 	== false)) {
				if (tank.isChangeAxis(Direction.LEFT)) {
					int newY = tank.getY() % 16 > 7 ? ((tank.getY()/16) + 1) * 16 : (tank.getY()/16)*16;
					tank.setY(newY);
				}
				tank.setDirection(Direction.LEFT);
				tank.setX(tank.getX()-1);
			}
			if ((keyInputHandler.leftPressed 		== false)&
					(keyInputHandler.rightPressed 	== true)&
					(keyInputHandler.upPressed 		== false)&
					(keyInputHandler.downPressed 	== false)) {
				if (tank.isChangeAxis(Direction.RIGHT)) {
					int newY = tank.getY() % 16 > 7 ? ((tank.getY()/16) + 1) * 16 : (tank.getY()/16)*16;
					tank.setY(newY);
				}
				tank.setDirection(Direction.RIGHT);
				tank.setX(tank.getX()+1);
			}
			if ((keyInputHandler.leftPressed 		== false)&
					(keyInputHandler.rightPressed 	== false)&
					(keyInputHandler.upPressed 		== true)&
					(keyInputHandler.downPressed 	== false)) {
				if (tank.isChangeAxis(Direction.UP)) {
					int newX = tank.getX() % 16 > 7 ? ((tank.getX()/16) + 1) * 16 : (tank.getX()/16)*16;
					tank.setX(newX);
				}
				tank.setDirection(Direction.UP);
				tank.setY(tank.getY()-1);
			}
			if ((keyInputHandler.leftPressed 		== false)&
					(keyInputHandler.rightPressed 	== false)&
					(keyInputHandler.upPressed 		== false)&
					(keyInputHandler.downPressed 	== true)) {
				if (tank.isChangeAxis(Direction.DOWN)) {
					int newX = tank.getX() % 16 > 7 ? ((tank.getX()/16) + 1) * 16 : (tank.getX()/16)*16;
					tank.setX(newX);
				}
				tank.setDirection(Direction.DOWN);
				tank.setY(tank.getY()+1);
			}	
		}
		
		//Проверяем можно ли передвинуть PlayerTank
		for (Object object : objects) {
			if (object instanceof Blocked) {
				if (((Blocked) object).isInTheArea(tank.getX(), tank.getY(), 4)&(object != tank)) {
					switch (tank.getDirection()) {
					case UP:
						tank.setY(tank.getY()+1);
						break;
					case DOWN:
						tank.setY(tank.getY()-1);
						break;
					case LEFT:
						tank.setX(tank.getX()+1);
						break;
					case RIGHT:
						tank.setX(tank.getX()-1);
						break;
					}
				}
			}
		}
		
		// Можно ли выстрелить
		if ((keyInputHandler.spacePressed == true) & (tank.canShoot == true)) {
			switch (tank.getDirection()) {
			case UP:
				objects.add(new Bullet(tank.getX()+12, tank.getY() - 12, tank.getDirection(), tank));
				break;
			case DOWN:
				objects.add(new Bullet(tank.getX()+12, tank.getY() + 31, tank.getDirection(), tank));
				break;
			case LEFT:
				objects.add(new Bullet(tank.getX()-12, tank.getY() + 12, tank.getDirection(), tank));
				break;
			case RIGHT:
				objects.add(new Bullet(tank.getX()+31, tank.getY() + 12, tank.getDirection(), tank));
				break;
			}
		}
		
		//Двигаем все пули и при необходимости подрываем объекты
		LinkedList<Object> newObjects = new LinkedList<Object>();
		for (Object subject : objects) {
			if (subject instanceof Bullet) {
				if (((Bullet) subject).isLive) {
					((Bullet) subject).move(delta);
				
					for (Object object : objects) {
						if ((object instanceof Blocked)&(!(object instanceof Water))){
							if (((Blocked) object).isInTheArea(((Bullet)subject).getX(), ((Bullet)subject).getY(), 1)&
									(object != subject)&
									(object != ((Bullet) subject).getParentTank())){
								if (((Blocked) object).destroy()) {
									newObjects.add(object);
								}
								//((Bullet)subject).destroy();
								//newObjects.add(subject);
								((Bullet)subject).bang(delta);
							}
						}
					}
				} else {
					((Bullet)subject).bang(delta);
					if (((Bullet) subject).die) {
						((Bullet)subject).destroy();
						newObjects.add(subject);
					}
				}
			}
		}
		
		for (Object object : newObjects) {
			objects.remove(object);
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		new Window(game);
		game.start();
	}
}
