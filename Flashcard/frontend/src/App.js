import React from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import QuestionForm from "./component/QuestionForm";
import QuestionList from "./component/QuestionList";
import FlashcardPage from "./component/FlashcardPage";

const App = () => {
  return (
    <Router>
      <div style={styles.container}>
        <nav style={styles.navbar}>
          <Link to="/" style={styles.link}>
            Add Question
          </Link>
          <Link to="/flashcards" style={styles.link}>
            Flashcards
          </Link>
        </nav>

        <Routes>
          <Route path="/" element={<QuestionForm />} />
          <Route path="/flashcards" element={<FlashcardPage />} />
        </Routes>
      </div>
    </Router>
  );
};

// 💡 Simple styles
const styles = {
  container: {
    fontFamily: "Arial, sans-serif",
    padding: "20px",
  },
  navbar: {
    display: "flex",
    justifyContent: "center",
    gap: "20px",
    marginBottom: "20px",
  },
  link: {
    textDecoration: "none",
    color: "#007bff",
    fontSize: "18px",
  },
};

export default App;
