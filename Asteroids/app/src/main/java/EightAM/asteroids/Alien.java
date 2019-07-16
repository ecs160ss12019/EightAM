package EightAM.asteroids;

class Alien extends GameObject {
    // ---------------Member variables-------------
    enum Size { SMALL, LARGE }; // denotes what kind of alien
    private Size size;
    // ---------------Member methods --------------

    protected Alien(Size s) {
        size = s;

        // might use later
        this.objectID = ObjectID.ALIEN;
    }


}
