package eu.minevalley.core.api.misc;

public interface Countdown {

    Countdown setLength(long length);

    Countdown setPeriod(long period);

    Countdown on(long time, Runnable callback);

    Countdown onEnd(Runnable callback);

    Countdown onPeriod(Runnable callback);

    Countdown onTick(Runnable callback);

    Countdown start();

    Countdown stop();

    long getLeft();

    long getPeriod();

    long getLength();

    long getNow();

    void setLeft(long left);

    boolean isStarted();

    void performTick();
}