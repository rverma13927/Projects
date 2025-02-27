import React, { useState, useEffect } from "react";
import axios from "axios";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const FlashcardPage = () => {
  const [topics, setTopics] = useState([]);
  const [selectedTopic, setSelectedTopic] = useState(0);
  const [question, setQuestion] = useState(null);
  const [showAnswer, setShowAnswer] = useState(false);

  useEffect(() => {
    const fetchTopics = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/topics");
        setTopics(response.data);
      } catch (error) {
        console.error("Error fetching topics:", error);
      }
    };
    fetchTopics();
  }, []);

  const fetchRandomQuestion = async () => {
    if (!selectedTopic) {
      alert("Please select a topic first.");
      return;
    }

    try {
      const response = await axios.get(
        `http://localhost:8080/api/questions/random?topicId=${selectedTopic}`
      );
      setQuestion(response.data);
      setShowAnswer(false);
    } catch (error) {
      console.error("Error fetching question:", error);
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.heading}>Flashcard</h2>

      <select
        value={selectedTopic}
        onChange={(e) => setSelectedTopic(e.target.value)}
        style={styles.select}
      >
        <option value="">Select Topic</option>
        {topics.map((topic) => (
          <option key={topic.id} value={topic.id}>
            {topic.name}
          </option>
        ))}
      </select>

      <button onClick={fetchRandomQuestion} style={styles.nextButton}>
        Get Question
      </button>

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
        </div>
      ) : (
        <p>Select a topic and get a question.</p>
      )}
    </div>
  );
};

// Styles
const styles = {
  container: {
    maxWidth: "800px",
    margin: "auto",
    textAlign: "center",
    padding: "20px",
  },
  heading: { color: "#333" },
  select: { padding: "10px", fontSize: "16px", borderRadius: "5px" },
  card: {
    padding: "20px",
    backgroundColor: "white",
    borderRadius: "10px",
    boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)",
  },
  questionText: { fontSize: "18px", fontWeight: "bold" },
  answer: {
    marginTop: "10px",
    padding: "10px",
    backgroundColor: "#e8f5e9",
    borderRadius: "5px",
  },
  button: {
    marginTop: "10px",
    padding: "10px",
    fontSize: "16px",
    backgroundColor: "#007bff",
    color: "white",
    borderRadius: "5px",
  },
  nextButton: {
    marginTop: "10px",
    padding: "10px",
    fontSize: "16px",
    backgroundColor: "#28a745",
    color: "white",
    borderRadius: "5px",
  },
};

export default FlashcardPage;
