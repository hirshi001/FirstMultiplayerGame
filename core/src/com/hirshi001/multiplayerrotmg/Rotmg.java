package com.hirshi001.multiplayerrotmg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.multiplayerrotmg.client.Client;
import com.hirshi001.multiplayerrotmg.gameadapter.GameApplication;
import com.hirshi001.multiplayerrotmg.gameadapter.GameApplicationAdapter;
import com.hirshi001.multiplayerrotmg.registry.DisposableRegistry;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;
import io.netty.util.internal.StringUtil;

import java.awt.Dimension;
import java.util.function.IntPredicate;

public class Rotmg extends ApplicationAdapter {
	SpriteBatch batch;
	GameApplicationAdapter gameApplication;

	public static final Dimension size = new Dimension(1200,1000);

	public OrthographicCamera camera;



	@Override
	public void create () {


		/* Create OrthographicCamera */
		camera = new OrthographicCamera(size.width, size.height);

		/* create spritebatch */
		batch = new SpriteBatch();

		/* create game application*/
		gameApplication = new GameApplication();
		try {
			gameApplication.init();
		}catch(NoSuchFieldException | IllegalAccessException e){
			System.err.println("There was a problem registering something in the init method");
			e.printStackTrace();
			System.exit(-1);
		}
		gameApplication.setCamera(camera);
		gameApplication.startup();
		
		Client.setGame(gameApplication.getGame());
		Client.run();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}

	@Override
	public void render () {
		if(!Client.isReady()) return;

		//update
		gameApplication.update();
		camera.update();

		//draw
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		gameApplication.draw(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		gameApplication.dispose();
		DisposableRegistry.dispose();
	}
}
