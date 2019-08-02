package EightAM.asteroids;

import android.graphics.Color;

import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.specs.LaserWeaponSpec;
import EightAM.asteroids.specs.ShotgunWeaponSpec;

public enum Powerups {
    Laser(0, 10000), // Time limit in milliseconds
    Shotgun(1, 150), // each shot fires 10 bullets so 1000 bullets = 100 shots
    Score(2, 500),
    Invincible(3, 10000), // time limit in MS
    Life(4, 1);

    public final int index;
    public final int amount;

    Powerups(int index, int amount) {
        this.index = index;
        this.amount = amount;
    }

    static abstract class Powerup {
        abstract void applyPowerUp(EventHandler eventHandler);
    }


    static class LaserPowerup extends Powerup {
        int durationMS;

        public LaserPowerup(int durationMS) {
            this.durationMS = durationMS;
        }

        @Override
        void applyPowerUp(EventHandler eventHandler) {
            LaserWeapon laserWeapon = (LaserWeapon) BaseWeaponFactory.getInstance().createWeapon(
                    new LaserWeaponSpec());
            laserWeapon.timeLimit.resetTimer(durationMS);
            eventHandler.givePrimaryWeapon(laserWeapon);
        }
    }

    static class ShotgunPowerup extends Powerup {
        int ammoCount;

        public ShotgunPowerup(int ammoCount) {
            this.ammoCount = ammoCount;
        }

        @Override
        void applyPowerUp(EventHandler eventHandler) {
            Weapon weapon = BaseWeaponFactory.getInstance().createWeapon(new ShotgunWeaponSpec());
            ((ShotgunWeapon) weapon).ammoCount.resetTimer(ammoCount);
            eventHandler.givePrimaryWeapon(weapon);
        }
    }

    static class ScorePowerup extends Powerup {
        int points;

        public ScorePowerup(int points) {
            this.points = points;
        }

        @Override
        void applyPowerUp(EventHandler eventHandler) {
            eventHandler.processScore(
                    new DestroyedObject(points, ObjectID.getNewID(Faction.Neutral),
                            ObjectID.getNewID(Faction.Player), null));
            eventHandler.sendMessage(new Messages.MessageWithFade(points + "+", Color.WHITE, 500,
                    Messages.FontSize.Small, Messages.HorizontalPosition.Center,
                    Messages.VerticalPosition.Bottom, 500)
            );
        }
    }

    static class InvincibilityPowerup extends Powerup {
        int durationMS;

        InvincibilityPowerup(int durationMS) {
            this.durationMS = durationMS;
        }

        @Override
        void applyPowerUp(EventHandler eventHandler) {
            eventHandler.giveInvincibility(durationMS);
        }
    }

    static class LifePowerup extends Powerup {
        int lives;

        public LifePowerup(int lives) {
            this.lives = lives;
        }

        @Override
        void applyPowerUp(EventHandler eventHandler) {
            eventHandler.incrementLife(lives);
        }
    }

}
