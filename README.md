Got it! Since your project is not a console-based application but rather an FXML-based JavaFX application, here's an updated version of your project description and instructions:

---

# LinkedIn Simulator

This repository contains the **Final Term Project** for the Advanced Programming course of the Computer Engineering Department at Amirkabir University of Technology (AUT). The project, titled **"LinkedIn Simulator"**, was developed under the supervision of Dr. Hossein Zeinali and Dr. Amir Kalbasi.

---

## Project Description
**LinkedIn Simulator** is a **GUI-based** application that emulates core functionalities of the LinkedIn platform. The project focuses on implementing advanced programming concepts, object-oriented design, and efficient data management using **JavaFX** and **FXML**.

---

## Features
- **User Management:** Create, update, and delete user profiles.
- **Connections:** Add and manage connections between users.
- **Messaging System:** Send and receive messages between users.
- **Search Functionality:** Search for users based on specific criteria.
- **Activity Feed:** View and manage posts and updates from connections.

---

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/yarahmadi0077/LinkedIn-Simulator.git
   cd LinkedIn-Simulator
   ```

2. **Set up the project with Maven**:
   - Ensure **OpenJDK 19** is installed.
   - Use the following Maven command to download dependencies:
     ```bash
     mvn clean install
     ```

3. **Run the JavaFX Application**:
   - Use Maven to run the JavaFX application:
     ```bash
     mvn javafx:run
     ```

   - Alternatively, you can compile and run the project manually:
     ```bash
     javac -d out src/com/yarahmadi/LinkedInSimulator/*.java
     java -cp out com.yarahmadi.LinkedInSimulator.Main
     ```

4. Follow the on-screen instructions to use the simulator.

---

## Requirements
- **Programming Language**: Java (with JavaFX for the front end)
- **Minimum OpenJDK Version**: 19
- **Build Tool**: Maven (for managing dependencies)
- **JavaFX**: For GUI development with FXML and Scene Builder

---

## Contribution
Contributions are welcome! If you'd like to improve the project or fix any issues, please fork the repository and open a pull request.

---

## Contact
- **Developer**: Mohammad Yarahmadi  
  GitHub: [yarahmadi0077](https://github.com/yarahmadi0077)

- **Professors:**  
  Dr. Hossein Zeinali  
  Email: [hzeinali@aut.ac.ir](mailto:hzeinali@aut.ac.ir)  
  Dr. Amir Kalbasi  
  Email: [kalbasi@aut.ac.ir](mailto:kalbasi@aut.ac.ir)

---

## License
This project is licensed under the MIT License. See the LICENSE file for details.

---

Let me know if you need any other modifications or additional details!
