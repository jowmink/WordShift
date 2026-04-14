# Word Shift Game

## Overview

The **Word Shift Game** is a simple word ladder-style application where players transform a starting word into a target word by changing one letter at a time. Each intermediate step must be a valid word, and only one letter can be changed per move.

This project was developed as part of a course assignment, focusing on implementing the core game logic and validation system.

---

## How It Works

* The game provides:

  * A **starting word**
  * A **target word**
* The player must transform the starting word into the target word by:

  * Changing **one letter at a time**
  * Ensuring **each step is a valid English word**
* The game validates each guess and provides feedback if a move is invalid.

---

## Example

Start: `WIDE`
End: `SOBS`

Valid sequence:

```
WIDE → WIRE → WORE → SORE → SORB → SOBS
```

---

## Features

* Input validation for:

  * Word length consistency
  * Single-letter changes
  * Dictionary word checking
* Interactive guessing interface
* Error feedback for invalid guesses
* Simple and intuitive gameplay

---

## My Contribution

* Implemented the **core game logic**, including:

  * Word validation
  * Letter-difference checking
  * Game progression rules
* Integrated logic with the provided UI and structure

### Provided Code

* The **professor supplied**:

  * Project dependencies
  * Initial/skeletal code structure
* This project builds upon that foundation.

---

## Technologies Used

* Java / JavaFX (depending on your setup)
* Maven (for dependency management)

---

## Educational Use Notice

⚠️ **IMPORTANT**
This project was created for **educational purposes only** as part of a university course.

* Do **NOT** publish, distribute, or upload this code to public repositories (e.g., GitHub, GitLab, etc.).
* Sharing this work may violate academic integrity policies.

---

## How to Run

1. Ensure Java (JDK 21+ or your course version) is installed
2. Import the project into your IDE (e.g., IntelliJ)
3. Run the main application file

---

## Notes

* Some valid English words may not be accepted depending on the dictionary used in the program.
* If a guess is rejected, it may not exist in the internal word list.

---

## Author

Johanna Potestas
University of Calgary

If you want, I can tweak the tone (more formal / more casual) or match your course requirements exactly 👍
