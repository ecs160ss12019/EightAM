package EightAM.asteroids.interfaces;

public interface LimitedWeapon {
    boolean expired();

    int amountLeft();

    int amountTotal();
}
