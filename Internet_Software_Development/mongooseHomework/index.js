import express from 'express';
import { bookByID, books, createBook, deleteBook, updateBook } from './database/database.js';
const app = express();
const PORT = 3000;

app.use(function (req, res, next) {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods',
        'GET,PUT,POST,PATCH,DELETE,OPTIONS');
    res.header('Access-Control-Allow-Headers',
        'Content-Type, Authorization, Content-Length, X-Requested-With');
    if (req.method === "OPTIONS") res.sendStatus(200);
    else next();
});

app.use(express.json());

app.get('/books', async (req, res) => {
    const bookList = await books(req.query.avail);
    res.send(bookList);
});

app.get('/books/:id', async (req, res) => {
    const id = Number(req.params.id);
    const book = await bookByID(id);
    console.log(`BOOKS BY ID: ${book}`);
    if(Object.keys(book).length !== 0)
        res.sendStatus(200);
    else
        res.sendStatus(404);
    //res.send(book);
});


app.post('/books', async (req, res) => {
    let book = req.body;

    if(await createBook(book))
        res.sendStatus(201);
    else
        res.sendStatus(403);
});

app.put('/books/:id', async (req, res) => {
    const id = Number(req.params.id);
    const book = req.body;

    if(await updateBook(id, book))
        res.sendStatus(200);
    else
        res.sendStatus(404);
});

app.delete('/books/:id', async (req, res) => {
    const id = Number(req.params.id);
    
    if(await deleteBook(id))
        res.sendStatus(200)
    else
        res.sendStatus(204)
});

app.listen(PORT, () => {
    console.log(`Listening on http://localhost:${PORT}`);
});