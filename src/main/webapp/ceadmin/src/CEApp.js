import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import CEHeader from './components/CEHeader';
import CEFooter from './components/CEFooter';
import MiddleArea from './components/MiddleArea';


//import MiddleArea from './components/MiddleArea';

class CEApp extends Component {
  render() {
    return (
      <div>
        <CEHeader />
        <MiddleArea/>
        <CEFooter />
      </div>
    );
  }
}

export default CEApp;
