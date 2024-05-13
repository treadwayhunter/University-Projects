import { useEffect, useState, useRef } from 'react';
import '../styles/Chatroom.css'
import io from 'socket.io-client';

const socket = io('http://192.168.10.130:8000');

export default function Chatroom() {
    const chatBoxRef = useRef(null);
    const [inputValue, setInputValue] = useState('');
    const [chat, setChat] = useState([]);

    useEffect(() => {
        if (chat.length === 0) {
            fetch('http://192.168.10.130:8000/chat')
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                setChat(data);
            });
        }
    }, []);

    useEffect(() => {
        chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
        socket.on('chat message', (msg) => {
            //setChat([msg, ...chat]);
            setChat([...chat, msg]);
        });

        return () => socket.off('chat message');
    }, [chat]);

    function handleEnter(event) {
        if (event.key === 'Enter') {
            if (inputValue) { 
                emitValue();
            }
        }
    }

    function handleChange(event) {
        setInputValue(event.target.value);
    }

    function emitValue() {
        // post value of input text to SQL server
        // new values go on top of the stack
        socket.emit('chat message', inputValue);
        setInputValue('');
    }

    return (
        <div className="Chatroom">
            <div className="ChatBox" ref={chatBoxRef}>
                {
                    chat.map((text, index) =>(
                        <div className="ChatMessage" key={index} dangerouslySetInnerHTML={{__html: text}}/>
                    ))
                }
            </div>
            <div className="InputBox">
                <input type="text" value={inputValue} onChange={handleChange} onKeyDown={handleEnter}/>
                <button onClick={emitValue()}>Send</button>
            </div>
        </div>
    );
}