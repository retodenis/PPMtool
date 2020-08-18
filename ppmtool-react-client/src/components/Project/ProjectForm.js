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

    const { name, uniqueLabel, description, startDate, endDate } = this.props.project;
    const mode = this.props.mode === 'update';

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
                      className={classnames("form-control form-control-lg ", { "is-invalid": errors.name })}
                      placeholder="Project Name"
                      name="name"
                      value={name}
                      onChange={this.onChange}
                    />
                    <ProjectErrors errors={errors.name} />
                  </div>
                  <div className="form-group">
                    <input
                      type="text"
                      className={classnames("form-control form-control-lg ", { "is-invalid": errors.uniqueLabel })}
                      placeholder="Unique Project ID"
                      name="uniqueLabel"
                      value={uniqueLabel}
                      onChange={this.onChange}
                      disabled={mode}
                    />
                    <ProjectErrors errors={errors.uniqueLabel} />
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