#!/bin/bash

# Navigate to the directory containing the JAR file and run it
cd Desktop/Java/Projects/Flashcard/flashcard/target
java -jar flashcard-0.0.1-SNAPSHOT.jar &  # Run in the background (&)

# Navigate to the frontend directory

cd ../../frontend/
# Start the frontend development server
npm start
