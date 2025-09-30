package com.qwerty55558.javagame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.qwerty55558.javagame.consts.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jdk.jfr.Timespan;

public class SpawnEntityFactory implements EntityFactory {

    @Spawns("Player")
    public Entity Player(SpawnData data){
        return FXGL.entityBuilder()
                .type(EntityType.PLAYER)
                .at(data.getX(), data.getY())
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PlayerAnimeComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("Coin")
    public Entity Coin(SpawnData data){
        return FXGL.entityBuilder()
                .type(EntityType.COIN)
                .at(data.getX(), data.getY())
                .viewWithBBox("coin.png")
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityType.WALL)
                .with(new CollidableComponent(true))
                .at(data.getX(), data.getY())
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .view(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.RED))
                .build();
    }

}
