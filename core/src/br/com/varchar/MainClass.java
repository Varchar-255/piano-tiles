package br.com.varchar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import static br.com.varchar.Cons.currentVel;
import static br.com.varchar.Cons.initialVel;
import static br.com.varchar.Cons.screenY;
import static br.com.varchar.Cons.tileHeight;

public class MainClass extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;

	private Array<Row> rows;

	private float totalTime;

	private int indexInfTiles;

	private int score;

	private Random random;

	private int state;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		rows = new Array<Row>();
		random = new Random();
		initGame();
	}

	@Override
	public void render () {
		input();

		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin();

		for (Row row : rows) {
			row.draw(shapeRenderer);
		}

		shapeRenderer.end();
	}

	private void input() {
		if (Gdx.input.justTouched()) {

			// Init a game...
			if (state == 0) {
				state = 1;
			}
			if (state == 1) {
				int x = Gdx.input.getX();
				int y = screenY - Gdx.input.getY();

				for (int i = 0; i < rows.size; i++) {
					int returnValue = rows.get(i).touch(x, y);
					if (returnValue != 0) {
						if (returnValue == 1 && i == indexInfTiles) {
							score++;
							indexInfTiles++;
						} else if (returnValue == 1) {
							rows.get(indexInfTiles).error();
							finish(0);
						} else {
							finish(0);
						}
						break;
					}
				}
			} else if (state == 2) {
				initGame();
			}
		}
	}

	private void finish(int opt) {
		Gdx.input.vibrate(200);

		state = 2;

		if (opt == 1) {
			for (Row row: rows) {
				row.y += tileHeight;
			}
		}
	}

	private void update(float deltaTime) {
		if (state == 1){
			totalTime += deltaTime;
			currentVel = initialVel + tileHeight * totalTime / 8;
			for (int i = 0; i < rows.size; i++) {
				int value = rows.get(i).update(deltaTime);
				rows.get(i).anim(deltaTime);
				if (value != 0) {
					if (value == 1) {
						rows.removeIndex(i);
						i--;
						indexInfTiles --;

						addRow();
					} else if (value == 2){
						finish(1);
					}
				}
			}
		} else if (state == 2) {
			for (Row row: rows) {
				row.anim(deltaTime);
			}
		}
	}

	private void addRow() {
		float y = rows.get(rows.size - 1).y + tileHeight;
		rows.add(new Row(y, random.nextInt(4)));
	}

	private void initGame() {
		totalTime = 0;
		indexInfTiles = 0;
		score = 0;
		state = 0;

		rows.clear();
		rows.add(new Row(tileHeight, random.nextInt(4)));

		addRow();
		addRow();
		addRow();
		addRow();
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

}
