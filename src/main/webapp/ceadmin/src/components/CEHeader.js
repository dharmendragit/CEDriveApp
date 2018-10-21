import React, { Component, Fragment } from 'react';
import ReactDOM from 'react-dom';
//import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import Navigation from './Navigation';

class CEHeader extends Component{
  constructor(props){
    super(props);
    this.state={

    }
  }

  render() {
    let headerStyle={
      height:"100px",
      width:"100%",
      fontSize:"30px",
      color:"white",
      backgroundColor:"#435066",
      border: '0px solid blue',

    }
      return (
        <div>
      <div className="container" style={headerStyle}>Welcome to Custom Google Drive Application </div>
        </div>

    );
  }
}

export default CEHeader;
