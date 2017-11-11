package br.com.varchar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by josevieira on 11/11/17.
 */
public class Piano {

    private final Array<String> notas;

    private Map<String, Sound> sounds;

    private int index = 0;

    public Piano(String music) {
        FileHandle file = Gdx.files.internal(music + ".txt");
        String texto = file.readString();
        notas = new Array<String>(texto.split(" "));

        sounds = new HashMap<String, Sound>();

        for (String nota : notas) {

            if (!sounds.containsKey(nota)) {
                //sounds.put(nota, Gdx.audio.newSound(Gdx.files.internal("data/music/d.wav")));
            }
        }
    }

    public void play() {
        sounds.get(notas.get(index)).play();
        index++;

        if (index == notas.size) {
            index = 0;
        }
    }

    public void reset() {
        index = 0;
    }

    public void dispose() {
        for (String key: sounds.keySet()) {
            sounds.get(key).dispose();
        }
    }

}
