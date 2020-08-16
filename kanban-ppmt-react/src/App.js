import React, { Component } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";
import Landing from "./components/Layout/Landing";
import Register from "./components/Authentication/Register";
import Login from "./components/Authentication/Login";
import jwt_decode from "jwt-decode";
import { setJwtToken } from "./securityUtil/setJwtToken";
import { SET_USER } from "./actions/types";
import { logout } from "./actions/userActions";
import SecuredRoute from "./securityUtil/SecureRoute";

const jwt = localStorage.jwtToken;

if (jwt) {
  setJwtToken(jwt);
  const jwt_decoded = jwt_decode(jwt);
  store.dispatch({
    type: SET_USER,
    payload: jwt_decoded,
  });

  const currentTime = Date.now() / 1000;
  if (jwt_decoded.exp < currentTime) {
    store.dispatch(logout());
    window.location.href = "/";
  }
}

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />
            {
              //Public Route
            }
            <Route exact path="/" component={Landing} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/login" component={Login} />
            {
              //Private Route
            }
            <Switch>
              <SecuredRoute exact path="/dashboard" component={Dashboard} />
              <SecuredRoute exact path="/addProject" component={AddProject} />
              <SecuredRoute
                exact
                path="/updateProject/:id"
                component={UpdateProject}
              />
              <SecuredRoute
                exact
                path="/projectBoard/:id"
                component={ProjectBoard}
              />
              <SecuredRoute
                exact
                path="/addProjectTask/:id"
                component={AddProjectTask}
              />
              <SecuredRoute
                exact
                path="/updateProjectTask/:backlog_id/:pt_id"
                component={UpdateProjectTask}
              />
            </Switch>
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
