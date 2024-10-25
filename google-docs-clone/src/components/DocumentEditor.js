import React, { useState } from "react";
import useWebSocket from "../hooks/useWebSocket";

function DocumentEditor({ documentId }) {
  const { content, sendDelta } = useWebSocket(documentId);
  const [text, setText] = useState(content);

  const handleChange = (e) => {
    const newDelta = e.target.value.replace(text, "");
    setText(e.target.value);
    sendDelta(newDelta);
  };

  return (
    <textarea
      value={text}
      onChange={handleChange}
      rows="20"
      cols="60"
    ></textarea>
  );
}

export default DocumentEditor;
