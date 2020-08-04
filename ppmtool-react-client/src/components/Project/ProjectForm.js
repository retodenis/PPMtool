import React, { Component } from 'react'
import ProjectErrors from './ProjectErrors';
import classnames from 'classnames';

class ProjectForm extends Component {

  onSubmit = (e) => {
    this.props.onSubmit(e);
  }

  onChange = (e) => {
    this.props.onChange(e);
  }

  render() {
    const errors = this.props.errors;
    const buttonLabel = this.props.buttonLabel;

    const { projectName, projectIdentifier, description, startDate, endDate } = this.props.project;

    return (
      <div>
        <div className="project">
          <div className="container">
            <div className="row">
              <div className="col-md-8 m-auto">
                <h5 className="display-4 text-center">{buttonLabel}</h5>
                <hr />
                <form onSubmit={this.onSubmit}>
                  <div className="form-group">
                    <input
                      type="text"
                      className={classnames("form-control form-control-lg ", { "is-invalid": errors.projectName })}
                      placeholder="Project Name"
                      name="projectName"
                      value={projectName}
                      onChange={this.onChange}
                    />
                    <ProjectErrors errors={errors.projectName} />
                  </div>
                  <div className="form-group">
                    <input
                      type="text"
                      className={classnames("form-control form-control-lg ", { "is-invalid": errors.projectIdentifier })}
                      placeholder="Unique Project ID"
                      name="projectIdentifier"
                      value={projectIdentifier}
                      onChange={this.onChange}
                    />
                    <ProjectErrors errors={errors.projectIdentifier} />
                  </div>
                  <div className="form-group">
                    <textarea
                      className={classnames("form-control form-control-lg ", { "is-invalid": errors.description })}
                      placeholder="Project Description"
                      name="description"
                      value={description}
                      onChange={this.onChange}
                    />
                    <ProjectErrors errors={errors.description} />
                  </div>
                  <h6>Start Date</h6>
                  <div className="form-group">
                    <input
                      type="date"
                      className="form-control form-control-lg"
                      name="startDate"
                      value={startDate ? startDate : ""}
                      onChange={this.onChange}
                    />
                  </div>
                  <h6>Estimated End Date</h6>
                  <div className="form-group">
                    <input
                      type="date"
                      className="form-control form-control-lg"
                      name="endDate"
                      value={endDate ? endDate : ""}
                      onChange={this.onChange}
                    />
                  </div>

                  <input
                    type="submit"
                    className="btn btn-primary btn-block mt-4"
                  />
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default ProjectForm;