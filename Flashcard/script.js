let questions = [];
let currentIndex = -1;

document
  .getElementById("fileInput")
  .addEventListener("change", handleFileSelection);
document.getElementById("showAnswerBtn").addEventListener("click", showAnswer);
document
  .getElementById("nextQuestionBtn")
  .addEventListener("click", showNextQuestion);

function handleFileSelection(event) {
  const files = event.target.files;
  questions = [];

  for (const file of files) {
    if (file.name.endsWith(".txt")) {
      const reader = new FileReader();
      reader.onload = () => parseFileContent(reader.result);
      reader.readAsText(file);
    }
  }
}

function parseFileContent(content) {
  const lines = content.split("\n");
  let questionObj = {};

  lines.forEach((line) => {
    if (line.startsWith("Q")) {
      if (questionObj.question) questions.push(questionObj); // Store previous question
      questionObj = {
        question: line.substring(line.indexOf(":") + 1).trim(),
        answer: "",
      };
    } else if (line.startsWith("Ans")) {
      questionObj.answer = line.substring(line.indexOf(":") + 1).trim();
    }
  });

  if (questionObj.question) questions.push(questionObj);

  if (questions.length) {
    document.getElementById("showAnswerBtn").disabled = false;
    document.getElementById("nextQuestionBtn").disabled = false;
  }
}

function showNextQuestion() {
  document.getElementById("answer").style.display = "none";

  if (questions.length === 0) {
    alert("No questions found!");
    return;
  }

  currentIndex = Math.floor(Math.random() * questions.length);
  const currentQuestion = questions[currentIndex];
  document.getElementById("question").textContent = currentQuestion.question;
}

function showAnswer() {
  if (currentIndex === -1) return;
  const currentQuestion = questions[currentIndex];
  document.getElementById("answer").innerHTML = currentQuestion.answer;
  document.getElementById("answer").style.display = "block";
}
