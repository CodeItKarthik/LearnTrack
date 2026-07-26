## LearnTrack - Student & Course Management System

LearnTrack is a modular, console-based Student & Course Management System built using Core Java. Designed with a strict focus on foundational software engineering principles, the application provides an in-memory platform for managing students, courses, and academic enrollments.

This project completely avoids advanced frameworks, external databases, concurrency, or complex streams. Instead, it serves as a pure sandbox to master core object-oriented programming (OOP), structured package layouts, data encapsulation, dynamic collections, and clean command-line interface design.

## Core Feature Set

- **Student Management:** Provision new system identities, search student directories via structured tracking keys, and softly deactivate student profiles without compromising relational data records.
- **Course Control:** Manage active curriculum profiles, track multi-week timelines, and dynamically toggle visibility flags for active enrollment pools.
- **Enrollment Engine:** Bind unique Student and Course profiles with contextual state parameters (ACTIVE, COMPLETED, CANCELLED).
- **In-Memory Volatile Storage:** Uses Java ArrayList collections to manage data lifecycles cleanly within runtime application memory.
- **Failure-Resistant Console Input:** Intercepts out-of-bounds menu indices, string parsing errors, and structural missing-record faults without terminating the system process thread.
