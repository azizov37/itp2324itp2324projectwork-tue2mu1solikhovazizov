# MazeRunner Game

MazeRunner is a 2D top-down view game built using the libGDX framework. In this game, you navigate through mazes, collect keys, avoid traps and enemies, and aim to reach the exit while preserving your limited lives.


![MazeRunner Screenshot](assets\Screenshot_Game.png)
## Table of Contents
- [Game Overview](#game-overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Controls](#controls)
- [Game Mechanics](#game-mechanics)
- [Extensions](#extensions)
- [Documentation](#documentation)
- [Credits](#credits)

## Game Overview
MazeRunner presents you with challenging mazes 
to explore. Your objective is to navigate through the maze, 
collect keys to unlock the exit, and reach it while avoiding
traps and enemies. Keep an eye on your remaining lives. Victory awaits 
those who successfully conquer the maze!

## Features
MazeRunner offers the following features:

- Dynamic mazes loaded from Java properties files.
- Character movement in four directions (up, down, left, right).
- Limited lives for the character.
- Collectible keys to unlock the exit.
- Various obstacles, including static traps and dynamic enemies as well as pathfinding intelligent enemy.
- Game menu for pausing, restarting, or exiting.
- Victory and game over screens with the option to return to the main menu.
- Background music and sound effects.

## Getting Started
To play MazeRunner, follow these steps:

1. Clone this repository to your local machine.
https://github.com/your-username/MazeRunner.git

2. Import the project into your preferred Java development environment, such as IntelliJ IDEA or Eclipse.

3. Run the game by executing the `DesktopLauncher.java` file located in the `desktop` module.

4. Enjoy the game! Use the keyboard controls to navigate the maze and collect keys to unlock the exit.

## Controls
- Arrow keys: Move the character (up, down, left, right).
- Spacebar: Pause or resume the game.
- Esc key: Access the game menu (pause the game).

## Game Mechanics
- **Maze Structure:** The maze consists of walls and paths. The entrance and exit are distinguishable, and exits are located on the outer border of the maze.
- **Character:** You control the main character, which has limited lives. 
The character must collect at least one key to use the exit. Character is controlled using arrow keys, movement in diagonal direction is not possible.
- **Obstacles.** The maze in this game features two types of obstacles:
  1. **Static Traps:** These are stationary obstacles placed within the maze. When the character comes into contact with a static trap, they lose a life.

  2. **Dynamic Enemies:** Dynamic enemies are mobile obstacles that move in random directions within the maze. They continue moving until they encounter an obstacle, which can be a wall, trap, or exit. Alternatively, dynamic enemies may move a certain distance in their current direction before choosing a new random direction to continue their movement.
    - These obstacles add complexity and challenge to the gameplay, requiring the player to navigate carefully to avoid losing lives when encountering traps or dynamic enemies.

- **Keys:** Collect keys to unlock the exit. Attempting to exit without a key will block the path.
- **HUD:** The HUD displays the number of lives remaining and whether a key has been collected as well as Player's name. 
- **Game Menus.** Access the game menu to continue, start a new game with a different maze, or exit the game.

  - **Main Menu**
    The Main Menu is the initial screen where the player can perform the following actions:
    - Enter a player name (mandatory to start the game).
  - **Map Selection Menu**
  The Map Selection Menu allows the player to:
    - Choose a game level from predefined maze options.
    - Upload a custom maze file if desired.
  - **In-Game Pause Menu**
  While playing the game, the player can access the Pause Menu by pressing SPACE key, where they can:
    - Resume the game.
    - Return to the Main Menu.
    - Restart the current game level.
  - **Game Over Menu**
  When the player loses the game, they are taken to the Game Over Menu, where they can:
    - Return to the Map Selection Menu to start a new game.
    - Choose a different game level from the Map Selection Menu.
  - **Victory Menu**
  Upon winning the game, the player is presented with the Victory Menu, where they can:
    - Return to the Map Select  Menu to start a new game.
- 
- **Victory and Game Over:** Win the game by reaching the exit without losing all lives. If you lose all lives, the game is over, and you can return to the main menu.

## Extensions
This implementation of MazeRunner includes several extensions:

**Intelligent Enemy - Yovuz**

Allow us to introduce Yovuz, the drowsy Ghost! Yovuz is a unique adversary in the game, adding an extra layer of intrigue to your maze-solving adventures. Yovuz's behavior is anything but ordinary:

- **Slumber Cycles:** Yovuz doesn't start in a typical "sleep" mode. Instead, he operates on a cyclical pattern, dozing off at times and then rousing from his slumber. During his active phases, he can awaken and commence his ghostly patrol.

- **Alert and Astute:** When Yovuz awakens, he becomes astutely aware of his surroundings. Get too close or step into his designated detection zone, and Yovuz springs into action, fully alert. In this mode, he's a master at tracking the player's every move, so careful planning is essential to stay out of his reach.

- **Phantom Skills:** Yovuz, being a Ghost, boasts a unique set of spectral talents. He effortlessly glides through walls and obstacles, making him an enigmatic and formidable presence.

- **Player Interactions:** Yovuz doesn't shy away from interaction. On contact with the player, he can deal damage, resulting in the loss of one of the player's lives. Players must employ cunning strategies, leading Yovuz away from the Key when he's awake and seizing the opportunity to collect it while steering clear of any encounters.

**Bonus Special User**

Discover a hidden feature that can enhance your gameplay experience:

- **Exclusive Access:** By entering the username "UZBEKVIP" when starting the game, you unlock a unique advantage that adds an exciting twist to your maze adventure.

- **Extra Lives:** Players using this special username will begin with additional lives, providing a buffer against the challenges of the maze. You can also replenish lives as needed during gameplay.

- **Strategic Advantage:** "UZBEKVIP" offers a strategic edge, allowing you to tackle tough levels with confidence. It's not just a cheat; it's a tool for mastering the game.

Use this special username to elevate your gaming experience and conquer the maze like never before!


## Documentation
The code for MazeRunner is well-documented using JavaDoc comments. Each class and method is documented to provide clarity and understanding of the codebase.

## Credits
MazeRunner uses open-source 2D assets (images) from pre-given sources for rendering game objects and animations. The background music and sound effects are sourced from royalty-free tracks and effects available in the public domain (Zapsplat.com) as well as images generated by DALL-E OpenAI.

Enjoy playing MazeRunner and have fun exploring challenging mazes!
