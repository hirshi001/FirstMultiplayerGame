package com.hirshi001.multiplayerrotmg.field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.multiplayerrotmg.registry.registrations.DisposableRegistry;

public class Block implements Disposable {

    public static final int BLOCKWIDTH = 16, BLOCKHEIGHT = 16;
    private Texture t;
    private boolean collidable;
    private final short id;

    public Block(short id){
        this.id = id;
    }

    public short getId(){
        return id;
    }

    public Block setTexture(String path){
        try {
            //Scale the texture to have width and height of BlockWidth and BlockHeight
            Pixmap pixmap200 = new Pixmap(Gdx.files.internal(path));
            Pixmap pixmap100 = new Pixmap(BLOCKWIDTH, BLOCKHEIGHT, pixmap200.getFormat());
            pixmap100.drawPixmap(pixmap200,
                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
            );
            t = new Texture(pixmap100);
            DisposableRegistry.addDisposable(t);
            pixmap200.dispose();
            pixmap100.dispose();

        } catch(Exception e){e.printStackTrace();}
        return this;
    }

    public Texture getTexture(){return t;}

    public Block collidable(boolean collidable){
        this.collidable = collidable;
        return this;
    }

    public boolean isCollidable(){return collidable;}

    @Override
    public void dispose() {
        t.dispose();
    }
}
