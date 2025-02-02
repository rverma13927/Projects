import React, { useState } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import axios from "axios";

const QuestionForm = () => {
  const [questionText, setQuestionText] = useState("");
  const [answerHtml, setAnswerHtml] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    const questionData = { questionText, answerHtml };

    try {
      await axios.post("http://localhost:8080/api/questions/add", questionData);
      alert("Question added successfully!");
    } catch (error) {
      console.error("Error saving question:", error);
    }
  };

  return (
    <div>
      <h2>Add Question</h2>
      <form style={styles.form} onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Enter question..."
          value={questionText}
          style={styles.input}
          onChange={(e) => setQuestionText(e.target.value)}
          required
        />
        <ReactQuill
          value={answerHtml}
          style={styles.quillEditor}
          onChange={setAnswerHtml}
        />
        <button type="submit" style={styles.button}>
          Save
        </button>
      </form>
    </div>
  );
};

// 💡 Styles for the form
const styles = {
  container: {
    maxWidth: "600px",
    margin: "auto",
    padding: "20px",
    backgroundColor: "#f9f9f9",
    borderRadius: "10px",
    boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.1)",
  },
  heading: {
    textAlign: "center",
    color: "#333",
  },
  form: {
    display: "flex",
    flexDirection: "column",
    gap: "10px",
  },
  input: {
    padding: "10px",
    fontSize: "16px",
    border: "1px solid #ddd",
    borderRadius: "5px",
    outline: "none",
  },
  quillEditor: {
    backgroundColor: "white",
    borderRadius: "5px",
    height: "500px",
  },
  button: {
    padding: "10px",
    fontSize: "16px",
    fontWeight: "bold",
    color: "white",
    backgroundColor: "#007bff",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
    marginTop: "100px",
  },
};

export default QuestionForm;
