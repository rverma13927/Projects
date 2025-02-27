import React, { useState, useEffect } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import axios from "axios";

const QuestionForm = () => {
  const [questionText, setQuestionText] = useState("");
  const [answerHtml, setAnswerHtml] = useState("");
  const [topics, setTopics] = useState([]);
  const [selectedTopicId, setSelectedTopicId] = useState("");

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

  const handleSubmit = async (e) => {
    e.preventDefault();

    const questionData = {
      questionText: questionText,
      answerHtml: answerHtml,
      topicId: selectedTopicId, // Sending the topic ID instead of name
    };
    setQuestionText("");
    setAnswerHtml("");
    setSelectedTopicId("");

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
        <select
          value={selectedTopicId}
          onChange={(e) => setSelectedTopicId(e.target.value)}
          style={styles.select}
          required
        >
          <option value="">Select Topic</option>
          {topics.map((topic) => (
            <option key={topic.id} value={topic.id}>
              {topic.name}
            </option>
          ))}
        </select>
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

// Styles
const styles = {
  form: { display: "flex", flexDirection: "column", gap: "10px" },
  input: { padding: "10px", fontSize: "16px", borderRadius: "5px" },
  quillEditor: { backgroundColor: "white", height: "300px" },
  select: { padding: "10px", fontSize: "16px", borderRadius: "5px" },
  button: {
    padding: "10px",
    fontSize: "16px",
    backgroundColor: "#007bff",
    color: "white",
    borderRadius: "5px",
    marginTop: "50px",
    width: "100px",
  },
};

export default QuestionForm;
