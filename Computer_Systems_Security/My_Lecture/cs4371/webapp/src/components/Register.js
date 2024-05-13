import { useState } from 'react';
import '../styles/Register.css';

function RegisterInput({label, type, valueFunc, keydown}) {
    return (
        <div className='RegisterInput'>
            <label>{label}</label>
            <input type={type} onChange={valueFunc} onKeyDown={keydown}/>
        </div>
    );
}

export default function Register() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirm, setConfirm] = useState('');



    function getFirstName(event) {
        setFirstName(event.target.value);
    }

    function getLastName(event) {
        setLastName(event.target.value);
    }

    function getEmail(event) {
        setEmail(event.target.value);
    }

    function getPassword(event) {
        setPassword(event.target.value);
    }

    function getConfirm(event) {
        setConfirm(event.target.value);
    }

    function handleEnter(event) {
        if (event.key === 'Enter' && inputsNotNull()) {

            if(password === confirm) {
                postData();
            }
        }
    }

    function handleClick() {
        if(inputsNotNull()) {
            if (password === confirm) {
                postData();
            }
        }
    }

    function inputsNotNull() {
        if (firstName && lastName && email && password && confirm) {
            return true;
        }
        console.log('An input is null');
        return false;
    }

    function postData() {
        fetch('http://192.168.10.130:8000/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password
            })
        });
    }

    return (
        <div className='RegisterPage'>
            <div className='Register'>
                <h2>Register!</h2>
                <RegisterInput label='First Name' type='text' valueFunc={getFirstName} keydown={handleEnter}/>
                <RegisterInput label='Last Name' type='text' valueFunc={getLastName} keydown={handleEnter}/>
                <RegisterInput label='Email' type='email' valueFunc={getEmail} keydown={handleEnter}/>
                <RegisterInput label='Password' type='password' valueFunc={getPassword} keydown={handleEnter}/>
                <RegisterInput label='Confirm Password' type='password' valueFunc={getConfirm} keydown={handleEnter}/>
                <button onClick={handleClick}>Send</button>
            </div>
        </div>
    );
}