package Zombies;

import Accessories.Enemy;
import Accessories.Animation;
import Tiles.TileAccessories;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;


public class Dog extends Enemy {
	
	private BufferedImage[] sprites;

	public Dog(TileAccessories tm) {
		
		super(tm);
		rank = 1;
        enemyType = 1;
		moveSpeed = 1;
		maxSpeed = 1;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		bounce = true;
		
	
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Enemies/dog.png"
				)
			);
			
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);
		
		right = true;
		facingRight = false;
		
	}
	
	private void getNextPosition() {
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		if(falling) {
			dy += fallSpeed;
			dx = 0;
		}	
	}
	
	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1_000_000;
			if(elapsed > 400) {
				flinching = false;
			}
		}
			
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = true;
			dx = -dx;
		}

		
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = false;
			dx = -dx;
		}
		
		
		if(!bottomLeft) {
			left = false;
			right = true;
			facingRight = false;
			// dx = -dx;
		}
		if(!bottomRight) {
			left = true;
			right = false;
			facingRight = true;
			// dx = -dx;
		}
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		super.draw(g);
		System.out.println(dx);
	}
	
}





