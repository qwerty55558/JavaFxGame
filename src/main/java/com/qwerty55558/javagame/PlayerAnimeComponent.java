package com.qwerty55558.javagame;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.qwerty55558.javagame.consts.AnimeType;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class PlayerAnimeComponent extends Component {
    // ★★★ speed를 speedX와 speedY로 분리
    private int speedX = 0;
    private int speedY = 0;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public PlayerAnimeComponent() {
        // 애니메이션 채널 설정은 그대로 둡니다.
        this.animWalk = new AnimationChannel(FXGL.image(AnimeType.monkRun.getPath()),4,192,192, Duration.millis(400),0,3);
        this.animIdle = new AnimationChannel(FXGL.image(AnimeType.monkIdle.getPath()),6,192,192, Duration.millis(600),0,5);
        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(96,96));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        // ★★★ X축과 Y축으로 모두 움직이도록 수정
        entity.translateX(speedX * tpf);
        entity.translateY(speedY * tpf);

        // ★★★ X 또는 Y축으로 움직임이 있을 때 걷는 애니메이션 재생
        if (speedX != 0 || speedY != 0) {
            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }
        }

        // ★★★ 감속 로직을 X, Y 모두에 적용
        speedX = (int) (speedX * 0.9);
        speedY = (int) (speedY * 0.9);

        // ★★★ X와 Y의 속도가 모두 0에 가까워지면 멈춤
        if (FXGLMath.abs(speedX) < 1 && FXGLMath.abs(speedY) < 1) {
            speedX = 0;
            speedY = 0;

            // ★★★ 멈췄을 때만 idle 애니메이션으로 전환
            if (texture.getAnimationChannel() == animWalk) {
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void moveRight() {
        speedX = 300;
        getEntity().setScaleX(1); // 오른쪽 볼 때 원본 방향
    }

    public void moveLeft(){
        speedX = -300;
        getEntity().setScaleX(-1); // 왼쪽 볼 때 X축으로 뒤집기
    }

    // ★★★ moveUp과 moveDown 수정
    public void moveUp(){
        // FXGL 좌표계에서는 Y값이 작아질수록 위로 갑니다.
        speedY = -300;
    }

    public void moveDown(){
        // FXGL 좌표계에서는 Y값이 커질수록 아래로 갑니다.
        speedY = 300;
    }
}