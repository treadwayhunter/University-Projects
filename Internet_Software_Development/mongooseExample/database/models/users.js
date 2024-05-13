import { Schema } from "mongoose";
import { conn1 } from "../connections.js";

const userSchema = Schema({
    first: String,
    last: String,
});

export const User = conn1.model('User', userSchema);