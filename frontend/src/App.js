import React, {Component} from 'react';
import './App.css';
import LoginForm from "./Login";
import BookBrowser from "./Book";
import Cart from "./Cart";
import OrderBrowser from "./OrderBrowser";
import axios from 'axios';
import RegistrationForm from "./Registration";
import Order from "./Order";
import {BrowserRouter as Router, Link, Redirect, Route} from "react-router-dom";

window.ainst = axios.create({baseURL: 'http://localhost:7070/'});
window.ainst.defaults.headers.common['Authorization'] = "Basic " + btoa("john_smith:js");
let ainst = window.ainst;

function SubjectBrowser({match}) {
    return <BookBrowser key={match.params.subject}
                        url={"/api/book/subject/" + match.params.subject}/>
}

function DefaultBrowser() {
    return <BookBrowser key="defaultBrowser"
                        url={"/api/book"}/>
}

function OrderWrapper({match}) {
    return <Order order={match.params.order}/>
}

class AuthorBrowser extends Component {
    constructor(props) {
        super(props);

        this.state = {
            author: ""
        }
    }

    render() {
        return <>
            <SearchView label="Author's Name (partial): " onChange={(author) => this.setState({author: author})}/>
            <BookBrowser key={"authorSearchBrowser_" + this.state.author}
                         url="/api/book/search"
                         queryParams={{author: this.state.author}}
                         cartChanged={this.handleCartAdd}/>
        </>;
    }
}

class TitleBrowser extends Component {
    constructor(props) {
        super(props);

        this.state = {
            title: ""
        }
    }

    render() {
        return <>
            <SearchView label="Title (partial): " onChange={(title) => this.setState({title: title})}/>
            <BookBrowser key={"titleSearchBrowser_" + this.state.title}
                         url="/api/book/search"
                         queryParams={{title: this.state.title}}
                         cartChanged={this.handleCartAdd}/>
        </>;
    }
}

function PrivateRoute({component: Component, ...rest}) {
    return (
        <Route
            {...rest}
            render={props =>
                window.isAuthenticated ? (
                    <Component {...props} />
                ) : (
                    <Redirect to={{pathname: "/login", state: {from: props.location}}}/>
                )
            }
        />

    )
}

class OneClickOrder extends Component {
    constructor(props) {
        super(props);

        this.state = {
            finished: false,
            order: null
        }
    }


    componentDidMount() {
        ainst.get("/api/order/oneclick")
            .then(response => {
                console.log(response);
                this.setState({finished: true, order: response.data.ono})
            })
    }

    render() {
        if (this.state.finished) {
            return <Redirect to={"/order/" + this.state.order}/>;
        } else {
            return null;
        }
    }
}

function LoginRegistrationForm() {
    return <>
        <LoginForm/>
        <br/>
        <div className="text-center"><h2>OR</h2></div>
        <br/>
        <Link to="/register" className="btn btn-success btn-block">Register</Link>
    </>
}

function logout() {
    window.isAuthenticated = false;
    window.ainst.defaults.headers.common['Authorization'] = null;
}

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            count: 0
        }
    }

    updateCount = () => {
        console.log("Hello");
        ainst.get("/api/cart/count")
            .then(response =>
                this.setState(
                    {count: response.data}
                ));
    };

    render() {
        return (
            <Router>
                <div>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                        <div className="collapse navbar-collapse">
                            <ul className="navbar-nav mr-auto">
                                <li className="nav-item dropdown">
                                    <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Browse Books
                                    </a>
                                    <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <Link className="dropdown-item" to="/book">All Books</Link>
                                        <Link className="dropdown-item" to="/book/subject">By Subject</Link>
                                        <Link className="dropdown-item" to="/book/search/author">By Author</Link>
                                        <Link className="dropdown-item" to="/book/search/title">By Title</Link>

                                    </div>
                                </li>
                                <li className="nav-item">
                                    <Link to="/order" className="nav-link">Orders</Link>
                                </li>
                            </ul>
                            <ul className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link to="/order/oneclick" className="nav-link">One Click Order</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/cart" className="nav-link">Cart ({this.state.count})</Link>
                                </li>
                                <li className="nav-item">
                                    <a href='#' className="nav-link" onClick={logout}>Logout</a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <div className="container">
                        <Route path="/" exact={false} onChange={this.updateCount}/>
                        <Route path="/login" component={LoginRegistrationForm}/>
                        <Route path="/register" component={RegistrationForm}/>
                        <PrivateRoute path="/book" exact={true} component={DefaultBrowser}/>
                        <PrivateRoute path="/book/subject" exact={true} component={SubjectPicker}/>
                        <PrivateRoute path="/book/subject/:subject" component={SubjectBrowser}/>
                        <PrivateRoute path="/book/search/author" component={AuthorBrowser}/>
                        <PrivateRoute path="/book/search/title" component={TitleBrowser}/>
                        <PrivateRoute path="/order" exact={true} component={OrderBrowser}/>
                        <PrivateRoute path="/order/:order" component={OrderWrapper}/>
                        <PrivateRoute path="/order/oneclick" component={OneClickOrder}/>
                        <PrivateRoute path="/cart" component={Cart}/>
                    </div>
                </div>
            </Router>
        );
    }
}

class SubjectPicker extends Component {
    constructor(props) {
        super(props);

        this.state = {subjects: []};
    }

    componentDidMount() {
        ainst.get("/api/book/subject")
            .then(res => this.setState({subjects: res.data}));
    }

    render() {
        const subjects = this.state.subjects.map(subject =>
            <div key={subject} className="col-md-3">
                <Link to={"/book/subject/" + subject} className="btn-link btn">{subject}</Link>
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

class SearchView extends Component {
    constructor(props) {
        super(props);

        this.state = {
            value: ""
        }
    }

    onChange = (e) => {
        this.setState({value: e.target.value});
        this.props.onChange(e.target.value)
    };

    render() {
        return (
            <>
                <br/>
                <div className="form-group row">
                    <div className="col-md-3">&nbsp;</div>
                    <input type="text"
                           className="form-control col-md-6"
                           name="search"
                           id="search"
                           placeholder={this.props.label}
                           onChange={this.onChange}
                    />
                </div>
            </>
        )
    }

}

export default App;
