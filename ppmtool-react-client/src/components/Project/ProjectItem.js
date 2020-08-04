import React, { Component } from 'react'
import UpdateProjectButton from './UpdateProjectButton';

class ProjectItem extends Component {
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
                                <UpdateProjectButton projectIdentifier={projectIdentifier}/>
                                <a href="">
                                    <li className="list-group-item delete">
                                        <i className="fa fa-minus-circle pr-1">Delete Project</i>
                                    </li>
                                </a>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ProjectItem;