package br.com.varchar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import static br.com.varchar.Cons.currentVel;
import static br.com.varchar.Cons.initialVel;
import static br.com.varchar.Cons.screenX;
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

	private SpriteBatch spriteBatch;

	private Texture texture;

	private Piano piano;

	private BitmapFont font;

	private GlyphLayout glyphLayout;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		piano = new Piano("natal");
		glyphLayout = new GlyphLayout();

		setupFont();

		texture = new Texture("iniciar.png");
		shapeRenderer.setAutoShapeType(true);
		rows = new Array<Row>();
		random = new Random();
		initGame();
	}

	private void setupFont() {
		FreeTypeFontGenerator.setMaxTextureSize(2048);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));

		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int)(0.1f*screenX);
		parameter.color = Color.CYAN;

		font = generator.generateFont(parameter);
		generator.dispose();
	}

	private float getWidth(BitmapFont font, String text) {
		glyphLayout.reset();
		glyphLayout.setText(font, text);
		return glyphLayout.width;
	}

	@Override
	public void render () {
		input();

		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// ROWS
		shapeRenderer.begin();
		for (Row row : rows) {
			row.draw(shapeRenderer);
		}
		shapeRenderer.end();

		// GUI
		spriteBatch.begin();

		if (state == 0) spriteBatch.draw(texture, 0, tileHeight/4, screenX, tileHeight/2);

		font.draw(spriteBatch, String.valueOf(score), 0, screenY);

		String fontVel = String.format("%.3f", currentVel/tileHeight);

		font.draw(spriteBatch, fontVel , screenX - getWidth(font, fontVel), screenY);

		spriteBatch.end();

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
							//piano.play();
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

		piano.reset();
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
		spriteBatch.dispose();
		texture.dispose();
		piano.dispose();
	}

}
