import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logout } from "../../actions/userActions";

class Header extends Component {
  constructor() {
    super();
    this.state = {
      collapsed: true,
    };

    this.toggleNavbar = this.toggleNavbar.bind(this);
  }
  logout() {
    this.props.logout();
    window.location.href = "/";
  }

  toggleNavbar() {
    this.setState({
      collapsed: !this.state.collapsed,
    });
    //console.log(this.state.collapsed);
  }

  render() {
    const { validToken, user } = this.props.security;
    const collapsed = this.state.collapsed;
    /* Ternary Operator for navbar class*/
    const classOne = collapsed
      ? "collapse navbar-collapse"
      : "collapse navbar-collapse show";

    const classTwo = collapsed
      ? "navbar-toggler navbar-toggler-right collapsed"
      : "navbar-toggler navbar-toggler-right";

    const userIsAuthenticated = (
      <div className={`${classOne}`} id="mobileNav">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link
              className="nav-link"
              to="/dashboard"
              onClick={this.toggleNavbar}
            >
              Dashboard
            </Link>
          </li>
        </ul>

        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link
              className="nav-link "
              to="/dashboard"
              onClick={this.toggleNavbar}
            >
              <i className="fas fa-user-circle mr-1">&nbsp;{user.fullname}</i>
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/" onClick={this.logout.bind(this)}>
              Logout
            </Link>
          </li>
        </ul>
      </div>
    );

    const userIsNotAuthenticated = (
      <div className={`${classOne}`} id="mobileNav">
        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link
              className="nav-link "
              to="/register"
              onClick={this.toggleNavbar}
            >
              Sign Up
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/login" onClick={this.toggleNavbar}>
              Login
            </Link>
          </li>
        </ul>
      </div>
    );
    let headerLinks;

    if (validToken && user) {
      headerLinks = userIsAuthenticated;
    } else {
      headerLinks = userIsNotAuthenticated;
    }
    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-dark mb-4">
        <div className="container">
          <Link
            className="navbar-brand mr-auto"
            to=""
            onClick={this.toggleNavbar}
          >
            <small> Personal Project Management Tool </small>
          </Link>
          <button
            onClick={this.toggleNavbar}
            className={`${classTwo}`}
            type="button"
            data-toggle="collapse"
            data-target="#mobileNav"
            // aria-controls="mobileNav"
            // aria-expanded="false"
            // aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon" />
          </button>
          {headerLinks}
        </div>
      </nav>
    );
  }
}

Header.propTypes = {
  logout: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps, { logout })(Header);
