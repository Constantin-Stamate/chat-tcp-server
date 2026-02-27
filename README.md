# TCP Chat Server

This repository contains a Java-based TCP server application developed for laboratory exercises in
the **Network Programming Course** at **UTM (Technical University of Moldova)**. The project focuses on the
practical implementation of TCP-based client-server communication, including multi-threaded client
handling, message broadcasting, virtual IP assignment, and graceful server shutdown. The implementation
covers socket communication, concurrent connection management, shared resource synchronization, and server
lifecycle control, aiming to support hands-on learning and experimentation in network programming and
distributed systems.

## Core Functionality

- TCP Server Initialization: creating and configuring the server socket on a defined port.
- Client Connection Handling: accepting multiple client connections and assigning a dedicated thread for each session.
- Message Broadcasting: forwarding incoming messages to all connected clients in real time.
- Virtual IP Assignment: generating unique virtual IP addresses for connected users.
- User Management: maintaining a synchronized mapping between usernames and assigned virtual IPs.

## Concurrency and Synchronization

- Multi-threaded architecture for handling simultaneous client sessions.
- Use of synchronized collections for safe shared resource access.
- Explicit synchronization blocks to prevent race conditions.
- Safe removal of disconnected clients from active connections.

## Server Lifecycle Management

- Continuous listening for incoming client connections.
- Detection of client disconnection commands.
- Graceful shutdown mechanism via console command.
- Automatic cleanup of sockets, streams, and shared resources.

## Technical Architecture

- The server listens on port **65433** using Java `ServerSocket` and `Socket`.
- Input/Output communication is handled through `BufferedReader` and `PrintWriter`.
- A multi-threaded architecture is used, where each client is managed by a dedicated `ClientHandler` thread.
- A separate console thread allows graceful shutdown via command input.
- Active client output streams are stored in a synchronized `Set<PrintWriter>`.
- Username-to-virtual-IP mappings are stored in a synchronized `Map<String, String>`.
- A static `broadcast(String message)` method distributes messages to all connected clients.
- A `volatile` boolean flag (`isRunning`) ensures safe lifecycle control across threads.
- Proper exception handling is implemented for network and I/O failures.

## Installation

1. **Clone the repository**

```bash
  git clone https://github.com/Constantin-Stamate/chat-tcp-server
```

2. **Navigate to the project folder**

```bash
  cd chat-tcp-server
```

3. **Build the project using Maven**

```bash
   mvn clean install
```

4. **Run the server**

```bash
   java -cp target/classes org.tcpchat.server.Server
```

## Resources

For guidance and references on TCP networking and concurrent server development, you can check:

- [Transmission Control Protocol (TCP)](https://en.wikipedia.org/wiki/Transmission_Control_Protocol) — for understanding
  reliable client-server communication.
- [Java Socket Programming (Oracle Tutorial)](https://docs.oracle.com/javase/tutorial/networking/sockets/) — for
  implementing TCP communication in Java.
- [Multithreading in Java](https://docs.oracle.com/javase/tutorial/essential/concurrency/) — for understanding
  thread-based concurrency and synchronization.

## Technologies

- Programming Language: Java
- Editor/IDE: IntelliJ IDEA

## Author

This project was developed as part of the **Network Programming Course** at **UTM (Technical University of Moldova)**,
where TCP socket communication, multi-threaded server design, and client-server architecture were studied and
implemented.

- GitHub: [Constantin-Stamate](https://github.com/Constantin-Stamate)
- Email: constantinstamate.r@gmail.com