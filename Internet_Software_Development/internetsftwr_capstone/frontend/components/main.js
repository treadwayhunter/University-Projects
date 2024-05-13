const { useState, useEffect } = React;


// A book component for managing the state of individual books
function Book({ id, title, author, publisher, isbn, avail, who, due, refreshFunc}) {
    
    const [isCheckingOut, setIsCheckingOut] = useState(false); // a flag indicating whether the book is in the state of being checked out
    const [name, setName] = useState(''); // this state holds the name of someone checking out a book

    const bookClass = isCheckingOut ? 'book blur' : 'book'; // if book is being checked out, change the class name of the book

    // this section is just for cleaning up the date for readability
    let formattedDate;
    if(!avail) {
        let date = new Date(due);
        let day = date.getDate();
        let month = date.getMonth() + 1;
        let year = date.getFullYear();
        formattedDate = `${month}/${day}/${year}`;
    }


    function handleCheckIn() {
        fetch('http://localhost:8000/checkIn', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: id
            })
        })
        .then(res => {
            if(res.ok) {
                refreshFunc();
            }
        });
    }

    function handleOverlayToggle() {
        setName('');
        setIsCheckingOut(!isCheckingOut);
    }

    function handleCheckOut() {
        fetch('http://localhost:8000/checkOut', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: id,
                name: name
            })
        })
        .then(res => {
            if(res.ok) {
                refreshFunc();
            }
        });
    }

    function handleInput(event) {
        setName(event.target.value);
    }

    return (
        <div className={bookClass}>
            <h2>{title}</h2>
            <div className='spacer'></div>
            <h4>{author}</h4>
            <div className='bullet'></div>
            <h5>{publisher}</h5>
            <h5>isbn: {isbn}</h5>
            <div className='spacer'></div>
            {avail
                ? <>
                    <h5>Available</h5>
                    <button onClick={handleOverlayToggle}>Check Out</button>
                </>
                : <>
                    <h5>Not Available</h5>
                    <h5>Borrower: {who}</h5>
                    <h5>Due: {formattedDate}</h5>
                    <button onClick={handleCheckIn}>Check In</button>
                </>
            }
            {isCheckingOut
                ? 
                <div className='book-overlay'>
                    <h4>Checkout: {title}</h4>
                    <input type='text' placeholder="Name" onChange={handleInput}></input>
                    <div className="overlay-button-box">
                        <button onClick={handleCheckOut}>Check Out</button>
                        <button onClick={handleOverlayToggle}>Cancel</button>
                    </div>
                </div>
                :
                <></>
            }
        </div>
    );
}


// The main component that shows every book
function Main() {
    const [books, setBooks] = useState([]);
    const [selection, setSelection] = useState(null);
    console.log();
    function refreshList() {
        setBooks([]);
    }

    function getBookList(avail) {
        let url;

        if (avail === true || avail === false) {
            url = `http://localhost:8000/books?avail=${avail}`;
        }
        else {
            url = `http://localhost:8000/books`;
        }

        fetch(url)
            .then(res => {
                if (res.ok) {
                    return res.json();
                }
            })
            .then(data => {
                setBooks(data);
            })
    }

    function handleChange(event) {
        switch (event.target.value) {
            case 'Available': setSelection(true); break;
            case 'Unavailable': setSelection(false); break;
            default: setSelection(null);
        }
        setBooks([]);
    }

    useEffect(() => {
        if (books.length === 0) {
            getBookList(selection);
        }
    }, [books]);

    return (
        <div className="main">
            <h1>Hunter's Library</h1>
            <label htmlFor="books">Book Display: </label>
            <select onChange={handleChange}>
                <option value="All">All</option>
                <option value="Available">Available</option>
                <option value="Unavailable">Checked Out</option>
            </select>
            <ul>
                {books.map((item, index) => (
                    <li key={index}>
                        <Book
                            id={item._id}
                            title={item.title}
                            author={item.author}
                            publisher={item.publisher}
                            isbn={item.isbn}
                            avail={item.avail}
                            who={item.who}
                            due={item.due}
                            refreshFunc={refreshList}
                        />
                    </li>
                ))}
            </ul>
        </div>
    );
}