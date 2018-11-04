import React, {Component} from 'react';
import './App.css';
import Order from "./Order";

class OrderListItem extends Component {
    render() {
        return (
            <tr>
                <td><a href="#" onClick={() => this.props.onClick(this.props.order)}>Order #{this.props.order.ono}</a>
                </td>
                <td>{this.props.order.received}</td>
                <td>{this.props.order.shipped}</td>
            </tr>
        );
    }
}


class OrderList extends Component {
    render() {
        const orders = this.props.orders.map(order =>
            <OrderListItem key={order.ono} order={order} onClick={this.props.orderSelected}/>
        );
        return (
            <table className="table">
                <thead>
                <tr>
                    <th>Order Number</th>
                    <th>Received Date</th>
                    <th>Shipped Date</th>
                </tr>
                </thead>
                <tbody>
                {orders}
                </tbody>
            </table>
        );
    }
}

export default class OrderBrowser extends Component {
    constructor(props) {
        super(props);

        if (this.props.order !== null) {
            this.state = {
                orders: [],

                orderView: true,
                order: this.props.order
            }
        } else {
            this.state = {
                orders: [],

                orderView: false,
                order: null
            }
        }
    }

    orderSelected = (order) => {
        this.setState({orderView: true, order: order})
    };

    returnToOrderList = () => {
        this.setState({orderView: false, order: null})
    };

    componentDidMount() {
        window.ainst.get("/api/order/list")
            .then(response => {
                    this.setState({orders: response.data})
                }
            )
    }

    getContent = () => {
        if (this.state.orderView) {
            return <Order key={this.state.order.ono} order={this.state.order} onBackButton={this.returnToOrderList}/>
        }
        return <OrderList key="orderList" orderSelected={this.orderSelected} orders={this.state.orders}/>
    };

    render() {
        return this.getContent()
    }
}

