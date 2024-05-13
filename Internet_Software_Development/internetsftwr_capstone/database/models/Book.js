//const {Schema, model} = require('mongoose');
import {Schema, model} from 'mongoose';

const bookSchema = Schema({
    title: String,
    author: String,
    publisher: String,
    isbn: String,
    avail: Boolean,
    who: String,
    due: Date
});

export const Book = model('Book', bookSchema);

