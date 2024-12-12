# TETRIS

## Prerequisites
- Java Development Kit (JDK)

## How to Run
### For Ubuntu/Linux
1. Build
   ```bash
   mkdir -p bin
   javac -d bin \
        src/audio/*.java \
        src/board/*.java \
        src/enums/*.java \
        src/main/*.java \
        src/mino/*.java \
        src/state/*.java \
        src/ui/*.java \
        src/utility/*.java
    
   cp -r ./assets/* ./bin
   ```
3. Run
   ```bash
   java -cp bin main.Main
   ```

### For Windows
1. Build
   ```bash
   mkdir bin
   javac -d bin ^
      src\audio\*.java ^
      src\board\*.java ^
      src\enums\*.java ^
      src\main\*.java ^
      src\mino\*.java ^
      src\state\*.java ^
      src\ui\*.java ^
      src\utility\*.java
  
   xcopy .\assets\* .\bin\ /E /I
   ```
2. Run
   ```bash
   java -cp bin main.Main
   ```

## Description
TETRIS is a puzzle-based video game. In this game, players are tasked with arranging blocks of various shapes (called tetrominoes) that gradually fall from the top of the screen. The goal is to form complete horizontal rows without gaps and earn as many points as possible.

The rules are quite simple: players need to move the falling blocks to the left or right and rotate them using the WASD keys or arrow keys on the keyboard. Players can also use the spacebar to drop the blocks instantly. Successfully arranging a horizontal row without gaps rewards the player with points and may increase their level. The higher the level, the faster the blocks fall.

## Game Display
- **Main Menu**
  ![Screenshot from 2024-12-12 14-46-58](https://github.com/user-attachments/assets/3c77b452-178e-4be1-9825-1a127b3a9eda)
- **In Game**
  ![Screenshot from 2024-12-12 14-47-34](https://github.com/user-attachments/assets/2cc0adba-c2a1-4f05-b0ef-155567ee55e5)
- **Game Paused**
  ![Screenshot from 2024-12-12 14-47-43](https://github.com/user-attachments/assets/822ab500-3c79-434b-a831-cc1190c9bbda)
- **Game Over**
  ![Screenshot from 2024-12-12 15-44-57](https://github.com/user-attachments/assets/4ba8c62a-d27b-404a-ac0d-489ab23478d6)
- **About**
  ![Screenshot from 2024-12-12 14-47-08](https://github.com/user-attachments/assets/b6c296ea-865e-4cd2-90be-b3a60656fa93)


## Game Asstes
https://moxica.itch.io/tetrominoes

https://prinbles.itch.io/bliss



