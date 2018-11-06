import React, {Component} from 'react';
import './App.css';
import {Redirect} from "react-router-dom";

class OrderItem extends Component {
    render() {
        return (
            <tr>
                <td>{this.props.cart.isbn.title}</td>
                <td>{this.props.qty}</td>
                <td>${Number(this.props.cart.isbn.price).toFixed(2)}</td>
                <td>${Number(this.props.cart.isbn.price * this.props.cart.qty).toFixed(2)}</td>
            </tr>
        )
    }
}

class OrderSummary extends Component {
    constructor(props) {
        super(props);

        this.state = {
            cartItems: [],
            total: 0
        }
    }

    componentDidMount() {
        this.updateCart()
    }

    updateCart = () => {
        window.ainst.get("/api/cart/list")
            .then(response => {
                console.log(response.data);
                this.setState({cartItems: response.data})
            });
        window.ainst.get("/api/cart/total")
            .then(response =>
                this.setState({total: response.data}));
    };

    render() {
        const cartItems = this.state.cartItems.map(cart =>
            <OrderItem key={cart.isbn.isbn}
                       cart={cart}/>
        );
        return (
            <>
                <table className="table">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    {cartItems}
                    {cartItems.length !== 0 &&
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <th>${Number(this.state.total).toFixed(2)}</th>
                    </tr>}
                    </tbody>

                </table>
            </>
        )
    }
}

export default class OrderForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            shipAddress: "",
            shipCity: "",
            shipState: "",
            shipZip: "",
            newCc: false,
            newCcn: "",
            newCcType: "",
            successfulPost: false,
            successfulOno: "",
            hasErrors: true,
            errors: {
                shipAddress: "",
                shipCity: "",
                shipState: "",
                shipZip: "",
                newCcn: "",
                newCcType: ""
            }
        }
    }

    validate = () => {
        let errors = {};
        if (this.state.newCc) {
            if (this.state.newCcn === "") {
                errors.newCcn = "Please specify credit card number."
            }
            if (this.state.newCcType === "") {
                errors.newCcType = "Please specify credit card type"
            }
            if (this.state.newCcType !== "amex" &&
                this.state.newCcType !== "mc" &&
                this.state.newCcType !== "visa" &&
                this.state.newCcType !== "discover") {
                errors.newCcType = "Please specify a valid credit card type (amex, mc, visa, discover).";
            }
        }

        let zip = Number(this.state.shipZip).valueOf();
        if (zip < 10000 || zip > 99999) {
            errors.shipZip = "Please enter a valid (5-digit) zip code."
        }
        if (this.state.shipAddress === "") {
            errors.shipAddress = "Please enter an address."
        }

        if (this.state.shipCity === "") {
            errors.shipCity = "Please enter a city."
        }
        if (this.state.shipState === "") {
            errors.shipState = "Please enter a state."
        }

        if (Object.keys(errors).length !== 0) {
            this.setState({hasErrors: true, errors: errors});
            return false;
        } else {
            this.setState({hasErrors: false});
            return true;
        }

    };

    onSubmit = () => {
        let valid = this.validate();
        if (valid) {
            console.log(this.state);
            window.ainst.post("/api/order/new",
                {
                    shipAddress: this.state.shipAddress,
                    shipCity: this.state.shipCity,
                    shipState: this.state.shipState,
                    shipZip: this.state.shipZip,
                    newCc: this.state.newCc,
                    newCcn: this.state.newCcn,
                    newCcType: this.state.newCcType
                })
                .then((response) => {
                    this.setState(
                        {
                            successfulPost: true,
                            successfulOno: response.data.ono
                        }
                    )
                })
                .catch((response) => {
                    console.log(response);
                })
        }
    };

    onChange = (e) => {
        this.setState({[e.target.id]: e.target.value});
    };

    render() {
        if (this.state.successfulPost) {
            return (<Redirect to={"/order/" + this.state.successfulOno}/>)
        }
        return (
            <>
                <h1>Order Summary</h1>
                <OrderSummary/>
                <form name="f">
                    <div className="form-group row">
                        <div className="col-md-6">
                            <input type="text"
                                   className="form-control"
                                   placeholder="Shipping Address"
                                   name="shipAddress"
                                   id="shipAddress"
                                   onChange={this.onChange}/>
                            {this.state.hasErrors &&
                            <span className="text-danger">{this.state.errors.shipAddress}</span>
                            }
                        </div>
                        <div className="col-md-6">
                            <input type="text"
                                   className="form-control"
                                   placeholder="City"
                                   name="shipCity"
                                   id="shipCity"
                                   onChange={this.onChange}/>
                            {this.state.hasErrors &&
                            <span className="text-danger">{this.state.errors.shipCity}</span>}
                        </div>
                    </div>
                    <div className="form-group row">
                        <div className="col-md-6">
                            <input type="text"
                                   className="form-control"
                                   placeholder="State"
                                   name="shipState"
                                   id="shipState"
                                   onChange={this.onChange}/>
                            {this.state.hasErrors &&
                            <span className="text-danger">{this.state.errors.shipState}</span>}
                        </div>
                        <div className="col-md-6">
                            <input type="text"
                                   className="form-control"
                                   placeholder="Zip"
                                   name="shipZip"
                                   id="shipZip"
                                   onChange={this.onChange}/>
                            {this.state.hasErrors &&
                            <span className="text-danger">{this.state.errors.shipZip}</span>}
                        </div>
                    </div>
                    <div className="form-group row">
                        <div className="col-md-2">
                            <label>
                                <input type="checkbox"
                                       className="form-control"
                                       name="newCc"
                                       id="newCc"
                                       onChange={this.onChange}/>
                                <span className="cr">
                                New Credit Card
                                </span>
                            </label>
                        </div>
                        <div className="col-md-5">
                            <input type="text"
                                   className="form-control"
                                   placeholder="New Credit Card Number"
                                   name="newCcn"
                                   id="newCcn"
                                   onChange={this.onChange}/>
                            {this.state.hasErrors &&
                            <span className="text-danger">{this.state.errors.newCcn}</span>}
                        </div>
                        <div className="col-md-5">
                            <input type="text"
                                   className="form-control"
                                   placeholder="New Credit Card Type"
                                   name="newCcType"
                                   id="newCcType"
                                   onChange={this.onChange}/>
                            {this.state.hasErrors &&
                            <span className="text-danger">{this.state.errors.newCcType}</span>}
                        </div>
                    </div>
                    <div className="row form-group">
                        <div className="col-md-12">
                            <input type="button"
                                   className="btn btn-block btn-primary"
                                   onClick={this.onSubmit}
                                   value="Submit Order"/>
                        </div>
                    </div>
                </form>
            </>
        );
    }
}
