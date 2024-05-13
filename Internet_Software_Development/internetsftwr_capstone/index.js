import express from 'express';
const app = express();
import path from 'path';
const PORT = 8000;
import { fileURLToPath } from 'url';
import { books, checkInBook, checkOutBook } from './database/database.js';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename); // in ES modules, __dirname doesn't exist... so this is my fix

app.use(express.json());

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'frontend/react.html'));
});

app.get('/books', async (req, res) => {
    const bookList = await books(req.query.avail);
    res.send(bookList);
});

app.put('/checkOut', async (req, res) => {
    if(await checkOutBook(req.body.id, req.body.name))
        res.sendStatus(200);
    else
        res.sendStatus(404);
});

app.put('/checkIn', async (req, res) => {
    if(await checkInBook(req.body.id))
        res.sendStatus(200);
    else
        res.sendStatus(404);
});

app.get('*', (req, res) => {
    res.sendFile(path.join(__dirname, `frontend/${req.path}`));
});

app.listen(PORT, () => {
    console.log(`Server listening on http://localhost:${PORT}`);
});