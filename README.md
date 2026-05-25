# Number Guessing Game System

A fun and interactive **Number Guessing Game** built with **Spring Boot** and **Thymeleaf**. Players try to guess a randomly generated number between 1 and 100 within 10 attempts. The game tracks your score, rounds won, and provides instant feedback for every guess.

---

## Demo

Watch the game in action: [Demo Video](https://drive.google.com/file/d/16dSQ1jvfuzmuf5wgPeNkPd-cMHesB-fq/view?usp=sharing)

---

## Features

- **Random Number Generation:** A new number between 1–100 is generated for each round.
- **10 Attempts per Round:** Try to guess the number before you run out of attempts.
- **Score System:** Earn points based on how quickly you guess correctly — fewer attempts = higher score!
- **Round Tracking:** Keeps count of rounds won and current round number.
- **Visual Feedback:** Color-coded messages — green for correct, yellow for hints (too high / too low), red for game over.
- **Play Again & Restart:** Continue your streak with "Play Again" or reset everything with "Restart Game".
- **Responsive UI:** Clean, modern card-based design that works on desktop and mobile.
- **Session-Based State:** Game progress is stored in the HTTP session — no database required.
- **Client-Side Validation:** JavaScript prevents invalid guesses before submission.

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Spring Boot 4.0.6 |
| Template Engine | Thymeleaf |
| Frontend | HTML5, CSS3, JavaScript |
| Build Tool | Maven |
| Language | Java 26 |
| Testing | JUnit 5, Spring Boot Test, AssertJ |

---

## Project Structure

```
number-guessing-game-system/
├── pom.xml                                              # Maven build configuration
├── src/
│   ├── main/
│   │   ├── java/com/techtechnicworld/
│   │   │   └── number_guessing_game_system/
│   │   │       ├── NumberGuessingGameSystemApplication.java   # Spring Boot entry point
│   │   │       └── controller/
│   │   │           └── GameController.java                      # Game logic & endpoints
│   │   └── resources/
│   │       ├── application.properties                         # App config (port 8085)
│   │       ├── static/
│   │       │   └── style.css                                  # Stylesheet
│   │       └── templates/
│   │           └── index.html                                 # Thymeleaf game UI
│   └── test/
│       └── java/com/techtechnicworld/number_guessing_game_system/
│           └── NumberGuessingGameSystemApplicationTests.java  # Unit tests
└── README.md
```

---

## Game Rules

1. The computer picks a **random number between 1 and 100**.
2. You have **10 attempts** to guess it correctly.
3. After each guess, you receive feedback:
   - **"Too high!"** — your guess is greater than the target.
   - **"Too low!"** — your guess is less than the target.
   - **"Correct! You guessed the number."** — you win the round!
4. If you run out of attempts, the game reveals the number and ends the round.
5. **Scoring:** Points are awarded based on remaining attempts:  
   `Score += (10 - attempts_used) × 10`
6. Click **"Play Again"** to start a new round while keeping your total score.  
   Click **"Restart Game"** to reset everything (score, rounds, etc.).

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/` | Loads the game page and initializes a new session |
| `POST` | `/guess` | Submits a guess and returns feedback |
| `POST` | `/play-again` | Starts a new round, preserves score & stats |
| `POST` | `/restart` | Invalidates session and starts a completely fresh game |

---

## Getting Started

### Prerequisites

- Java 26+
- Maven 3.9+

### Run Locally

```bash
# 1. Clone the repository
git clone <repository-url>
cd number-guessing-game-system

# 2. Run with Maven Wrapper
./mvnw spring-boot:run

# Or on Windows
mvnw.cmd spring-boot:run
```

The application will start on **http://localhost:8085**

### Build & Run JAR

```bash
./mvnw clean package
java -jar target/number-guessing-game-system-0.0.1-SNAPSHOT.jar
```

### Run Tests

```bash
./mvnw test
```

---

## Screenshots / UI Highlights

- **Gradient purple header** with clean white card layout
- **Stats grid** showing Round, Attempts Left, Rounds Won, and Score
- **Color-coded feedback banners** with fade-in animation
- **Responsive design** — adapts beautifully to mobile screens

---

## Testing

The project includes unit tests covering:

- ✅ Home page initializes game with default values
- ✅ Invalid guess (out of range) returns validation feedback
- ✅ Correct guess ends round, updates score, and marks game over
- ✅ Play Again works even without an existing session

Tests use `MockHttpSession` and `ExtendedModelMap` to simulate HTTP requests without a running server.

---

## License

This project is for educational and demonstration purposes.

---

## Author

Built with by **TechtechnicWorld**
