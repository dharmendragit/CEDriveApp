import React ,{Component} from 'react';
import ReactDOM from 'react-dom';
import {Route,Switch } from 'react-router';
import HomePage from './HomePage';

  const CERouter=()=>

    <main>
        <Switch>
        <Route exact path="/" component={HomePage}/>
        </Switch>
    </main>
 export default CERouter;
