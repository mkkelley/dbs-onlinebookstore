import React, {Component} from 'react';
import './App.css';

const required_fields = [
    'userid',
    'password',
    'fname',
    'lname',
    'address',
    'city',
    'state',
    'zip'
];

class RegistrationInput extends Component {
    constructor(props) {
        super(props);

        this.state = {
            value: ""
        }
    }

    onChange = (e) => {
        this.setState({value: e.target.value});
        this.props.onChange(e);
    };

    render() {
        return (
            <>
                <div className="col-md-3 form-group">
                    <label htmlFor={this.props.name}>{this.props.label}</label>
                </div>
                <div className="col-md-3 form-group">
                    <input type="text"
                           className="form-control"
                           id={this.props.name}
                           name={this.props.name}
                           value={this.state.value}
                           onChange={this.onChange}/>
                </div>
            </>
        )
    }

}

export default class RegistrationForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userid: "",
            password: "",
            fname: "",
            lname: "",
            address: "",
            city: "",
            state: "",
            zip: "",
            phone: "",
            email: "",
            creditcardnumber: "",
            creditcardtype: ""
        }
    }

    onChange = (e) => {
        this.setState({[e.target.id]: e.target.value})
    };

    onSubmit = () => {
        window.ainst.post("/api/member/new", this.state)
            .then(response => this.props.onRegistration())
            .catch(response => console.log(response))
    };

    render() {
        return (
            <form name="f">
                <div className="row">
                    <RegistrationInput name="userid" label="Username: " onChange={this.onChange}/>
                    <RegistrationInput name="password" label="Password: " onChange={this.onChange}/>
                </div>
                <div className="row">
                    <RegistrationInput name="fname" label="First Name: " onChange={this.onChange}/>
                    <RegistrationInput name="lname" label="Last Name: " onChange={this.onChange}/>
                </div>
                <div className="row">
                    <RegistrationInput name="address" label="Address: " onChange={this.onChange}/>
                    <RegistrationInput name="city" label="City: " onChange={this.onChange}/>
                </div>
                <div className="row">
                    <RegistrationInput name="state" label="State: " onChange={this.onChange}/>
                    <RegistrationInput name="zip" label="Zip: " onChange={this.onChange}/>
                </div>
                <div className="row">
                    <RegistrationInput name="email" label="Email: " onChange={this.onChange}/>
                    <RegistrationInput name="phone" label="Phone Number: " onChange={this.onChange}/>
                </div>
                <div className="row">
                    <RegistrationInput name="creditcardnumber" label="Credit Card Number (optional): "
                                       onChange={this.onChange}/>
                    <RegistrationInput name="creditcardtype" label="Credit Card Type (optional): "
                                       onChange={this.onChange}/>
                </div>
                <div className="row">
                    <div className="col-md-3"><a href="#" onClick={this.props.onRegistration}
                                                 className="btn btn-block btn-warning">Cancel</a></div>
                    <div className="col-md-6">&nbsp;</div>
                    <div className="col-md-3"><a href="#" onClick={this.onSubmit}
                                                 className="btn btn-block btn-primary">Submit</a>
                    </div>
                </div>
            </form>
        )
    }
}
