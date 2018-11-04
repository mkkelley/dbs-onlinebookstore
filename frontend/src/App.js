import React, {Component} from 'react';
import Menu, {MenuItem, SubMenu} from 'rc-menu';
import './App.css';
import 'rc-menu/assets/index.css'
import LoginForm from "./Login";
import BookBrowser from "./Book";
import Cart from "./Cart";
import OrderBrowser from "./OrderBrowser";
import axios from 'axios';
import RegistrationForm from "./Registration";

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
                   href="#"
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

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isAuthenticated: false,

            registerView: false,

            subjectPicker: false,
            subjectFilter: false,
            subject: "",

            orderBrowser: false,
            order: null,

            titleSearch: false,
            title: "",

            authorSearch: false,
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
        if (this.state.registerView) {
            return <RegistrationForm onRegistration={this.onRegistration}/>
        }
        if (!this.state.isAuthenticated) {
            return <>
                <LoginForm userAuthenticated={this.setAuthenticated}/>
                <br/>
                <div className="text-center"><h2>OR</h2></div>
                <br/>
                <a href="#" onClick={() => this.handleRegisterButton()}
                   className="btn btn-success btn-block">Register</a>
            </>
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
                subjectPicker: false,
                titleSearch: false,
                authorSearch: false
            })}/>
        }
        if (this.state.authorSearch) {
            return <>
                <SearchView label="Author's Name (partial): " onChange={(author) => this.setState({author: author})}/>
                <BookBrowser key={"authorSearchBrowser_" + this.state.author}
                             url="/api/book/search"
                             queryParams={{author: this.state.author}}
                             cartChanged={this.handleCartAdd}/>
            </>;
        }
        if (this.state.titleSearch) {
            return <>
                <SearchView label="Title (partial): " onChange={(title) => this.setState({title: title})}/>
                <BookBrowser key={"titleSearchBrowser_" + this.state.title}
                             url="/api/book/search"
                             queryParams={{title: this.state.title}}
                             cartChanged={this.handleCartAdd}/>
            </>;
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

    handleRegisterButton = () => {
        this.setState({registerView: true})
    };

    onRegistration = () => {
        this.setState({registerView: false})
    };

    setAuthenticated = (username, password) => {
        ainst.defaults.headers.common['Authorization'] = "Basic " + btoa(username + ":" + password);
        this.setState({isAuthenticated: true});
        this.updateCount()
    };

    handleCartAdd = (isbn, callback) => {
        ainst.post("/api/cart/" + isbn)
            .then(() => {
                this.updateCount();
                callback()
            });
    };

    handleOneClickOrder = () => {
        ainst.get("/api/order/oneclick")
            .then(response => {
                console.log(response);
                this.setState({orderBrowser: true, order: response.data})
            })
    };

    clearFilters = () => {
        this.setState({
            subjectFilter: false,
            subject: "",
            titleSearch: false,
            titleFilter: false,
            title: "",
            authorSearch: false,
            authorFilter: false,
            author: ""
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
                order: null,
                titleSearch: false,
                authorSearch: false
            })
        } else if (e.key === "orders") {
            this.setState({orderBrowser: true})
        } else if (e.key === "oneclick") {
            this.handleOneClickOrder()
        } else if (e.key === "clear") {
            this.clearFilters()
        } else if (e.key === "by_author") {
            this.clearFilters();
            this.setState({authorSearch: true})
        } else if (e.key === "by_title") {
            this.clearFilters();
            this.setState({titleSearch: true})
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
                <Menu key="menuKey" onClick={(e) => this.handleMenuClick(e)}
                      mode="horizontal">
                    <SubMenu title="Browse Books">
                        <MenuItem key="By Subject">By Subject</MenuItem>
                        <MenuItem key="by_author">By Author</MenuItem>
                        <MenuItem key="by_title">By Title</MenuItem>
                    </SubMenu>
                    <MenuItem key="orders">Orders</MenuItem>
                    <MenuItem key="clear">Clear Filters</MenuItem>

                    <MenuItem key="logout" className="float-right">Logout</MenuItem>
                    <MenuItem key="cart" className="float-right"><CartButton
                        key="cartButton" count={this.state.count}/></MenuItem>
                    <MenuItem key="oneclick" className="float-right">One Click
                        Order</MenuItem>
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
