import React from "react";
import { Link } from "react-router-dom";

const CreateProjectButton = () => {
  return (
    <React.Fragment>
      <Link to="/addProject" className="btn btn-lg btn-info">
        <i className="fas fa-plus-circle">
          <text className="btn-font">Create a Project</text>
        </i>
      </Link>
    </React.Fragment>
  );
};

export default CreateProjectButton;
