
# Java Basra Game Engine 🃏

Hey everyone! Welcome to the Java Basra Game Engine project.

## What is This?

This is a simple, text-based game engine for the card game **Basra** (also known as Bastra or Basara in different regions), it is a common game in the Middle East and I have fond memories of playing it with family and friends, so I decided it would be a fun project to build entirely in Java.

## Why Basra?

Basra is a really popular and fun card game, especially common across the Middle East and parts of the Balkans. It's a game I grew up playing with family and friends, and it holds a special place for me culturally. It's simple enough to learn quickly but has enough little tricks to keep it interesting.

Building this engine was a personal project driven by that nostalgia and a desire to practice Java programming by creating something tangible and fun based on those fond memories.

## About the Game: Basra Rules (as implemented here)

If you haven't played before, here's a quick rundown of the basic rules this engine follows:

1.  **Players:** 2 players.
2.  **Deck:** Standard 52-card deck.
3.  **Goal:** Capture cards from a central "floor" by matching ranks or using special cards, which makes it a little similar to UNO in the sense that you capture cards that are the same rank as the one you played. The goal is to score points based on the cards you capture.
4.  **Setup:**
    *   Each player gets 4 cards.
    *   4 cards are dealt face-up to the center (the "floor").
    *   *Rule:* The initial floor cannot contain a Jack or the 7 of Diamonds. If it does, the floor cards are shuffled back into the deck, and 4 new floor cards are dealt.
5.  **Gameplay:**
    *   Players take turns playing **one** card from their hand.
    *   **Matching Rank:** If you play a card (e.g., a King) and there's one or more cards of the same rank (Kings) on the floor, you capture your played card *and* all matching cards from the floor.
    *   **Jack:** Playing a Jack captures *everything* currently on the floor. If the floor is empty, the Jack is just placed on the floor.
    *   **7 of Diamonds (Kommy/Special 7):** In this version, the 7 of Diamonds acts exactly like a Jack – it captures everything on the floor. (Note: Some variations have more complex rules for the 7D, but we kept it simple here).
    *   **No Match:** If your played card doesn't match any card on the floor by rank, and it's not a Jack or 7D, your card is simply added face-up to the floor.
    *   **Basra!:** This is a special capture! If you manage to capture *all* the cards on the floor with a single play (either by matching the last card(s) or using a Jack/7D on a non-empty floor), you score a "Basra". This gives bonus points!
6.  **Dealing:** When both players run out of cards in their hands, if the deck still has cards, they are each dealt another 4 cards. This continues until the deck is empty.
7.  **End Game:** Once the deck is empty and all hands have been played, the *last player who successfully captured any cards* gets to take any remaining cards left on the floor.
8.  **Scoring:** Points are awarded at the end for:
    *   Capturing the most cards overall (+3 points, no points awarded if tied).
    *   Each "Basra" scored (+10 points per Basra).
    *   Capturing specific cards:
        *   Each Jack: +1 point
        *   Each Ace: +1 point
        *   7 of Diamonds: +1 point
        *   2 of Clubs: +1 point
        *   10 of Diamonds: +1 point

## Project Goal

The aim was to create a clean, understandable, and functional engine for Basra in Java. The focus is on the core game logic, separating it from the user interface, making it potentially reusable or extendable in the future.

## Current Features

*   ✅ Core Basra gameplay logic implemented.
*   ✅ Standard 52-card deck handling (shuffle, deal).
*   ✅ Supports 2 players.
*   ✅ Correct handling of card captures (rank match, Jack, 7 of Diamonds).
*   ✅ Basra detection and scoring.
*   ✅ End-game logic (last capturer takes remaining floor cards).
*   ✅ Automatic point calculation based on captured cards and Basras.
*   ✅ Simple text-based User Interface (Console UI) for playing the game.

## How It Works: The Logic Inside

The project is structured to separate different responsibilities, making it easier to understand and manage:

1.  **`model` Package:** Contains the "things" or data structures of the game.
    *   `Card`, `Suit`, `Rank`: Define what a card is.
    *   `Deck`: Represents the deck, knows how to shuffle and deal.
    *   `Hand`: Represents the cards a player holds.
    *   `Player`: Represents a player, holding their `Hand`, captured cards (`capturePile`), Basra count, and score.
    *   `Table`: Represents the central floor cards.

2.  **`engine` Package:** Contains the main game orchestration logic.
    *   `Game`: This is the "boss" class. It manages the game flow, keeps track of whose turn it is, handles dealing, interacts with the UI, uses the `rules` package to process plays, and determines the winner.
    *   `PlayerAction`: A simple object to pass the player's chosen card from the UI to the engine.

3.  **`rules` Package:** Contains specific game rule implementations, keeping the main `Game` class cleaner.
    *   `CaptureLogic`: Figures out *what* gets captured when a card is played, and checks if a play results in a Basra.
    *   `ScoringLogic`: Calculates the final scores based on captured cards and Basras.

4.  **`ui` Package:** Handles how the game interacts with the user.
    *   `ConsoleUI`: The current implementation. It prints game information (like the floor and player hands) to the console and reads the player's input (which card they want to play).

5.  **`BasraGameMain`:** The entry point of the application (contains the `main` method). It simply creates the `Game` object and starts the game loop.

**In simple terms:** `BasraGameMain` starts the `Game`. The `Game` sets up the `Deck`, `Table`, and `Player`s (with their `Hand`s). It then loops through turns: asks the `ConsoleUI` what the current player wants to play, gets the `PlayerAction`, uses `CaptureLogic` to see what happens, updates the `Player`'s captures and the `Table`, and repeats until the game ends. Finally, it uses `ScoringLogic` to calculate scores and declares the winner via the `ConsoleUI`.

## File Structure

java_card_game_basra_engine/
├── build.xml # Ant build configuration
├── manifest.mf # Manifest file
├── nbproject/ # NetBeans project configuration
│   └── ... # NetBeans specific configuration files
├── build/ # Compiled classes and build output
│   └── ... # Build artifacts
├── src/
│   ├── engine/
│   │   ├── Game.java # Main game controller
│   │   └── PlayerAction.java # Represents a player's move
│   ├── model/
│   │   ├── Card.java
│   │   ├── Deck.java
│   │   ├── Hand.java
│   │   ├── Player.java
│   │   ├── Rank.java
│   │   ├── Suit.java
│   │   └── Table.java # Represents the floor
│   ├── rules/
│   │   ├── CaptureLogic.java # Handles capture rules
│   │   └── ScoringLogic.java # Handles scoring rules
│   └── ui/
│       └── ConsoleUI.java # Text-based user interface
├── README.md # Project documentation
└── LICENSE.md # License information



## Getting Started: How to Run

You'll need Java Development Kit (JDK) (e.g., version 11 or later) and Apache Maven installed on your system.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Eng-M-Abdrabbou/Java_Card_Game_Basra_Engine.git
    ```
2.  **Navigate to the project directory:**
    ```bash
    cd Java_Card_Game_Basra_Engine
    ```
3.  **Build the project using Maven:**
    This command compiles the code and packages it into a runnable JAR file.
    ```bash
    mvn clean package
    ```
4.  **Run the game:**
    After the build is successful, you'll find the JAR file in the `target` directory. Run it using:
    ```bash
    java -jar target/Java_Card_Game_Basra_Engine-1.0-SNAPSHOT.jar
    ```    
Follow the prompts in the console to play the game!

## Future Ideas / Potential Improvements

This is a basic implementation, and there's always room for more features!
*   [ ] Implement a Graphical User Interface (GUI) using Swing or JavaFX.
*   [ ] Add an optional AI player.
*   [ ] Implement network multiplayer.
*   [ ] Add support for different Basra rule variations (e.g., the more complex 7 of Diamonds rule, different point values).
*   [ ] More robust input validation in the UI.
*   [ ] Add unit tests for game logic.

## Contributing

Feel free to fork this repository, report issues, suggest improvements, or submit pull requests! Any contributions are welcome.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details (or simply state: Distributed under the MIT License).