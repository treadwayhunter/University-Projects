import { Book } from "./models/Book.js";

export async function initData() {
    await Book.create({
        id: 1,
        title: 'Reactions in REACT',
        author: 'Ben Dover',
        publisher: 'Random House',
        isbn: '978-3-16-148410-0',
        avail: true,
        who: null,
        due: null,
    });
    await Book.create({
        id: 2,
        title: 'Express-sions',
        author: 'Frieda Livery',
        publisher: 'Chaotic House',
        isbn: '978-3-16-148410-2',
        avail: true,
        who: null,
        due: null
    });
    await Book.create({
        id: 3,
        title: 'Restful REST',
        author: 'Al Gorithm',
        publisher: 'ACM',
        isbn: '978-3-16-143310-1',
        avail: true,
        who: null,
        due: null,
    });
    await Book.create({
        id: 4,
        title: 'See Essess',
        author: 'Anna Log',
        publisher: 'O`Reilly',
        isbn: '987-6-54-148220-1',
        avail: false,
        who: 'Homer',
        due: '1/1/23',
    });
    await Book.create({
        id: 5,
        title: 'Scripting in JS',
        author: 'Dee Gital',
        publisher: 'IEEE',
        isbn: '987-6-54-321123-1',
        avail: false,
        who: 'Marge',
        due: '1/2/23',
    });
    await Book.create({
        id: 6,
        title: 'Be An HTML Hero',
        author: 'Jen Neric',
        publisher: 'Coders-R-Us',
        isbn: '987-6-54-321123-2',
        avail: false,
        who: 'Lisa',
        due: '1/3/23',
    });
}