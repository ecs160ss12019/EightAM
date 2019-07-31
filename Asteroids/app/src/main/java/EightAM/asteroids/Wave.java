package EightAM.asteroids;

import static EightAM.asteroids.Constants.BOUNDARY_OFFSET;
import static EightAM.asteroids.Constants.MAX_ALIENS_PER_LEVEL;
import static EightAM.asteroids.Constants.WAVE_GRACE_PERIOD;

import android.graphics.Point;
import android.graphics.RectF;
import android.util.Pair;

import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.specs.BasicShipSpec;

class Wave {
    WaveMode waveMode;
    EventHandler eventHandler;
    Pair<RectF, RectF> spawnBox;
    long duration;
    float alienSpawnProb;
    int aliensSpawned;
    int asteroidSpawnCount;
    int asteroidInc;

    int currAsteroids;
    int currAliens;

    Wave(EventHandler eventHandler, Point spaceSize) {
        this.eventHandler = eventHandler;
        this.spawnBox = new Pair<>(new RectF(0, 0, spaceSize.x, spaceSize.y),
                new RectF(-BOUNDARY_OFFSET, -BOUNDARY_OFFSET, spaceSize.x + BOUNDARY_OFFSET,
                        spaceSize.y + BOUNDARY_OFFSET));
        this.waveMode = new OutWave(0);
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
                wave.setWaveMode(new OutWave(this.waveNumber++));
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
            eventHandler.createObjects(AsteroidGenerator.createBelt(
                    new Point((int) wave.spawnBox.first.right, (int) wave.spawnBox.first.bottom),
                    new Ship(new BasicShipSpec()),
                    wave.asteroidSpawnCount)); // TODO: temporary very hacky
        }
    }
}
