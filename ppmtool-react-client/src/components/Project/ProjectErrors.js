import React from 'react'

const ProjectErrors = (props) => {
    
    const errors = props.errors;
    const errorList = errors ? errors.map((err) => 
        <p key={err.errorId}>{err.errorName}</p>
    ) : '';

    return (
        <div className="invalid-feedback">{errorList}</div>
    )
}

export default ProjectErrors