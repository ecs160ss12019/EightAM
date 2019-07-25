package EightAM.asteroids;

final class EndGameStats {
    private int endScore;
    private int livesLeft;

    EndGameStats(GameStats gs) {
        endScore = gs.score;
        livesLeft = gs.livesLeft;
    }

    int getEndScore() { return endScore;}

    int getLivesLeft() { return livesLeft;}
}
