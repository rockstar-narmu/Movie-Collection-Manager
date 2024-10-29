# Movie Collection Manager [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A Java-based application for managing your personal movie collection. Users can add, edit, and track movies, view their collection, and filter by "yet to watch" or "watched" status. The app includes user authentication and is connected to a MySQL database.

## Features

- **User Registration & Login**: Users can create accounts and securely log in.
- **Movie Management**: Add, edit, and delete movies, including details like title, genre, release year, director, cast, rating, and watch status.
- **Database Integration**: All data is stored in a MySQL database.
- **Status Tracking**: Filter movies by "Yet to Watch" or "Watched."

## Setup

### Prerequisites

- Java JDK (version 8 or higher)
- MySQL server
- JDBC MySQL Connector (place the `.jar` file in your project library)

### Database Configuration

1. Create a database named `MovieDB`.
2. Run the following SQL commands to set up the `users` and `movies` tables:

   ```sql
   CREATE TABLE users (
       user_id INT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(50) UNIQUE NOT NULL,
       password VARCHAR(50) NOT NULL
   );

   CREATE TABLE movies (
       movie_id INT AUTO_INCREMENT PRIMARY KEY,
       user_id INT,
       title VARCHAR(100),
       genre VARCHAR(50),
       release_year INT,
       director VARCHAR(50),
       cast VARCHAR(100),
       rating FLOAT,
       status VARCHAR(20),
       FOREIGN KEY (user_id) REFERENCES users(user_id)
   );

3. Update the `DatabaseConnection` class with your MySQL username and password.

## Running the Project
1. Clone this Repository

   ```bash
   git clone https://github.com/rockstar-narmu/Movie-Collection-Manager.git

2. Open the project in your preferred Java IDE. (I used NetBeans IDE 8.0.2)
3. Run the `LoginForm` class to start the application.

## Screenshots
### User Registration
![Screenshot 2024-10-19 100904](https://github.com/user-attachments/assets/115432cb-dac5-4679-8cf2-419242c1a103)
![Screenshot 2024-10-19 100915](https://github.com/user-attachments/assets/aa406358-6fe0-44fc-8e65-55b8f197ead8)

### User Login
![Screenshot 2024-10-19 100931](https://github.com/user-attachments/assets/ac71ac9d-248f-4523-bcc7-4ad3d6169dec)
![Screenshot 2024-10-19 100939](https://github.com/user-attachments/assets/9d8b3e5b-8e07-4787-a305-dea5035652bf)

### Add a Movie
![Screenshot 2024-10-19 101321](https://github.com/user-attachments/assets/946fd808-77db-4204-8209-eaa911655b55)
![Screenshot 2024-10-19 101330](https://github.com/user-attachments/assets/cd401581-ad42-497f-a7a8-6756641e3b3d)

### View Movies
![Screenshot 2024-10-19 101414](https://github.com/user-attachments/assets/1c84d0a1-1593-4965-9693-9b225f752f16)

### Update or Edit Movie
![Screenshot 2024-10-19 101451](https://github.com/user-attachments/assets/2a5d45f4-f817-4126-ac85-765baa9e7f08)
![Screenshot 2024-10-19 101500](https://github.com/user-attachments/assets/100e8681-708e-4d70-b912-8f5f700c1394)

### Delete a Movie
![Screenshot 2024-10-19 101817](https://github.com/user-attachments/assets/ff57564a-82ce-4f9e-a88d-f1f166835c07)
![Screenshot 2024-10-19 101830](https://github.com/user-attachments/assets/d3311694-e0bf-4f39-90b0-93e39ac3b7a2)

## License
This project is licensed under the MIT License - see the `LICENSE` file for details.
