import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";
import CreateProjectButton from "./Project/CreateProjectButton";
import { connect } from "react-redux";
import { getProjects } from "../actions/projectActions";
import PropTypes from "prop-types";
import "../App.css";
import DashboardSvg from "../svg/ProjectDashboardSvg";

class Dashboard extends Component {
  componentDidMount() {
    this.props.getProjects();
  }

  render() {
    const { projects } = this.props.project;
    const ProjectDashboard = () => {
      /* Mapping Projects into individual Project */
      return (
        <div>
          {projects.map((project) => (
            <ProjectItem key={project.id} project={project} />
          ))}
        </div>
      );
    };

    const SVGDashboard = () => {
      /* If No Project is there */
      return (
        <div className="alert alert-secondary text-center">
          <text className="custom-font">
            Beat Procastination, Start Your Project Now!
          </text>
          <DashboardSvg className="svg-image text-center" />
        </div>
      );
    };

    const ProjectDashboardSVG = () => {
      if (projects.length) {
        return <ProjectDashboard />;
      } else {
        return <SVGDashboard />;
      }
    };

    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center custom-font">Projects</h1>
              <br />
              <CreateProjectButton />
              <br />
              <hr />
              <ProjectDashboardSVG />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  project: PropTypes.object.isRequired,
  getProjects: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => ({
  project: state.project,
});

export default connect(mapStateToProps, { getProjects })(Dashboard);
