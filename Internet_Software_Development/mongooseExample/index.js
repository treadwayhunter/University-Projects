import express from 'express';
import { insertUser, insertVideo } from './database/database.js';

const app = express();
const PORT = 8000;

app.get('/', (req, res) => {
    insertUser('Hunter', 'Treadway');
    insertVideo('Awesome.mp4');
    res.send('Hello World');
});

app.listen(PORT, () => {
    console.log(`App listening on localhost:${PORT}`);
});