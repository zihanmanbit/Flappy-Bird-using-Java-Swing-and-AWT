# Flappy-Bird-using-Java-Swing-and-AWT🎮🐤

![Flappy Bird](https://github.com/zihanmanbit/Flappy-Bird-using-Java-Swing-and-AWT/blob/main/src/com/mycompany/flappybird/flappy-bird-logo.png)

A Java-based clone of the Flappy Bird game featuring smooth animations, immersive audio effects, high score tracking, dynamic difficulty and day/night mode. Built using **Java Swing** and **AWT**.
Completed as part of Object-Oriented Programming course (CSE 221).

## 🎥 Game Preview  
![Flappy Bird Preview](https://github.com/zihanmanbit/Flappy-Bird-using-Java-Swing-and-AWT/blob/main/src/com/mycompany/flappybird/game-preview.gif)

---

## ✨ Features  

### 👾 Core Gameplay  
- Smooth bird physics & gravity simulation
- Randomly generated pipes with dynamic spacing
- Real-time collision detection for pipes and ground
- Dynamic score and high score tracking
- Smooth 60 FPS game loop using `Timer`
- Game over state and quick restart

### 🌗 Modes & Difficulty
- 🌅 **Day Mode** and 🌃 **Night Mode** background themes
- 🎮 Switch between **Easy** (default) and **Hard** modes after game over  

### 🔊 Audio Effects  
- 🎶 Background music  
- 🎙️ Sound effects for:  
  - Jump (`jump.wav`)  
  - Scoring a point (`point.wav`)  
  - Hitting a pipe (`hit.wav`)  
  - Game over (`die.wav`)  
  - Swoosh effect for mode changes (`swooshing.wav`)
- Integrated using Java `javax.sound.sampled` API

### 🎨 Visuals
- Retro-style pixel graphics
- Clean, responsive UI rendered via Java 2D  

---

## 🎮 Controls  

| Key       | Action                           |
|-----------|----------------------------------|
| `SPACE`   | Bird Flap (jump)                 |
| `ENTER`   | Restart the game after Game Over |
| `D`       | Start in Day mode                |
| `N`       | Start in Night mode              |
| `E`       | Switch to Easy mode (post game)  |
| `H`       | Switch to Hard mode (post game)  |

---

## 🛠️ Getting Started  

### Prerequisites  
- Java Development Kit (JDK) 8 or higher  
- Any Java-supported IDE or terminal  

### Installation  

1. Clone the repository:  
   ```sh  
   git clone https://github.com/zihanmanbit/Flappy-Bird-using-Java-Swing-and-AWT.git  
   ```  
2. Navigate to the project directory:  
   ```sh  
   cd Flappy-Bird-using-Java-Swing-and-AWT  
   ```  
3. Compile the source code:  
   ```sh  
   javac -d bin src/com/mycompany/flappybird/*.java  
   ```  
4. Run the game:  
   ```sh  
   java -cp bin com.mycompany.flappybird.FlappyBirdGame  
   ```  

---

## 📁 Project Structure  

```
Flappy-Bird-using-Java-Swing-and-AWT/
│
├── src/com/mycompany/flappybird/
│   ├── FlappyBirdGame.java
│   ├── GamePanel.java
│   ├── flappy-bird-logo.png
│   ├── game-preview.gif
│   ├── flappybirdbg.png
│   ├── fbBG_night.png
│   ├── flappy_bird.gif
│   ├── toppipe.png
│   ├── bottompipe.png
│   ├── jump.wav
│   ├── point.wav
│   ├── hit.wav
│   ├── die.wav
│   ├── swooshing.wav
│   └── Tintin.wav
├── README.md
└── LICENSE
```
---

## 🧠 Game Logic Overview

### 🚀 **Physics System**  
- Gravity-based movement (`gravity = 1`)  
- Flap mechanics (`velocityY = -9` when jumping)  
- Pipes move leftward continuously  
- Score increases when passing pipes
- Collision detection ends the game  

### 🎲 **Game States**  
- **Menu/Intro:** Displays controls before game starts  
- **Playing state:** Pipes move, bird flies and score counts
- **Day/Night Mode switch**  
- **Game over screen:** Shows score and high score  
- **Restart functionality**
- **Difficulty switch**  

---

## 🧩 Future Enhancements  
  
- More background variations 
- Additional bird skins  
- Advanced achievements or medals
- Pause/Resume functionality  
- Adjustable volume controls  

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

## 👤 Author

**Zihan Manbit**

## 📞 Contact  

🔗 [GitHub Profile](https://github.com/zihanmanbit)
 
### Project Link: [https://github.com/zihanmanbit/Flappy-Bird-using-Java-Swing-and-AWT](https://github.com/zihanmanbit/Flappy-Bird-using-Java-Swing-and-AWT) 

--- 
