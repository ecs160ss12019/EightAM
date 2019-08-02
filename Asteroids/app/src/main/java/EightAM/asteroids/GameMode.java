package EightAM.asteroids;

abstract class GameMode {
    MainActivity activity;

    public GameMode(MainActivity activity) {
        this.activity = activity;
    }

    abstract void transitionTo(GameMode mode);
}

class StartScreen extends GameMode {

    public StartScreen(MainActivity activity) {
        super(activity);
    }

    @Override
    void transitionTo(GameMode mode) {
        // can only transition to ship select
        if (mode instanceof ShipSelection) {
            activity.hideStartScreen();
            activity.showShipSelect();
            ShipSelect shipSelect = new ShipSelect(activity);
            shipSelect.setListeners();
            activity.mode = mode;
        }
    }
}

class ShipSelection extends GameMode {

    public ShipSelection(MainActivity activity) {
        super(activity);
    }

    @Override
    void transitionTo(GameMode mode) {
        // can only transition to in game
        if (mode instanceof InGame) {
            activity.hideShipSelect();
            activity.showButtons();
            activity.mode = mode;
        }
    }
}

class InGame extends GameMode {

    public InGame(MainActivity activity) {
        super(activity);
    }

    @Override
    void transitionTo(GameMode mode) {
        if (mode instanceof Pause) {
            activity.hideButtons();
            activity.showPauseScreen();
            activity.mode = mode;
        } else if (mode instanceof GameOver) {
            activity.hideButtons();
            activity.showRestartScreen();
            activity.mode = mode;
        }
    }
}

class Pause extends GameMode {

    public Pause(MainActivity activity) {
        super(activity);
    }

    @Override
    void transitionTo(GameMode mode) {
        if (mode instanceof InGame) {
            activity.hidePauseScreen();
            activity.showButtons();
            activity.mode = mode;
        }
    }
}

class GameOver extends GameMode {

    public GameOver(MainActivity activity) {
        super(activity);
    }

    @Override
    void transitionTo(GameMode mode) {
        if (mode instanceof StartScreen) {
            activity.hideRestartScreen();
            activity.showStartScreen();
            activity.mode = mode;
        }
    }
}

