import React, {Component} from 'react';
import './App.css';


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
    render() {
        const items = this.props.order.orderDetailsList.map(item =>
            <OrderItem key={item.isbn.isbn} item={item}/>
        );
        const total = this.props.order.orderDetailsList.map(item =>
            item.price * item.qty
        ).reduce((a, b) => a + b, 0);
        return (
            <div>
                <h1>Details for Order #{this.props.order.ono}</h1>
                <div className="row">
                    <div className="col-md-3">
                        Order Received
                    </div>
                    <div className="col-md-3">
                        {this.props.order.received}
                    </div>
                    <div className="col-md-3">
                        Order Shipped
                    </div>
                    <div className="col-md-3">
                        {this.props.order.shipped}
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3">
                        Shipping Address
                    </div>
                    <div className="col-md-3">
                        {this.props.order.shipAddress}
                    </div>
                    <div className="col-md-3">
                        Shipping City
                    </div>
                    <div className="col-md-3">
                        {this.props.order.shipCity}
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3">
                        Shipping State
                    </div>
                    <div className="col-md-3">
                        {this.props.order.shipState}
                    </div>
                    <div className="col-md-3">
                        Shipping Zip
                    </div>
                    <div className="col-md-3">
                        {this.props.order.shipZip}
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3">
                        Billing Address
                    </div>
                    <div className="col-md-3">
                        {this.props.order.userid.address}
                    </div>
                    <div className="col-md-3">
                        Billing City
                    </div>
                    <div className="col-md-3">
                        {this.props.order.userid.city}
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3">
                        Billing State
                    </div>
                    <div className="col-md-3">
                        {this.props.order.userid.state}
                    </div>
                    <div className="col-md-3">
                        Billing Zip
                    </div>
                    <div className="col-md-3">
                        {this.props.order.userid.zip}
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
                <a href="#" className="btn btn-primary" onClick={this.props.onBackButton}>Back to Order List</a>
            </div>
        );
    }
}
