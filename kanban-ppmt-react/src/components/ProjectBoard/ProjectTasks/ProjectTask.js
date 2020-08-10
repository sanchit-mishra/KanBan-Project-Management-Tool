import React, { Component } from "react";

class ProjectTask extends Component {
  render() {
    const { project_task } = this.props;
    let priorityStatus;
    let priorityClass;

    if (project_task.priority === 1) {
      priorityStatus = "HIGH";
      priorityClass = "bg-danger text-light";
    } else if (project_task.priority === 2) {
      priorityStatus = "MEDIUM";
      priorityClass = "bg-warning text-light";
    } else {
      priorityStatus = "LOW";
      priorityClass = "bg-info text-light";
    }

    return (
      <div className="card mb-1 bg-light">
        <div className={`card-header text-primary ${priorityClass}`}>
          ID: {project_task.projectSequence} -- Priority: {priorityStatus}
        </div>
        <div className="card-body bg-light">
          <h5 className="card-title">{project_task.summary}</h5>
          <p className="card-text text-truncate ">
            {project_task.acceptanceCriteria}
          </p>
          <a href="" className="btn btn-primary">
            View / Update
          </a>

          <button className="btn btn-danger ml-4">Delete</button>
        </div>
      </div>
    );
  }
}
export default ProjectTask;
