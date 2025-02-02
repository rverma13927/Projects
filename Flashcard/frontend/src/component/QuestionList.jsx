import React, { useEffect, useState } from "react";
import axios from "axios";

const QuestionList = () => {
  const [questions, setQuestions] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/questions/all")
      .then((response) => setQuestions(response.data))
      .catch((error) => console.error("Error fetching questions:", error));
  }, []);

  return (
    <div>
      <h2>All Questions</h2>
      {questions.map((q) => (
        <div
          key={q.id}
          style={{ borderBottom: "1px solid #ddd", padding: "10px" }}
        >
          <h3>{q.questionText}</h3>
          <div dangerouslySetInnerHTML={{ __html: q.answerHtml }} />
        </div>
      ))}
    </div>
  );
};

export default QuestionList;
