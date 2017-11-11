package br.com.varchar;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by josevieira on 11/11/17.
 */
public class Cons {

    public static Color verde = new Color(0, 0.4f, 0, 1);

    public static Color correct = new Color(0.655f, 0.988f, 0.604f, 1);

    public static Color wrong = new Color(0.71f, 0.282f, 0.302f, 1);

    public static int screenX = Gdx.graphics.getWidth();

    public static int screenY = Gdx.graphics.getHeight();

    public static int tileWidth = screenX / 4;

    public static int tileHeight = screenY / 4;

    public static float initialVel = 2 * tileHeight / 1f;

    public static float currentVel = 0;

}
