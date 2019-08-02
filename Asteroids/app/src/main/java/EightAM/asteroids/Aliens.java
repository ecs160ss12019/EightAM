package EightAM.asteroids;

enum Aliens {
    Small(0, .3f),
    Big(1, .4f),
    KamiKaze(2, .3f);

    final int index;
    final float probOfSpawning;

    Aliens(int index, float probOfSpawning) {
        this.index = index;
        this.probOfSpawning = probOfSpawning;
    }
}
