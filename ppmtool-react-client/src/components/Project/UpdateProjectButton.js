import React from 'react'
import { Link } from 'react-router-dom';

const UpdateProjectButton = (props) => {

    const projectIdentifier = props.projectIdentifier;

    return (
        <React.Fragment>
            <Link to={`/updateProject/${projectIdentifier}`}>
                <li className="list-group-item update">
                    <i className="fa fa-edit pr-1">Update Project Info</i>
                </li>
            </Link>
        </React.Fragment>
    )
}

export default UpdateProjectButton;
