package com.layer_net.rxandroidex;

/**
 * Created by layer on 19/1/2559.
 */
public class MainBus extends RxEventBus{
    private static MainBus instance;

    public static MainBus getInstance() {
        if (instance == null)
            instance = new MainBus();
        return instance;
    }

    private MainBus() {
    }
}
