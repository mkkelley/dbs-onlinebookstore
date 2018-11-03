import React, {Component} from 'react';
import Menu, {MenuItem} from 'rc-menu';
import './App.css';
import 'rc-menu/assets/index.css'
import LoginForm from "./Login";
import axios from 'axios';

let ainst = axios.create();

class Book extends Component {
    render() {
        return (
            <tr>
                <td>{this.props.book.isbn}</td>
                <td>{this.props.book.title}</td>
                <td>{this.props.book.author}</td>
                <td>{this.props.book.subject}</td>
                <td>{this.props.book.price}</td>
                <td><a className="btn btn-info" href='#'
                       onClick={() => this.props.onClick(this.props.book.isbn)}>
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
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {books}
                </tbody>

            </table>
        )
    }
}

class CartItem extends Component {
    render() {
        return (
            <tr>
                <td>{this.props.cart.title}</td>
                <td>{this.props.cart.quantity}</td>
                <td>{this.props.cart.price}</td>
                <td>{this.props.cart.price * this.props.cart.quantity}</td>
            </tr>
        )

    }
}

class SubjectPicker extends Component {
    constructor(props) {
        super(props);

        this.state = {subjects: []};
    }

    componentDidMount() {
        ainst.get("http://localhost:7070/api/book/subject")
            .then(res => this.setState({subjects: res.data}));
    }

    render() {
        const subjects = this.state.subjects.map(subject =>
            <div key={subject} className="col-md-3">
                <a className="btn-link btn"
                   onClick={() => this.props.onSubjectClick(subject)}>
                    {subject}
                </a>

            </div>
        );
        const rows = [...Array(Math.ceil(subjects.length / 4))];
        const subjectRows = rows.map((sbj, idx) => subjects.slice(idx * 4, idx * 4 + 4));
        const out = subjectRows.map((row, idx) => (
            <div className="row" key={idx}>
                {row}
            </div>
        ));
        return (
            <div>
                {out}
            </div>
        )
    }

}

class PaginationButton extends Component {
    render() {
        return (
            <a className="btn btn-outline-primary"
               onClick={() => this.props.onClick()}>
                {this.props.text}
            </a>
        )
    }
}

class BookBrowser extends Component {
    constructor(props) {
        super(props);

        this.state = {
            books: [],
            hasNext: false,
            hasPrev: false,
            currentPage: 0
        };
    }

    getBooks(page = 0) {
        let params =
            {
                ...{page: page},
                ...this.props.queryParams
            };
        ainst.get(this.props.url,
            {
                params: params
            })
            .then(response => {
                let data = response.data;
                this.setState({
                    books: data.content,
                    hasNext: !data.last,
                    hasPrev: !data.first,
                    currentPage: data.number
                });
            });
    }

    componentDidMount() {
        this.getBooks()
    }

    render() {
        return (
            <div>
                {this.state.hasPrev &&
                <PaginationButton text="Previous Page" onClick={() => this.getBooks(this.state.currentPage - 1)}/>}
                {this.state.hasNext &&
                <PaginationButton text="Next Page" onClick={() => this.getBooks(this.state.currentPage + 1)}/>}
                <BookList books={this.state.books} addToCart={this.props.cartChanged}/>
            </div>

        );
    }
}

class CartButton extends Component {
    constructor(props) {
        super(props);

        this.state = {
            count: 0
        }
    }

    componentDidMount() {
        ainst.get("http://localhost:7070/api/cart/count")
            .then(response =>
                this.setState(
                    {count: response.data}
                ));
    }

    render() {
        return (
            <div>Cart ({this.state.count})</div>
        )
    }
}

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            subjectFilter: false,
            subject: "",
            subjectPicker: false,
            isAuthenticated: false
        };
    }

    getMainContent = () => {
        if (!this.state.isAuthenticated) {
            return <LoginForm userAuthenticated={this.setAuthenticated}/>
        }
        if (this.state.subjectPicker) {
            return <SubjectPicker key="subjPicker" onSubjectClick={(subject) => this.setState({
                subjectFilter: true,
                subject: subject,
                subjectPicker: false
            })}/>
        }
        if (this.state.subjectFilter) {
            return <BookBrowser key={this.state.subject}
                                url={"http://localhost:7070/api/book/subject/" + this.state.subject}
                                cartChanged={this.handleCartAdd}/>
        } else {
            return <BookBrowser key="none" url="http://localhost:7070/api/book"
                                cartChanged={this.handleCartAdd}/>
        }
    };

    setAuthenticated = (username, password) => {
        ainst.defaults.headers.common['Authorization'] = "Basic " + btoa(username + ":" + password);
        this.setState({isAuthenticated: true});
    };

    handleCartAdd = (isbn) => {
        console.log(isbn)
    };

    handleMenuClick = (e) => {
        if (e.key === "By Subject") {
            this.setState({subjectPicker: true})
        } else if (e.key === "logout") {
            this.setState({isAuthenticated: false});
        }
    };

    render() {
        return (
            <div>
                {this.state.isAuthenticated && <Menu key="menuKey" onClick={(e) => this.handleMenuClick(e)}>
                    <MenuItem key="By Subject">By Subject</MenuItem>
                    <MenuItem key="cart"><CartButton/></MenuItem>
                    <MenuItem key="logout">Logout</MenuItem>
                </Menu>}
                {this.getMainContent()}
            </div>
        );
    }
}

export default App;
