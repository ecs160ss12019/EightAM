
# EightAM - Game Asteroids

Team EightAM will create a 2019 version of the classic multidirectional shooter arcade game "Asteroids" which was originally released in 1979 by Atari, Inc. The team members include:

* Adrian Wang, senior CSE major. He likes swimming.
* Tim Van, senior CS major. He is very serious.
* Liyin Li (Kenny), senior CSE major. She likes swimming.
* Melissa Goh, senior CS major. She likes the surprised pikachu meme.
* Irene Ho, senior CS major. She likes eating a lot.

[I am a link to StoryMapping.md](https://github.com/ecs160ss12019/EightAM/blob/master/StoryMapping.md)

##  CRC cards
![Link to CRC Cards for Assignment 2](https://github.com/ecs160ss12019/EightAM/blob/master/crc_cards.png)
### Division of Workload
* GameModel: Adrian
* Asteroid (Rock): Kenny, Tim
* Ship: Kenny, Melissa
* Projectile: Tim
* InputControl: Adrian
* Game Object: Irene
* Alien: Irene
* Space: Melissa

## Acceptance Tests
###  Sprint 1
##### _**Game**_
* Test with score increasing upon asteroid decimation (projectile + asteroid collision). (pass)
* Test with score increasing upon alien decimation (projectile + alien collision). (pass)
* Test with score staying the same when the ship moves (in every direction). (pass)
* Test with score resetting upon new game. (pass)
##### _**Ship**_
* Test with left/right key, expecting correct change in orientation of ship. (pass)
* Test with ‘up’ key, expecting ship to accelerate in direction of current orientation. (pass)
##### _**Space**_
* When a new game is triggered, object space is expected to be generated and displayed on the screen statically, and object asteroid(s) also would be generated  and moving at constant speed (not necessarily in constant velocity) on screen and wrap around if it is on the edge of the screen. (pass)
##### _**Aliens**_
* When a new game is triggered, object aliens are expected to be generated and moving in constant speed (not necessarily in constant velocity) on screen and wrap around if it is on the edge of the screen. (pass)
##### _**Asteroids**_
* Test with simplified shapes like circles to see movement of object (pass)
* Test with no Asteroid in space, spawn more asteroids. (Pass)
* Test with spawning Asteroid, it should contain a random velocity. (Pass) 

 ###  Sprint 2
##### _**Game**_
* Test with losing one life when ship collides with alien. (pass)
* Test with losing one life when ship collides with an asteroid. (pass)
* Test with losing one life when ship collides with alien projectile. (pass)
* Test with number of lives remaining the same if ship is shot by own projectile. (pass)
##### _**Ship**_
* Upon hitting the fire button, the spaceship creates projectile
##### _**Space**_
* Test inputting with player’s hitbox and all generated asteroid hitboxes, it should return true to indicate the existence of a collision if the two types of hit box has any intersection, false otherwise.
##### _**Aliens**_
* Test that alien shoots projectiles periodically.  (pass)
* Test with inputting player’s coordinates into object alien detection method so that projectiles are shot towards the player. (pass)
##### _**Asteroids**_
*  Test with bullet shot by player. The asteroid shot at should break into smaller pieces of same asteroids. (pass)


### 3) Sprint 3
##### _**Game**_
* Test with the option for a new game appearing as the player runs out of lives. (pass)
* Test with option for new game actually starting a new game. (pass)
##### _**Ship**_
* Ship should disappear and reappear in a random location when hyper space is activated
##### _**Space**_
* Test with calling render function of each game object, expecting objects to appear in space. (pass)
##### _**Aliens**_
* Test with alien shooting ship standing still. (pass)
* Test with ship with different velocities, alien should correctly orient itself and shoot at the predicted position of the ship  (pass)
##### _**Asteroids**_
* Test with powerUp object - once Ship touches/crosses that object on the screen, +1 game life. (pass)
