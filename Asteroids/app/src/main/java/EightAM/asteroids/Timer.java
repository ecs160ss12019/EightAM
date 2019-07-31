package EightAM.asteroids;

public class Timer {
    long target;
    long start;
    long curr;
    boolean reachedTarget = false;
    private Direction direction;

    Timer(long target, long start) {
        this.target = target;
        this.start = start;
        this.curr = start;
        if (target >= start) {
            direction = Direction.Up;
        } else {
            direction = Direction.Down;
        }
    }

    void reset() {
        curr = start;
        reachedTarget = false;
    }

    boolean update(long updateTicks) {
        this.curr += updateTicks;
        if (direction == Direction.Up) {
            if (curr >= target) {
                reachedTarget = true;
                return true;
            }
        } else {
            if (curr <= target) {
                reachedTarget = true;
                return true;
            }
        }
        return false;
    }

    private enum Direction {
        Up, Down
    }
}
