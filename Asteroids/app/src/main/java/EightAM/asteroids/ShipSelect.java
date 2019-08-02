package EightAM.asteroids;

import android.view.View;

class ShipSelect {
    MainActivity mainActivity;

    TeleportListener tp;
    LaserListener laser;
    BombListener bomb;

    ShipSelect(MainActivity activity) {
        this.mainActivity = activity;
        tp = new TeleportListener();
        laser = new LaserListener();
        bomb = new BombListener();
    }

    void setListeners() {
        mainActivity.findViewById(R.id.TeleportImg).setOnClickListener(tp);
        mainActivity.findViewById(R.id.LaserImg).setOnClickListener(laser);
        mainActivity.findViewById(R.id.BombImg).setOnClickListener(bomb);
    }

    class TeleportListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mainActivity.mode.transitionTo(new InGame(mainActivity));
            mainActivity.startGame(Ships.Teleport);
        }
    }

    class LaserListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mainActivity.mode.transitionTo(new InGame(mainActivity));
            mainActivity.startGame(Ships.Laser);
        }
    }

    class BombListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mainActivity.mode.transitionTo(new InGame(mainActivity));
            mainActivity.startGame(Ships.Bomb);
        }
    }

}
