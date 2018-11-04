import React, {Component} from 'react';
import './App.css';
import {Link} from "react-router-dom";


class OrderItem extends Component {
    render() {
        return (
            <tr>
                <td>{this.props.item.isbn.isbn}</td>
                <td>{this.props.item.isbn.title}</td>
                <td>${Number(this.props.item.price).toFixed(2)}</td>
                <td>{this.props.item.qty}</td>
                <td>${Number(this.props.item.price * this.props.item.qty).toFixed(2)}</td>
            </tr>
        )
    }
}

export default class Order extends Component {
    constructor(props) {
        super(props);

        this.state = {
            order: null
        }
    }

    componentDidMount() {
        window.ainst.get("/api/order/" + this.props.order)
            .then(response => {
                console.log(response);
                this.setState({order: response.data})
            })
    }

    render() {
        if (this.state.order === null) return null;
        const items = this.state.order.orderDetailsList.map(item =>
            <OrderItem key={item.isbn.isbn} item={item}/>
        );
        const total = this.state.order.orderDetailsList.map(item =>
            item.price * item.qty
        ).reduce((a, b) => a + b, 0);
        return (
            <div>
                <h1>Details for Order #{this.state.order.ono}</h1>
                <div className="row">
                    <div className="col-md-3">
                        Order Received
                    </div>
                    <div className="col-md-3">
                        {this.state.order.received}
                    </div>
                    <div className="col-md-3">
                        Order Shipped
                    </div>
                    <div className="col-md-3">
                        {this.state.order.shipped}
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3">
                        Shipping Address
                    </div>
                    <div className="col-md-3">
                        {this.state.order.shipAddress}
                    </div>
                    <div className="col-md-3">
                        Shipping City
                    </div>
                    <div className="col-md-3">
                        {this.state.order.shipCity}
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3">
                        Shipping State
                    </div>
                    <div className="col-md-3">
                        {this.state.order.shipState}
                    </div>
                    <div className="col-md-3">
                        Shipping Zip
                    </div>
                    <div className="col-md-3">
                        {this.state.order.shipZip}
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3">
                        Billing Address
                    </div>
                    <div className="col-md-3">
                        {this.state.order.userid.address}
                    </div>
                    <div className="col-md-3">
                        Billing City
                    </div>
                    <div className="col-md-3">
                        {this.state.order.userid.city}
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3">
                        Billing State
                    </div>
                    <div className="col-md-3">
                        {this.state.order.userid.state}
                    </div>
                    <div className="col-md-3">
                        Billing Zip
                    </div>
                    <div className="col-md-3">
                        {this.state.order.userid.zip}
                    </div>
                </div>
                <table className="table">
                    <thead>
                    <tr>
                        <th>ISBN</th>
                        <th>Title</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    {items}
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <th>${Number(total).toFixed(2)}</th>
                    </tr>
                    </tbody>
                </table>
                <Link to="/order" className="btn btn-primary">Back to Order List</Link>
            </div>
        );
    }
}
