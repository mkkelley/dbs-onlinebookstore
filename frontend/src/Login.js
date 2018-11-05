import React, {Component} from 'react';
import './App.css';
import {Redirect} from "react-router-dom";

export default class LoginForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",
            authenticated: false
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
        window.ainst.post('/api/login', {
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
                        window.ainst.defaults.headers.common['Authorization'] = "Basic " +
                            btoa(this.state.username + ":" + this.state.password);
                        window.isAuthenticated = true;
                        this.setState({authenticated: true});
                    }
                }
            )
            .catch(error =>
                console.log(error)
            );
    };

    render() {
        if (this.state.authenticated) {
            return <Redirect to="/book"/>
        }
        return (
            <div>
                <div className="osb-filler">&nbsp;</div>
                <div className="row">
                    <div className="col-md-3"></div>
                    <div className="col-md-6">
                        <form onSubmit={this.handleSubmit}>
                            <div className="form-group row">
                                <label htmlFor="username" className="col-md-3">Username: </label>
                                <input className="form-control col-md-9"
                                       onChange={this.handleChange}
                                       value={this.state.username}
                                       type="text" id="username" name="username"/>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="password" className="col-md-3">Password: </label>
                                <input className="form-control col-md-9"
                                       onChange={this.handleChange}
                                       value={this.state.password}
                                       type="password" id="password" name="password"/>
                            </div>
                            <div className="text-center">
                                <input className="btn btn-primary btn-block"
                                       type="submit"
                                       value="Login"
                                       disabled={!this.validateForm()}/>
                            </div>
                        </form>
                    </div>
                    <div className="col-md-3"></div>
                </div>
            </div>
        )
    }
}
