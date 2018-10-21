import React, { Component } from 'react';
import ReactDOM from 'react-dom';
//import 'bootstrap/dist/css/bootstrap.min.css';
//import Style from './css/Style';
import Navigation from './Navigation';
import CERouter from './CERouter';
import HomePage from './HomePage';

import { BrowserRouter , Route,Link ,Switch} from "react-router-dom";
class MiddleArea extends Component{
  constructor(props) {
    super(props);
    this.state = {

    };


  }
  render(){


  return(
<div>
<CERouter />
</div>

    );
  }

}
export default MiddleArea;
