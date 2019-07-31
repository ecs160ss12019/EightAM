package EightAM.asteroids;

import static EightAM.asteroids.Constants.MAX_ALIENS_PER_LEVEL;
import static EightAM.asteroids.Constants.WAVE_GRACE_PERIOD;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Pair;

import EightAM.asteroids.interfaces.EventHandler;

class Wave {
    WaveMode waveMode;
    EventHandler eventHandler;
    Pair<RectF, RectF> spawnBox;
    long duration;
    double alienSpawnProb;
    int aliensSpawned;
    int asteroidSpawnCount;
    int asteroidInc;

    int currAsteroids;
    int currAliens;

    Wave(EventHandler eventHandler, RectF boundaries, RectF spawnBoundaries) {
        this.eventHandler = eventHandler;
        this.spawnBox = new Pair<>(boundaries, spawnBoundaries);
        this.waveMode = new InWave(0);
    }

    void updateDuration(long timePassed) {
        this.duration += timePassed;
        waveMode.update(this, eventHandler);
    }

    void updateAliens(int i) {
        currAliens += i;
        waveMode.update(this, eventHandler);
    }

    void updateAsteroids(int i) {
        currAsteroids += i;
        waveMode.update(this, eventHandler);
    }

    public void setWaveMode(WaveMode waveMode) {
        this.waveMode = waveMode;
    }

    static abstract class WaveMode {
        int waveNumber;

        WaveMode(int waveNumber) {
            this.waveNumber = waveNumber;
        }

        abstract void update(Wave wave, EventHandler eventHandler);

        abstract void doAction(Wave wave, EventHandler eventHandler);
    }

    // While there are aliens and asteroids
    static class InWave extends WaveMode {
        InWave(int waveNumber) {
            super(waveNumber);
        }

        @Override
        void update(Wave wave, EventHandler eventHandler) {
            if (wave.currAsteroids == 0 && wave.currAliens == 0) {
                wave.aliensSpawned = 0;
                wave.asteroidSpawnCount += wave.asteroidInc;
                wave.duration = 0;
                eventHandler.sendMessage(
                        new Messages.MessageWithFade("Wave " + waveNumber, Color.WHITE,
                                WAVE_GRACE_PERIOD, -1,
                                -1, 1500)
                );
                wave.setWaveMode(new OutWave(this.waveNumber + 1));
            }
            if (Math.random() < wave.alienSpawnProb && wave.aliensSpawned < MAX_ALIENS_PER_LEVEL) {
                doAction(wave, eventHandler);
            }
        }

        @Override
        void doAction(Wave wave, EventHandler eventHandler) {
            eventHandler.createObjects(AlienGenerator.createAlien(
                    new Point((int) wave.spawnBox.first.right, (int) wave.spawnBox.first.bottom)));
            wave.aliensSpawned++;
        }

    }

    // In between waves
    static class OutWave extends WaveMode {

        OutWave(int waveNumber) {
            super(waveNumber);
        }

        @Override
        void update(Wave wave, EventHandler eventHandler) {
            if (wave.duration >= WAVE_GRACE_PERIOD) {
                wave.duration = 0;
                doAction(wave, eventHandler);
                wave.setWaveMode(new InWave(this.waveNumber));
            }
        }

        @Override
        void doAction(Wave wave, EventHandler eventHandler) {
            eventHandler.createObjects(
                    AsteroidGenerator.createBelt(wave.spawnBox.first, wave.spawnBox.second,
                            wave.asteroidSpawnCount));

        }
    }
}
