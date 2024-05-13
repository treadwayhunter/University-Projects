const mysql = require('mysql2');
const bcrypt = require('bcrypt');
const saltRounds = 10;

const pool = mysql.createPool({
    host: 'localhost',
    user: 'node',
    password: 'securePassword',
    database: 'cs4371',
    multipleStatements: true // dangerous
});

const promisePool = pool.promise();

async function testDB() {
    const [rows, fields] = await promisePool.query('SELECT * FROM chat');
    console.log(rows);
}

async function getChat() {
    const [rows, fields] = await promisePool.query('SELECT * FROM chat');
    let messageArr = [];
    rows.forEach((row) => {
        messageArr.push(row.message);
    });
    return messageArr;
}

/**
 * This function sets up a security risk that enables easy SQL injection
 * inside the chatroom
 */
function setChat(chat) {
    promisePool.query(`INSERT INTO chat (message) values ("${chat}")`)
    .catch((error) => {
        console.log('Bad attempt with: ', chat);
    });
}

/**
 * 
 */
function registerUser(firstName, lastName, email, plainTextPassword) {
    bcrypt.hash(plainTextPassword, saltRounds)
    .then((hashedPassword) => {
        promisePool.query(`INSERT INTO users (first_name, last_name, email, password) VALUES ("${firstName}", "${lastName}", "${email}", "${hashedPassword}");`)
        .catch(error => console.error(error));
    });
}

module.exports = { testDB, getChat, setChat, registerUser }; 