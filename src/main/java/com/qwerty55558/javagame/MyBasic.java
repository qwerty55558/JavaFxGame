package com.qwerty55558.javagame;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import com.qwerty55558.javagame.consts.EntityType;
import com.qwerty55558.javagame.consts.MoveDirection;
import com.qwerty55558.javagame.consts.PropertyName;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getWorldProperties;

public class MyBasic extends GameApplication {
    private Entity player,coin;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1000);
        settings.setHeight(1000);
        settings.setTitle("Basic Game");
        settings.setVersion("0.1");
    }

    @Override
    protected void initPhysics() {
        // 코인 줍줍
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                coin.removeFromWorld();
                FXGL.inc(PropertyName.Coin.name(), 100);
                FXGL.play("coin.wav");
            }
        });

        // 플레이어와 벽의 충돌 처리 (내용은 비워둠)
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.WALL) {
            // onCollisionBegin 등을 오버라이드할 필요 없음
        });
    }

    @Override
    protected void initGame() {
        int mapWidth = 1600;
        int mapHeight = 1600;

        FXGL.getGameWorld().addEntityFactory(new SpawnEntityFactory());

        player = FXGL.spawn("Player",new SpawnData(300,300).put("width",96).put("height",96));
        coin = FXGL.spawn("Coin",new SpawnData(500,200).put("width",32).put("height",32));
        FXGL.getGameScene().getViewport().setBounds(0, 0, mapWidth, mapHeight);
        FXGL.getGameScene().getViewport().bindToEntity(
                player,
                FXGL.getAppWidth() / 2.0,
                FXGL.getAppHeight() / 2.0
        );
        FXGL.spawn("wall", new SpawnData(0, 0).put("width", mapWidth).put("height", 10));
        FXGL.spawn("wall", new SpawnData(0, mapHeight-10).put("width", mapWidth).put("height", 10));
        FXGL.spawn("wall", new SpawnData(0, 0).put("width", 10).put("height", mapHeight));
        FXGL.spawn("wall", new SpawnData(mapWidth-10, 0).put("width", 10).put("height", mapHeight));


    }

    @Override
    protected void initInput() {
//        FXGL.onKey(KeyCode.D, () -> {
//            player.translateX(5); // move right 5 pixels
//        });
        FXGL.getInput().addAction(new UserAction(MoveDirection.RIGHT.name()) {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimeComponent.class).moveRight();
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction(MoveDirection.LEFT.name()) {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimeComponent.class).moveLeft();
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction(MoveDirection.UP.name()) {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimeComponent.class).moveUp();
            }
        }, KeyCode.W);

        FXGL.getInput().addAction(new UserAction(MoveDirection.DOWN.name()) {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimeComponent.class).moveDown();
            }
        }, KeyCode.S);
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);

        textPixels.textProperty().bind(getWorldProperties().intProperty(PropertyName.Coin.name()).asString());

        Texture texture = FXGL.getAssetLoader().loadTexture("brick.png");
        texture.setTranslateX(50);
        texture.setTranslateY(450);


        FXGL.getGameScene().addUINode(textPixels);
        FXGL.getGameScene().addUINode(texture);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put(PropertyName.Coin.name(), 0);
    }
}
