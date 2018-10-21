import React, { Component, Fragment } from 'react';
import ReactDOM from 'react-dom';
import Navigation from './Navigation';

class CEFooter extends Component{
  constructor(props){
    super(props);
    this.state={

    }
  }

  render() {
    let footerStyle={
      height:"40px",
      width:"100%",
      marginTop:"-20px",
      marginBottom:"-30px",
      fontSize:"20px",
      color:"white",
      backgroundColor:"#435066",
      border: '0px solid blue',

    }
      return (
        <div>
        <div className="row">
        <div className="col-sm-12" style={footerStyle}>Copyrigt Custom Google Drive-2018. All Rights Reserved. Design & Developed by:Dharmendra.</div>
        </div>
        </div>

    );
  }
}

export default CEFooter;
