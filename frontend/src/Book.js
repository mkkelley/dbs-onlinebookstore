import React, {Component} from 'react';
import './App.css';

class Book extends Component {
    constructor(props) {
        super(props);

        this.state = {
            pendingAdd: false
        }
    }

    addFinished = () => {
        this.setState({pendingAdd: false})
    };

    render() {
        let style;
        if (this.state.pendingAdd) {
            style = "btn btn-warning"
        } else {
            style = "btn btn-info"
        }
        return (
            <tr>
                <td>{this.props.book.isbn}</td>
                <td>{this.props.book.title}</td>
                <td>{this.props.book.author}</td>
                <td>{this.props.book.subject}</td>
                <td>${Number(this.props.book.price).toFixed(2)}</td>
                <td><a className={style} href='#'
                       onClick={() => {
                           if (this.state.pendingAdd) return;
                           this.setState({pendingAdd: true});
                           this.props.onClick(this.props.book.isbn, this.addFinished);
                       }}>
                    Add to Cart</a>
                </td>
            </tr>
        )
    }
}

class BookList extends Component {
    render() {
        const books = this.props.books.map(book =>
            <Book key={book.isbn} book={book} onClick={this.props.addToCart}/>
        );
        return (
            <table className="table">
                <thead>
                <tr>
                    <th>ISBN</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Subject</th>
                    <th>Price</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                {books}
                </tbody>

            </table>
        )
    }
}

class PaginationButton extends Component {
    render() {
        return (
            <a className="btn btn-outline-primary btn-block"
               onClick={() => this.props.onClick()}>
                {this.props.text}
            </a>
        )
    }
}

export default class BookBrowser extends Component {
    constructor(props) {
        super(props);

        this.state = {
            books: [],
            hasNext: false,
            hasPrev: false,
            currentPage: 0,
            lastPage: 0
        };
    }

    getBooks(page = 0) {
        let params =
            {
                ...{page: page},
                ...this.props.queryParams
            };
        window.ainst.get(this.props.url,
            {
                params: params
            })
            .then(response => {
                let data = response.data;
                console.log(data);
                this.setState({
                    books: data.content,
                    hasNext: !data.last,
                    hasPrev: !data.first,
                    currentPage: data.number,
                    lastPage: data.totalPages - 1
                });
            });
    }

    componentDidMount() {
        this.getBooks()
    }

    render() {
        return (
            <div>
                <div className="row">
                    <div className="col-md-2">
                        {this.state.hasPrev &&
                        <PaginationButton text="First" onClick={() => this.getBooks(0)}/>}
                    </div>
                    <div className="col-md-2">
                        {this.state.hasPrev &&
                        <PaginationButton text="Previous Page"
                                          onClick={() => this.getBooks(this.state.currentPage - 1)}/>}
                    </div>
                    <div className="col-md-4 text-center">
                        Page {this.state.currentPage + 1} of {this.state.lastPage + 1}
                    </div>
                    <div className="col-md-2">
                        {this.state.hasNext &&
                        <PaginationButton text="Next Page" onClick={() => this.getBooks(this.state.currentPage + 1)}/>}
                    </div>
                    <div className="col-md-2">
                        {this.state.hasNext &&
                        <PaginationButton text="Last" onClick={() => this.getBooks(this.state.lastPage)}/>}
                    </div>
                </div>
                <BookList books={this.state.books} addToCart={this.props.cartChanged}/>
            </div>

        );
    }
}

