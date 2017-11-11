package br.com.varchar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static br.com.varchar.Cons.tileHeight;
import static br.com.varchar.Cons.tileWidth;
import static br.com.varchar.Cons.verde;

/**
 * Created by josevieira on 11/11/17.
 */
public class Row {

    private float y;

    private int correctTile;  //0..3

    public Row(float y, int correctTile) {
        this.y = y;
        this.correctTile = correctTile;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(verde);
        shapeRenderer.rect(correctTile*tileWidth, y, tileWidth, tileHeight);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);

        for (int i = 0; i < 3; i++) {
            shapeRenderer.rect(i * tileWidth, y, tileWidth, tileHeight);
        }
    }

}
