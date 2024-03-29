import React, {Component} from 'react';
import './App.css';
import {Link} from "react-router-dom";


class CartItem extends Component {
    constructor(props) {
        super(props);

        this.state = {
            qty: this.props.cart.qty
        }
    }

    handleQtyChange = (newQty) => {
        this.setState({qty: newQty});
        if (newQty !== "") {
            this.props.setQty(this.props.cart.isbn.isbn, newQty)
        }
    };

    render() {
        return (
            <tr>
                <td>{this.props.cart.isbn.title}</td>
                <td>
                    <input type="text"
                           value={this.state.qty}
                           onChange={(e) => this.handleQtyChange(e.target.value)}
                    />
                </td>
                <td>${Number(this.props.cart.isbn.price).toFixed(2)}</td>
                <td>${Number(this.props.cart.isbn.price * this.props.cart.qty).toFixed(2)}</td>
                <td>
                    <a href='#'
                       className="btn btn-danger"
                       onClick={() => this.props.removeFromCart(this.props.cart.isbn.isbn)}
                    >X</a>
                </td>
            </tr>
        )
    }
}

export default class Cart extends Component {
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

    changeBookQty = (isbn, newQty) => {
        window.ainst.put("/api/cart/" + isbn, {qty: newQty})
            .then(() => {
                this.updateCart();
            });
    };

    removeFromCart = (isbn) => {
        window.ainst.delete("/api/cart/" + isbn)
            .then(() => this.updateCart());
    };

    render() {
        const cartItems = this.state.cartItems.map(cart =>
            <CartItem key={cart.isbn.isbn}
                      cart={cart}
                      removeFromCart={this.removeFromCart}
                      setQty={this.changeBookQty}/>
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
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    {cartItems}
                    {cartItems.length !== 0 &&
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>${Number(this.state.total).toFixed(2)}</td>
                        <td>&nbsp;</td>
                    </tr>}
                    </tbody>

                </table>
                {cartItems.length !== 0 && <Link to="/order/new" className="btn btn-primary">Check Out</Link>}
            </>
        )
    }
}
