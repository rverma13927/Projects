import DocumentEditor from "./components/DocumentEditor";
import "./App.css";

function App() {
  return (
    <div className="App">
      <div>
        <h1>Google Docs Clone</h1>
        <DocumentEditor documentId="doc123" />
      </div>
    </div>
  );
}

export default App;
