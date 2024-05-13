// call this to initialize the database

import { Book } from "./models/Book.js";
import mongoose from "mongoose";


async function init() {
    await mongoose.connect('mongodb+srv://temp:tempPassword@intsftwrdev.rg8ct0l.mongodb.net/capstoneProj');

    Book.create({
        title: "The Gift",
        author: "Alison Croggon",
        publisher: 'Walker Books',
        isbn: '140636988',
        avail: true,
        who: null,
        due: null
    });

    Book.create({
        title: 'The Fountainhead',
        author: 'Jane Austen',
        publisher: 'NAL',
        isbn: '0452286751',
        avail: true,
        who: null,
        due: null
    });

    Book.create({
        title: 'Dune',
        author: 'Frank Herbert',
        publisher: 'Penguin Classics',
        isbn: '9780143111580',
        avail: false,
        who: 'Hunter Treadway',
        due: new Date('December 24, 2023 11:59:59')
    });

    Book.create({
        title: 'To Kill a Mockingbird',
        author: 'Harper Lee',
        publisher: 'Harper',
        isbn: '0062420704',
        avail: false,
        who: 'Steph Treadway',
        due: new Date('December 22, 2023 11:59:59')
    });

    Book.create({
        title: 'The Great Gatsby',
        author: 'F. Scott Fitzgerald',
        publisher: 'Fingerprint! Publishing',
        isbn: '9390183529',
        avail: true,
        who: null,
        due: null
    });

    Book.create({
        title: 'Brave New World',
        author: 'Aldous Huxley',
        publisher: 'Harper',
        isbn: '0062696122',
        avail: false,
        who: 'Hunter Treadway',
        due: new Date('December 21, 2023 11:59:59')
    });

    Book.create({
        title: 'Pride and Prejudice',
        author: 'Jane Austen',
        publisher: 'Penguin Classics',
        isbn: '9780141040349',
        avail: true,
        who: null,
        due: null
    });

    Book.create({
        title: 'Moby Dick',
        author: 'Herman Melville',
        publisher: 'Fingerprint! Publishing',
        isbn: '9354407341',
        avail: true,
        who: null,
        due: null
    });

    Book.create({
        title: 'Rifles for Watie',
        author: 'Harold Keith',
        publisher: 'Harper Teen',
        isbn: '9780064470308',
        avail: true,
        who: null,
        due: null
    });

    Book.create({
        title: 'The Lion, the Witch, and the Wardrobe',
        author: 'C.S. Lewis',
        publisher: 'Zondervan',
        isbn: '0061715050',
        avail: true,
        who: null,
        due: null
    });
}

init();