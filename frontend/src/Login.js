import React, {Component} from 'react';
import './App.css';
import 'rc-menu/assets/index.css'

export default class LoginForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: ""
        }
    }

    validateForm() {
        return this.state.username.length > 0 && this.state.password.length > 0
    }

    handleChange = (e) => {
        this.setState({[e.target.id]: e.target.value})
    };

    handleSubmit = (e) => {
        e.preventDefault();
        window.ainst.post('http://localhost:7070/api/login', {
                username: this.state.username,
                password: this.state.password
            },
            {
                auth: {
                    username: this.state.username,
                    password: this.state.password
                }
            })
            .then(response => {
                    if (response.data === true) {
                        this.props.userAuthenticated(this.state.username, this.state.password);
                    }
                }
            )
            .catch(error =>
                console.log(error)
            );
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <input onChange={this.handleChange}
                       value={this.state.username}
                       type="text" id="username" name="username"/>
                <input onChange={this.handleChange}
                       value={this.state.password}
                       type="password" id="password" name="password"/>
                <input type="submit"
                       value="Login"
                       disabled={!this.validateForm()}/>
            </form>
        )
    }
}
