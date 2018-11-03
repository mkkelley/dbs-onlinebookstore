import React, {Component} from 'react';
import Menu, {MenuItem} from 'rc-menu';
import './App.css';
import 'rc-menu/assets/index.css'
import LoginForm from "./Login";
import BookBrowser from "./Book";
import Cart from "./Cart";
import axios from 'axios';

window.ainst = axios.create({baseURL: 'http://localhost:7070/'});

let ainst = window.ainst;

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

class CartButton extends Component {
    constructor(props) {
        super(props);

        this.state = {
            count: 0
        }
    }

    updateCount = () => {
        ainst.get("/api/cart/count")
            .then(response =>
                this.setState(
                    {count: response.data}
                ));
    };

    componentDidMount() {
        this.updateCount()
    }

    componentDidUpdate() {
        this.updateCount();
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
            isAuthenticated: false,

            subjectPicker: false,
            subjectFilter: false,
            subject: "",

            cartView: false
        };
    }

    getMainContent = () => {
        if (!this.state.isAuthenticated) {
            return <LoginForm userAuthenticated={this.setAuthenticated}/>
        }
        if (this.state.cartView) {
            return <Cart key="cart"/>
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
                                url={"/api/book/subject/" + this.state.subject}
                                cartChanged={this.handleCartAdd}/>
        } else {
            return <BookBrowser key="none" url="/api/book"
                                cartChanged={this.handleCartAdd}/>
        }
    };

    setAuthenticated = (username, password) => {
        ainst.defaults.headers.common['Authorization'] = "Basic " + btoa(username + ":" + password);
        this.setState({isAuthenticated: true});
    };

    handleCartAdd = (isbn) => {
        console.log(isbn);
        ainst.post("/api/cart/" + isbn)
            .then(() => this.setState())
    };

    handleMenuClick = (e) => {
        if (e.key === "By Subject") {
            this.setState({subjectPicker: true})
        } else if (e.key === "logout") {
            this.setState({isAuthenticated: false});
        } else if (e.key === "cart") {
            this.setState({cartView: true});
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
