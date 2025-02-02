import React, { useState, useEffect } from "react";
import axios from "axios";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const FlashcardPage = () => {
  const [question, setQuestion] = useState(null);
  const [showAnswer, setShowAnswer] = useState(false);

  // Function to fetch a random question from backend
  const fetchRandomQuestion = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/questions/random"
      );
      setQuestion(response.data);
      setShowAnswer(false); // Hide the answer when a new question is fetched
    } catch (error) {
      console.error("Error fetching question:", error);
    }
  };

  useEffect(() => {
    fetchRandomQuestion(); // Fetch question when the page loads
  }, []);

  return (
    <div style={styles.container}>
      <h2 style={styles.heading}>Flashcard</h2>
      {question ? (
        <div style={styles.card}>
          <h3 style={styles.questionText}>{question.questionText}</h3>
          {showAnswer && (
            <ReactQuill
              value={question.answerHtml}
              readOnly={true}
              theme="bubble"
              style={styles.answer}
            />
          )}
          <button
            onClick={() => setShowAnswer(!showAnswer)}
            style={styles.button}
          >
            {showAnswer ? "Hide Answer" : "Check Answer"}
          </button>
          <button onClick={fetchRandomQuestion} style={styles.nextButton}>
            Next Question
          </button>
        </div>
      ) : (
        <p>Loading question...</p>
      )}
    </div>
  );
};

// 💡 Styles for the flashcard page
const styles = {
  container: {
    maxWidth: "1000px",
    margin: "auto",
    padding: "20px",
    textAlign: "center",
    backgroundColor: "#f9f9f9",
    borderRadius: "10px",
    boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.1)",
  },
  heading: {
    color: "#333",
  },
  card: {
    padding: "20px",
    backgroundColor: "white",
    borderRadius: "10px",
    boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)",
  },
  questionText: {
    fontSize: "18px",
    fontWeight: "bold",
    marginBottom: "10px",
  },
  answer: {
    marginTop: "10px",
    padding: "10px",
    backgroundColor: "#e8f5e9",
    borderRadius: "5px",
  },
  button: {
    marginTop: "10px",
    padding: "10px 15px",
    fontSize: "16px",
    backgroundColor: "#007bff",
    color: "white",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
  },
  nextButton: {
    marginTop: "10px",
    marginLeft: "10px",
    padding: "10px 15px",
    fontSize: "16px",
    backgroundColor: "#28a745",
    color: "white",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
  },
};

export default FlashcardPage;
