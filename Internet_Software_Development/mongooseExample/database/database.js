import { User } from "./models/users.js";
import { Video } from "./models/videos.js";

export function insertUser(first, last) {
   User.create({first: first, last: last});
}

export function insertVideo(name) {
    Video.create({name: name, date: new Date()});
}