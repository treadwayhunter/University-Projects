import { Book } from "./models/Book.js";
import mongoose from "mongoose";

mongoose.connect('mongodb+srv://temp:tempPassword@intsftwrdev.rg8ct0l.mongodb.net/capstoneProj');

const excludes = {__v: false};

export async function books(avail) {
    let books;
    
    if(avail || avail === false) {
        books = await Book.find({avail: avail}, excludes);
    }
    else {
        books = await Book.find({}, excludes);
    }

    return books;
}

export async function checkOutBook(id, name) {
    let currentDate = new Date();
    let oneMonthLater = new Date(currentDate);
    oneMonthLater.setMonth(currentDate.getMonth() + 1);

    const result = await Book.updateOne({_id: id}, {$set: {avail: false, who: name, due: oneMonthLater}});
    if(result.modifiedCount !== 1) {
        return false;
    }
    return true;
}

export async function checkInBook(id) {
    const result = await Book.updateOne({_id: id}, {$set: {avail: true, who: null, due: null}});
    if(result.modifiedCount === 0) {
        return false;
    }
    return true;
}