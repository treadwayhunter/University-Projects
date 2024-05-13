import express from 'express';
const app = express();
const PORT = 3000;

let bookMap = new Map();
bookMap.set(1, {
    title: 'Reactions in REACT',
    author: 'Ben Dover',
    publisher: 'Random House',
    isbn: '978-3-16-148410-0',
    avail: true,
    who: null,
    due: null,
});
bookMap.set(2, {
    title: 'Express-sions',
    author: 'Frieda Livery',
    publisher: 'Chaotic House',
    isbn: '978-3-16-148410-2',
    avail: true,
    who: null,
    due: null
});
bookMap.set(3, {
    title: 'Restful REST',
    author: 'Al Gorithm',
    publisher: 'ACM',
    isbn: '978-3-16-143310-1',
    avail: true,
    who: null,
    due: null,
});
bookMap.set(4, {
    title: 'See Essess',
    author: 'Anna Log',
    publisher: 'O`Reilly',
    isbn: '987-6-54-148220-1',
    avail: false,
    who: 'Homer',
    due: '1/1/23',
});
bookMap.set(5, {
    title: 'Scripting in JS',
    author: 'Dee Gital',
    publisher: 'IEEE',
    isbn: '987-6-54-321123-1',
    avail: false,
    who: 'Marge',
    due: '1/2/23',
});
bookMap.set(6, {
    title: 'Be An HTML Hero',
    author: 'Jen Neric',
    publisher: 'Coders-R-Us',
    isbn: '987-6-54-321123-2',
    avail: false,
    who: 'Lisa',
    due: '1/3/23',
});

const bookSelector = (avail) => {
    // iterate over map, then store in array
    let bookArr = [];
    bookMap.forEach((value, key) => {
        // put id inside the object
        value.id = key;

        // if not avail, and avail is not false
        // if avail is null or undefined, and not false
        if (!avail && avail !== false) {
            bookArr.push(value);
        } else if (value.avail === avail) {
            bookArr.push(value);
        }
    });

    return bookArr;
}

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

app.get('/books', (req, res) => {
    switch (req.query.avail) {
        case 'true': res.send(bookSelector(true)); break; // send available books
        case 'false': res.send(bookSelector(false)); break; // show unavailable books
        case undefined: res.send(bookSelector()); break; // show all books, avail was not set
        default: res.sendStatus(404); // avail was set, but the value is bad
    }
});

app.get('/books/:id', (req, res) => {
    let book = bookMap.get(Number(req.params.id));
    if (book) {
        book.id = Number(req.params.id);
        console.log(book);
    }
    else {
        console.log('not found');
    }
    res.sendStatus(200);
});

app.post('/books', (req, res) => {

    let book = req.body;

    if (book) {
        if (book.id && book.title && book.author && book.publisher && book.isbn) {
            if (bookMap.has(book.id)) return res.sendStatus(403); // book already exists
        }
        else return res.sendStatus(400)
        if (!book.who) book.who = null;
        if (!book.due) book.due = null;
    }
    else return res.sendStatus(400);

    bookMap.set(book.id, book); // set the book map with the id and book object
    res.sendStatus(201);
});

app.put('/books/:id', (req, res) => {
    const id = Number(req.params.id);
    const book = req.body;

    if (bookMap.has(id)) {
        bookMap.set(id, book);
        res.sendStatus(200);
    }
    else res.sendStatus(404); // not found

});

app.delete('/books/:id', (req, res) => {
    const id = Number(req.params.id);
    if (bookMap.delete(id)) res.sendStatus(200);
    else res.sendStatus(204);
});

app.listen(PORT, () => {
    console.log(`Express Server running on http://localhost:${PORT}`);
});