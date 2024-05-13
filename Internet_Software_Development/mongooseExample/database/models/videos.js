import { Schema } from "mongoose";
import { conn2 } from "../connections.js";

const videoSchema = Schema({
    name: String,
    date: Date,
});

export const Video = conn2.model('Video', videoSchema);