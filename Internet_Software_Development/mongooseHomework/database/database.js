import mongoose from "mongoose";
import { Book } from "./models/Book.js";

await mongoose.connect('mongodb+srv://temp:tempPassword@intsftwrdev.rg8ct0l.mongodb.net/mongoProj');

const excludes = {_id: false, __v: false};

export async function dbTest() {
    await Book.create({
        id: 100,
        title: 'Hunter`s Book',
        author: 'Hunter Treadway',
        publisher: 'Stephanie Treadway Inc.',
        isbn: '111-aaaa-bbbb-ccc',
        avail: true,
        who: 'Laura Treadway',
        due: new Date() 
    });
}

export async function books(avail) {
    let books;
    if(avail) {
        books = await Book.find({avail: avail}, excludes);
    }
    else {
        books = await Book.find();
    }

    return books;
}

export async function bookByID(id) {
    const book = await Book.find({id: id}, excludes);
    //console.log(book);
    return book;
}

export async function createBook(book) {
    if(!book.who)
        book.who = null;
    if(!book.due)
        book.due = null;
    try {
        await Book.create(book);
        return true;
    }
    catch(err) {
        return false;
    }
}

export async function updateBook(id, book) {
    const filter = {id: id};
    const update = {$set: book};
    
    const result = await Book.updateOne(filter, update);
    if(result.modifiedCount === 0) {
        return false;
    }
    return true;
}

export async function deleteBook(id) {
    
    const result = await Book.deleteOne({id: id});
    if(result.deletedCount === 0) {
        return false;
    }
    return true;
}