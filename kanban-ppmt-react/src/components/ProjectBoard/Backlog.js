import React, { Component } from "react";
import ProjectTask from "./ProjectTasks/ProjectTask";

class Backlog extends Component {
  render() {
    const { project_tasks_props } = this.props;

    const tasks = project_tasks_props.map((project_task) => (
      <ProjectTask key={project_task.id} project_task={project_task} />
    ));

    let toDo = [];
    let inProgress = [];
    let done = [];

    for (let i = 0; i < tasks.length; i++) {
      //console.log(tasks[i]);
      //console.log(tasks[i].props.project_task.status);
      /* Filtering Project Task on KanBan Board */
      if (tasks[i].props.project_task.status === "TO-DO") {
        toDo.push(tasks[i]);
      } else if (tasks[i].props.project_task.status === "IN_PROGRESS") {
        inProgress.push(tasks[i]);
      } else {
        done.push(tasks[i]);
      }
    }
    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {toDo}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {inProgress}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {done}
          </div>
        </div>
      </div>
    );
  }
}

export default Backlog;
