import { useEffect, useRef, useState } from "react";

function useWebSocket(documentId) {
  const [content, setContent] = useState("");
  const ws = useRef(null);

  useEffect(() => {
    ws.current = new WebSocket(
      `ws://localhost:8080/ws/documents/${documentId}`
    );
    ws.current.onmessage = (message) => {
      const { delta } = JSON.parse(message.data);
      setContent((prevContent) => prevContent + delta);
    };

    return () => ws.current.close();
  }, [documentId]);

  const sendDelta = (delta) => {
    ws.current.send(JSON.stringify({ delta }));
  };

  return { content, sendDelta };
}

export default useWebSocket;
