const express = require('express');
const app = express();
const socketIo = require('socket.io');
const PORT = 8000;
const path = require('path');
const cors = require('cors');
const { testDB, getChat, setChat, registerUser } = require('./data/database.js');

app.use(express.json());

/**
 * Middleware captures requests for static files and sends them to the client
 */
app.use(express.static(path.join(__dirname, 'build')));

/**
 * Middleware for allowing cross origin requests
 * Mainly for development.
 * Dangerous in production.
 */
app.use(cors({
    origin: 'http://localhost:3000'
}));

/**
 * An API endpoint that returns the current state of the chat table from the DB.
 */
app.get('/chat', async (req, res) => {
    let arr = await getChat();
    console.log(arr);
    res.json(arr);
});


app.post('/register', (req, res) => {
    const data = req.body;
    console.log(data);
    registerUser(data.firstName, data.lastName, data.email, data.password);
    res.sendStatus(200);
});

/**
 * A catch all endpoint. It simply returns index.html.
 * React will handle 404 errors.
 */
app.get('*', (req, res) => {
    res.sendFile(path.join(__dirname, 'build', 'index.html'));
});

/**
 * Create a variable to listen for incomign requests on port number PORT
 */
const server = app.listen(PORT, () => {
    console.log('App is listening on port', PORT);
});

/**
 * Creates a websocket, and initializes the webserevr at the same time.
 */
const io = socketIo(server, {
    cors: {
        origin: "http://localhost:3000",
        methods: ['GET', 'POST'],
        allowedHeaders: ['my-custom-header'],
        credentials: true
    }
});

/**
 * Handles the websocket for the chat room.
 */
io.on('connection', (socket) => {
    console.log('A user connected');
    socket.on('disconnect', () => {
        console.log('A user disconnected');
    });

    socket.on('chat message', (msg) => {
        setChat(msg);
        io.emit('chat message', msg);
    });
});
