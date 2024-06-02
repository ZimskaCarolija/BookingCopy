Objective:
Develop a comprehensive hotel booking system that allows users to book rooms, manage hotels and amenities, and handle user verification. The system will include a RESTful API built with Spring Boot, a PostgreSQL database for data storage, and a frontend utilizing HTML, CSS, JavaScript, and jQuery for interacting with the API.

Functionality Details
User Roles:

Regular User: Can book hotels and rooms.
Manager: Can add hotels, room types, and rooms. Can view all bookings and details of the rooms.
Admin: Can add amenities (e.g., whether a room has WiFi or is full board) and locations (cities and states where hotels are situated).
Features:

Hotel Management: Managers can add hotels with multiple images, define room types with their prices, and specify individual rooms with unique images.
User Verification: Users, managers, and admins need to verify their accounts via email. A verification link is sent using a mail server built in Java and integrated into the project. The link includes a security code for CSRF protection and expires after a certain period.
Login and Authorization: Users must be verified to log in. Upon entering their email and password, a verification code is sent to their email. They must enter this code on the website to complete the login process. This code also has a limited validity period.
CSRF Protection: Security codes are embedded in URLs and checked against the database for secure operations.
Implementation Details
Backend (Spring Boot):

RESTful API: Endpoints for user registration, verification, login, hotel management, room booking, and amenity management.
Email Verification: A service to send verification emails with a security code. The service checks if the code is valid and marks the account as verified.
Session Management: Maintain user sessions for authenticated access.
CSRF Protection: Implement tokens in URLs and validate them against stored tokens in the database.
Database (MySQL):

Tables: Users, Hotels, Rooms, RoomTypes, Bookings, Amenities, Locations, VerificationTokens, CSRF Tokens.
Relationships: Define relationships between tables, such as hotels to rooms, rooms to room types, and users to bookings.
Frontend (HTML, CSS, JavaScript, jQuery):

User Interface: Pages for login, registration, verification, hotel management, room booking, and viewing bookings.
AJAX Calls: Use jQuery for making asynchronous calls to the RESTful API.
Form Validation: Client-side validation for user input before sending requests to the backend.
Example Workflow
User Registration and Verification:

The user registers by providing an email and password.
The server sends a verification email with a unique link containing a CSRF token.
The user clicks the link, which verifies their account by checking the token and marking the account as verified in the database.
Login Process:

The user enters their email and password.
The server sends a verification code to the user's email.
The user enters the verification code on the website to complete the login process.
Hotel Management by Manager:

The manager logs in and adds a new hotel with images.
The manager then adds room types for the hotel, specifying prices.
Finally, the manager adds individual rooms with specific images.
Admin Adding Amenities and Locations:

The admin logs in and adds amenities that rooms can have, such as WiFi or full board.
The admin also adds locations, specifying cities and states where the hotels are located.
