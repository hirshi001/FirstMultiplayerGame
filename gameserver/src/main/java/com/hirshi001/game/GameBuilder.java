package com.hirshi001.game;


import com.hirshi001.field.Field;
import com.hirshi001.gameadapter.GameApplicationAdapter;

public class GameBuilder {

    private Field field;
    private GameApplicationAdapter adapter;

    public GameBuilder(Field field){
        this.field = field;
    }

    public GameBuilder gameApplicationAdapter(GameApplicationAdapter adapter){
        this.adapter = adapter;
        return this;
    }

    public Game build(){
        return build(new Game());
    }

    public Game build(Game game){
        game.setField(field).setGameApplicationAdapter(adapter);
        field.setGame(game);
        adapter.setGame(game);
        return game;
    }






}
