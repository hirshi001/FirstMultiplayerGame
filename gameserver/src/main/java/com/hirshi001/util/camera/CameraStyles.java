package com.hirshi001.util.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraStyles {

    public static Vector3 lerpToTarget(Vector3 cameraPos, Vector2 position, float factor){
        Vector2 pos = lerpToTarget(new Vector2(cameraPos.x, cameraPos.y), position, factor);
        cameraPos.x = pos.x;
        cameraPos.y = pos.y;
        return cameraPos;
    }

    public static Vector2 lerpToTarget(Vector2 cameraPos, Vector2 position, float factor){
        //linear interpolation
        cameraPos.x += (position.x-cameraPos.x) * factor;
        cameraPos.y += (position.y-cameraPos.y) * factor;
        return cameraPos;
    }

    public static OrthographicCamera center(OrthographicCamera camera, float width, float height){
        Vector2 centerScreen = new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        camera.position.x = centerScreen.x-width/2;
        camera.position.y = centerScreen.y-height/2;
        return camera;
    }

}
