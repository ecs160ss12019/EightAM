package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import java.util.Collections;
import java.util.Random;

import EightAM.asteroids.specs.TeleportShipSpec;

public class TeleportShip extends AbstractShip {

    // ---------------Member variables-------------
    private Timer teleportCooldownTimer;
    private Timer teleportDelayTimer;
    private boolean teleporting = false;

    // sound ids
    int teleportID;

    /**
     * Main constructor that loads in the spec. Only ever used once
     * per spec.
     */
    TeleportShip(TeleportShipSpec spec) {
        super(spec);
        this.teleportCooldownTimer = new Timer(spec.teleportCooldown);
        this.teleportDelayTimer = new Timer(spec.teleportDelay);
        this.teleportID = spec.teleport;
    }

    /**
     * The copy constructor, copies attributes from a TeleportShip prototype.
     */
    TeleportShip(TeleportShip ship) {
        super(ship);
        this.teleportCooldownTimer = new Timer(ship.teleportCooldownTimer);
        this.teleportDelayTimer = new Timer(ship.teleportDelayTimer);
        this.teleportID = ship.teleportID;
    }


    /**
     * Updates the position of the ship based on speed (a function of time).
     * and decrements the object specific counters
     */
    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        teleportAbility(timeInMillisecond);
    }


    /**
     * Teleport enables the HyperSpace ability of the TeleportShip.
     * Though, a delay and cooldown timers are introduced to prevent
     * abuse.
     */
    void teleportAbility(long deltaTime) {
        teleportCooldownTimer.update(deltaTime);
        if (teleporting && teleportCooldownTimer.reachedTarget
                && teleportDelayTimer.update(deltaTime)) {
            audioListener.sendSoundCommand(teleportID);
            eventHandler.teleportObjects(Collections.singleton(getID()));
            teleportCooldownTimer.reset();
            teleportDelayTimer.reset();
            teleporting = false;
            paint.setColorFilter(null);
        } else if (teleporting && !teleportCooldownTimer.reachedTarget) {
            //Log.d(this.getClass().getCanonicalName(), "Teleport Ability not yet available");
            teleporting = false;
        }
    }

    /**
     * Changes currPlayerShip values with respect to user input
     */
    @Override
    public void input(InputControl.Input i) {
        super.input(i);

        if (i.DOWN) {
            teleporting = true;
        }
    }

    void drawTeleporting() {
        if (teleporting && teleportCooldownTimer.reachedTarget
                && !teleportDelayTimer.reachedTarget) {
            Random r = new Random();
            paint.setColorFilter(
                    new PorterDuffColorFilter(
                            Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)),
                            PorterDuff.Mode.SRC_IN));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        drawTeleporting();
        super.draw(canvas);
    }

    @Override
    public Object makeCopy() {
        return new TeleportShip(this);
    }

    @Override
    public float getPercentageCharged() {
        return 1 - (float) teleportCooldownTimer.remaining() / teleportCooldownTimer.total();
    }
}
