import React, {Component} from 'react';
import Menu, {MenuItem} from 'rc-menu';
import './App.css';
import 'rc-menu/assets/index.css'
import LoginForm from "./Login";
import BookBrowser from "./Book";
import Cart from "./Cart";
import OrderBrowser from "./OrderBrowser";
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
    render() {
        return (
            <div>Cart ({this.props.count})</div>
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

            orderBrowser: false,
            order: null,

            titleSearch: false,
            titleFilter: false,
            title: "",

            authorSearch: false,
            authorFilter: false,
            author: "",

            cartView: false,
            count: 0
        };
    }

    isInSecondaryView = () => {
        return this.state.cartView ||
            this.state.subjectPicker ||
            this.state.orderBrowser;
    };

    getMainContent = () => {
        if (!this.state.isAuthenticated) {
            return <LoginForm userAuthenticated={this.setAuthenticated}/>
        }
        if (this.state.cartView) {
            return <Cart key="cart"/>
        }
        if (this.state.orderBrowser) {
            return <OrderBrowser key="orderBrowser" order={this.state.order}/>
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
        this.updateCount()
    };

    handleCartAdd = (isbn) => {
        ainst.post("/api/cart/" + isbn)
            .then(() => this.updateCount())
    };

    handleOneClickOrder = () => {
        ainst.get("/api/order/oneclick")
            .then(response => {
                console.log(response);
                this.setState({orderBrowser: true, order: response.data})
            })
    };

    handleMenuClick = (e) => {
        this.updateCount();
        if (e.key === "By Subject") {
            this.setState({subjectPicker: true})
        } else if (e.key === "logout") {
            this.setState({isAuthenticated: false});
        } else if (e.key === "cart") {
            this.setState({cartView: true});
        } else if (e.key === "back") {
            this.setState({
                cartView: false,
                subjectPicker: false,
                orderBrowser: false,
                order: null
            })
        } else if (e.key === "orders") {
            this.setState({orderBrowser: true})
        } else if (e.key === "oneclick") {
            this.handleOneClickOrder()
        }
    };

    updateCount = () => {
        ainst.get("/api/cart/count")
            .then(response =>
                this.setState(
                    {count: response.data}
                ));
    };

    render() {
        return (
            <div>
                {this.state.isAuthenticated && !this.isInSecondaryView() &&
                <Menu key="menuKey" onClick={(e) => this.handleMenuClick(e)} mode="horizontal">
                    <MenuItem key="By Subject">By Subject</MenuItem>
                    <MenuItem key="orders">Orders</MenuItem>

                    <MenuItem key="logout" className="float-right">Logout</MenuItem>
                    <MenuItem key="cart" className="float-right"><CartButton key="cartButton" count={this.state.count}/></MenuItem>
                    <MenuItem key="oneclick" className="float-right">One Click Order</MenuItem>
                </Menu>}
                {this.isInSecondaryView() &&
                <Menu key="backMenu" onClick={this.handleMenuClick} mode="horizontal">
                    <MenuItem key="back">Back</MenuItem>
                </Menu>}
                <div className="container">
                    {this.getMainContent()}
                </div>
            </div>
        );
    }
}

export default App;
