import logo from './logo.svg';
import { useEffect, useState } from "react";
import './App.css';

function App() {
  const [advice, setAdvice] = useState("");
  const [count, setCount] = useState(0);
  async function getAdvice() {
    const res = await fetch("https://api.adviceslip.com/advice");
    const data = await res.json();
    console.log(data.slip.advice);
    setAdvice(data.slip.advice);
    setCount((count)=> count+1);
  }
  useEffect(function(){
    getAdvice();
  },[]);
  return (
    <div>
      <h1>{advice}</h1>
      <button onClick={getAdvice}>Get advice</button>
      <Message count={count}/>
    </div>
  );
}

function Message(props){
      return(
      <p>You have read <strong>{props.count}</strong> of adivce</p>
      );
}
export default App;
