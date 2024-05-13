import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Main.css';
/**
 * A button that, when clicked, redirects the user to the target page
 */
function MainButton({ target }) {
    const navigate = useNavigate();

    function onClick() {
        console.log(target.toLowerCase());
        navigate(`/${target.toLowerCase()}`);
    }

    return (
        <button className='MainButton' onClick={onClick}>{target}</button>
    );
}

export default function Main() {
    return (
        <div className='Main'>
            <div className='ButtonBox'>
                Hello CS4371!
                <MainButton target={'Register'} />
                <MainButton target={'Login'} />
                <MainButton target={'Chatroom'} />
            </div>
        </div>
    );
}

