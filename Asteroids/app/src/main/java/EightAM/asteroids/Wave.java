package EightAM.asteroids;

import static EightAM.asteroids.Constants.WAVE_GRACE_PERIOD;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Pair;

import EightAM.asteroids.interfaces.AudioGenerator;
import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.interfaces.EventHandler;

/**
 * The Wave Class implements a state pattern:
 * InWave is a state during an active wave
 * OutWave for the grace period in between waves
 */

class Wave implements AudioGenerator {
    WaveMode waveMode;
    EventHandler eventHandler;
    Pair<RectF, RectF> spawnBox;
    Timer durationTimer;
    double alienSpawnProb;
    double alienSpawnProbInc;
    int asteroidInc;

    int maxAliens;
    int aliensSpawned;
    int currAliens;

    int asteroidSpawnCount;
    int currAsteroids;

    int maxPowerups;
    int powerupsSpawned;
    int currPowerups;
    private AudioListener audioListener;


    Wave(EventHandler eventHandler, RectF boundaries, RectF spawnBoundaries, int startingAliens,
            int startingAsteroids, int startingPowerups, int waveGracePeriod, double alienSpawnProb,
            double alienSpawnProbInc) {
        this.eventHandler = eventHandler;
        this.spawnBox = new Pair<>(boundaries, spawnBoundaries);
        this.waveMode = new InWave(0);
        this.maxAliens = startingAliens;
        this.asteroidSpawnCount = startingAsteroids;
        this.maxPowerups = startingPowerups;
        this.durationTimer = new Timer(waveGracePeriod);
        this.alienSpawnProb = alienSpawnProb;
        this.alienSpawnProbInc = alienSpawnProbInc;
    }

    void updateDuration(long timePassed) {
        this.durationTimer.update(timePassed);
        waveMode.update(this, eventHandler);
    }

    void updateAliens(int i) {
        currAliens += i;
    }

    void updateAsteroids(int i) {
        currAsteroids += i;
    }

    public void updatePowerups(int i) {
        currPowerups += i;
    }

    public void setWaveMode(WaveMode waveMode) {
        this.waveMode = waveMode;
    }

    @Override
    public void registerAudioListener(AudioListener listener) {
        this.audioListener = listener;
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
        boolean playedAlienWave;

        InWave(int waveNumber) {
            super(waveNumber);
        }

        /**
         * Updates the wave by incrementing the difficulty, increasing aliens and asteroids iff
         * the space is clear. Then, the wave enters a grace period (OutWave state)
         * Else, update will randomly spawn an alien.
         */
        @Override
        void update(Wave wave, EventHandler eventHandler) {
            if (wave.currAsteroids == 0 && wave.currAliens == 0) {
                wave.asteroidSpawnCount += waveNumber % 2;
                wave.maxAliens += waveNumber % 2;
                wave.alienSpawnProb += wave.alienSpawnProbInc;
                wave.aliensSpawned = 0;
                wave.durationTimer.reset();
                wave.powerupsSpawned = 0;
                eventHandler.sendMessage(
                        new Messages.MessageWithFade("Wave " + waveNumber, Color.WHITE,
                                WAVE_GRACE_PERIOD, Messages.FontSize.Large,
                                Messages.HorizontalPosition.Center,
                                Messages.VerticalPosition.Center, 1500)
                );
                wave.setWaveMode(new OutWave(this.waveNumber + 1));
            }
            doAction(wave, eventHandler);
        }

        @Override
        void doAction(Wave wave, EventHandler eventHandler) {
            if (Math.random() < wave.alienSpawnProb && wave.aliensSpawned < wave.maxAliens) {
                eventHandler.createObjects(AlienGenerator.createAlien(
                        new Point((int) wave.spawnBox.first.right,
                                (int) wave.spawnBox.first.bottom)));
                wave.aliensSpawned++;
                if (!playedAlienWave) {
                    wave.audioListener.onAlienWave();
                    playedAlienWave = true;
                }
            }
        }

    }

    // In between waves
    static class OutWave extends WaveMode {

        OutWave(int waveNumber) {
            super(waveNumber);
        }

        /**
         * Decrements the counter for the wave grace period.
         * Once fully decremented, the next wave state is commenced (InWave state)
         */
        @Override
        void update(Wave wave, EventHandler eventHandler) {
            if (wave.durationTimer.reachedTarget) {
                wave.durationTimer.reset();
                doAction(wave, eventHandler);
                wave.setWaveMode(new InWave(this.waveNumber));
            }
        }

        @Override
        void doAction(Wave wave, EventHandler eventHandler) {
            eventHandler.createObjects(
                    AsteroidGenerator.createBelt(wave.spawnBox.first, wave.spawnBox.second,
                            wave.asteroidSpawnCount));
            wave.audioListener.onAsteroidWave();
        }
    }
}
