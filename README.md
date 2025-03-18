# Simple Socket Client-Server in Java

This repository contains a basic example of a socket-based client-server application in Java. The application demonstrates communication between a server and a client using TCP sockets. 

**Note:** This repository is for educational purposes only. It provides a simple implementation of socket programming in Java and is not intended for production use.

## Features

- **Server**:
  - Listens on a specific IP address and port for incoming client connections.
  - Sends a welcome message to the client.
  - Reads and displays messages sent by the client.
  
- **Client**:
  - Connects to the server using a specified IP address and port.
  - Sends a greeting message to the server.
  - Closes the connection gracefully after communication.

## New: Simple HTTP Server

- **HTTP Server**:
  - Listens on a specified IP and port.
  - Serves HTML and CSS files from a designated `www` directory.
  - Responds with a 404 error if the requested file is not found.
  - Provides a basic example of how to create a minimal HTTP server using Java sockets.
