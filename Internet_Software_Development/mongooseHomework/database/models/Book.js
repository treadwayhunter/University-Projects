import { Schema } from "mongoose";
import { model } from "mongoose";

const bookSchema = Schema({
    id: {
        type: Number,
        unique: true,
        required: true
    },
    title: String,
    author: String,
    publisher: String,
    isbn: String,
    avail: Boolean,
    who: String,
    due: Date // might need this to be a string
});

export const Book = model('Book', bookSchema);