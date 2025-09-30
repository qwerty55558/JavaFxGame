package com.qwerty55558.javagame.consts;

public enum AnimeType {
    monkRun("monk/Run.png"),monkIdle("monk/Idle.png");
    private final String path;
    AnimeType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
