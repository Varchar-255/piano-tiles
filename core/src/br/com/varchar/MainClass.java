package br.com.varchar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import static br.com.varchar.Cons.currentVel;
import static br.com.varchar.Cons.initialVel;
import static br.com.varchar.Cons.tileHeight;

public class MainClass extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;

	private Array<Row> rows;

	private float totalTime;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		rows = new Array<Row>();

		rows.add(new Row(0, 0));
		rows.add(new Row(tileHeight, 1));
		rows.add(new Row(tileHeight * 2, 2));

		totalTime = 0;
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin();

		for (Row row : rows) {
			row.draw(shapeRenderer);
		}

		shapeRenderer.end();
	}

	private void update(float deltaTime) {
		totalTime += deltaTime;

		currentVel = initialVel + tileHeight * totalTime / 8;
		for (Row row: rows) {
			row.update(deltaTime);
		}
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

}
