//import logo from './logo.svg';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Main from './components/Main';
import Chatroom from './components/Chatroom';
import Register from './components/Register';

function Login() {
  return (
    <div>Login</div>
  );
}

function Error({error}) {
  return (
    <div>
      Error: {error}
    </div>
  );
}

function App() {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Main/>}/>
          <Route path="/register" element={<Register/>}/>
          <Route path="/login" element={<Login/>}/>
          <Route path="/chatroom" element={<Chatroom/>}/>
          <Route path="*" element={<Error error={404}/>}/>
        </Routes>
      </BrowserRouter>
    );
}

export default App;
