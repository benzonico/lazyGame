package com.mcamier.minimalgame;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.mcamier.lazyEngine.actor.ComponentTypeEnum;
import com.mcamier.lazyEngine.actor.impl.Actor;
import com.mcamier.lazyEngine.actor.impl.AnimationComponent;
import com.mcamier.lazyEngine.actor.impl.SpriteComponent;
import com.mcamier.lazyEngine.actor.impl.TransformComponent;
import com.mcamier.lazyEngine.game.AbstractGame;
import com.mcamier.lazyEngine.resource.impl.ResourceManager;
import com.mcamier.lazyEngine.resource.impl.ZipResourceLoaderImpl;

public class Game extends AbstractGame {

	private Actor player;
	private List<Actor> gameObjects;
	
	public Game() {
		super();
	}

	@Override
	public void initialize() {
		URL zipPath = ClassLoader.getSystemResource(GameConstant.ARCHIVE_FILENAME);
 		ResourceManager.setResourceLoader(new ZipResourceLoaderImpl(zipPath));
 		gameObjects = new ArrayList<Actor>();
		
 		initGL(800, 600);
		
		
		player = new Actor();
		
		AnimationComponent animation = new AnimationComponent(player);
		animation.setFrameCount(7);
		animation.setFrameHeight(245);
		animation.setFrameWidth(300);
		animation.setLoop(false);
		animation.setSprite(ResourceManager.getInstance().getTexture(GameConstant.RES_POOPY));
		player.putComponent(animation);
		
		this.gameObjects.add(player);
	}

	@Override
	public void update(int delta) {
		Iterator<Actor> gameObjectsIterator = this.gameObjects.iterator();
		while(gameObjectsIterator.hasNext()) {
			gameObjectsIterator.next().update(delta);
		}
		
		if( Keyboard.isKeyDown(Keyboard.KEY_RIGHT) ) {
			System.out.println("Key X pressed");
			
			TransformComponent c = (TransformComponent) player.getComponent(ComponentTypeEnum.TRANSFORM);
			c.getPosition().x += 1.5f;
		}
	}

	@Override
	public void draw(int delta) {
		glClear(GL_COLOR_BUFFER_BIT);
	
		Color.white.bind();
		
		TransformComponent c = player.getComponent(ComponentTypeEnum.TRANSFORM);
		SpriteComponent t = (SpriteComponent) player.getComponent(ComponentTypeEnum.RENDER);
		t.getSprite().bind(); // or GL11.glBind(texture.getTextureID());
		
		glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f(c.getPosition().x, c.getPosition().y);
			glTexCoord2f(1,0);
			glVertex2f(c.getPosition().x+t.getSprite().getTextureWidth()/3,c.getPosition().y);
			glTexCoord2f(1,1);
			glVertex2f(c.getPosition().x+t.getSprite().getTextureWidth()/3,c.getPosition().y+t.getSprite().getTextureHeight()/3);
			glTexCoord2f(0,1);
			glVertex2f(c.getPosition().x, c.getPosition().y +t.getSprite().getTextureHeight()/3);
		glEnd();
	}
	
	
	private void initGL(int width, int height) {
		glEnable(GL_TEXTURE_2D);               
        
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
        	// enable alpha blending
        	glEnable(GL_BLEND);
        	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        	glViewport(0,0,width,height);
		glMatrixMode(GL_MODELVIEW);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	

	@Override
	public void destroy() {
		this.gameObjects.clear();
		this.gameObjects = null;
	}
	
	
	/** Program entry point
	 * @param args
	 */
	public static void main(String[] args) {
		Game myGame = new Game();
		myGame.run();
	}
}