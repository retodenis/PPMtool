import React, { Component } from 'react'
import UpdateProjectButton from './UpdateProjectButton';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { deleteProject } from '../../actions/projectActions';

class ProjectItem extends Component {

    onDeleteClick = id => {
        if(window.confirm("Are you sure?")) {
            this.props.deleteProject(id);
        }
    }

    render() {

        const { projectIdentifier, projectName, description } = this.props.project;

        return (
            <div className="container">
                <div className="card card-body bg-light mb-3">
                    <div className="row">
                        <div className="col-2">
                            <span className="mx-auto">{projectIdentifier}</span>
                        </div>
                        <div className="col-lg-6 col-md-4 col-8">
                            <h3>{projectName}</h3>
                            <p>{description}</p>
                        </div>
                        <div className="col-md-4 d-none d-lg-block">
                            <ul className="list-group">
                                <a href="#">
                                    <li className="list-group-item board">
                                        <i className="fa fa-flag-checkered pr-1">Project Board </i>
                                    </li>
                                </a>
                                <UpdateProjectButton projectIdentifier={projectIdentifier} />
                                <li className="list-group-item delete" onClick={() => this.onDeleteClick(projectIdentifier)}>
                                    <i className="fa fa-minus-circle pr-1">Delete Project</i>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

ProjectItem.propTypes = {
    deleteProject: PropTypes.func.isRequired
}

export default connect(null, { deleteProject })(ProjectItem);