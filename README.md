# MazeRunner Game

MazeRunner is a 2D top-down view game built using the libGDX framework. In this game, you navigate through mazes, collect keys, avoid traps and enemies, and aim to reach the exit while preserving your limited lives.

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
MazeRunner presents you with challenging mazes to explore. Your objective is to navigate through the maze, collect keys to unlock the exit, and reach it while avoiding traps and enemies. Keep an eye on your remaining lives and make use of the HUD to track your progress. Victory awaits those who successfully conquer the maze!

## Features
MazeRunner offers the following features:

- Dynamic mazes loaded from Java properties files.
- Character movement in four directions (up, down, left, right).
- Limited lives for the character.
- Collectible keys to unlock the exit.
- Various obstacles, including static traps and dynamic enemies.
- Intelligent enemy movement (optional).
- Camera movement to keep the character in view.
- HUD displaying lives remaining and key collection status.
- Game menu for pausing, restarting, or exiting.
- Victory and game over screens with the option to return to the main menu.
- Background music and sound effects.
- Support for different screen sizes.
- Object-oriented code structure with inheritance and proper documentation.

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
- **Character:** You control the main character, which has limited lives. The character must collect at least one key to use the exit.
- **Obstacles:** The maze contains static traps and dynamic enemies. Contact with these obstacles causes the character to lose a life.
- **Keys:** Collect keys to unlock the exit. Attempting to exit without a key will block the path.
- **HUD:** The HUD displays the number of lives remaining and whether a key has been collected.
- **Game Menu:** Access the game menu to continue, start a new game with a different maze, or exit the game.
- **Victory and Game Over:** Win the game by reaching the exit without losing all lives. If you lose all lives, the game is over, and you can return to the main menu.

## Extensions
This implementation of MazeRunner includes several extensions:

- **Intelligent Enemies:** Enemies exhibit intelligent behavior and can pathfind toward the character within a certain range.
- **Point System:** Players earn a score based on their performance, such as completing the maze quickly.
- **Collectable Lives:** Lives can be collected as power-ups within the maze.
- **Additional Obstacles:** You can explore various types of obstacles beyond traps and keys.
- **Player Abilities:** The main character can have additional abilities, such as combat with enemies or power-ups for enhanced capabilities.
- **Fog of War:** Implement a fog of war mechanic to obscure unexplored areas of the maze.
- **Movable Walls:** Introduce movable walls that can be pushed by the character.
- **Multiple Exit Conditions:** Create additional conditions for opening the exit beyond collecting keys.

Feel free to further extend the game based on your preferences and creativity.

## Documentation
The code for MazeRunner is well-documented using JavaDoc comments. Each class and method is documented to provide clarity and understanding of the codebase.

## Credits
MazeRunner uses open-source 2D assets (images) from sources like Kenney and OpenGameArt for rendering game objects and animations. The background music and sound effects are sourced from royalty-free tracks and effects available in the public domain.

If you use external code or assets from the internet, the source URLs are provided in the JavaDoc comments of the respective code sections.

Enjoy playing MazeRunner and have fun exploring challenging mazes!