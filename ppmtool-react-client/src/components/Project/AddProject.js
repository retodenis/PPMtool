import React, { Component } from 'react'
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createProject } from '../../actions/projectActions';
import ProjectForm from './ProjectForm';

class AddProject extends Component {
    constructor() {
      super();
  
      this.state = {
        projectName: "",
        projectIdentifier: "",
        description: "",
        startDate: "",
        endDate: "",
        errors: {}
      };
    }

    componentWillReceiveProps(nextProps) {
      if (nextProps.errors) {
        this.setState({ errors: nextProps.errors });
      }
    }
  
    onChange = e => {
      this.setState({ [e.target.name]: e.target.value });
    }
  
    onSubmit = e => {
      e.preventDefault();
      const newProject = {
        projectName: this.state.projectName,
        projectIdentifier: this.state.projectIdentifier,
        description: this.state.description,
        startDate: this.state.startDate,
        endDate: this.state.endDate
      };
      this.props.createProject(newProject, this.props.history);
    }
  
    render() {
      const { errors } = this.state;
  
      return (
        <ProjectForm 
          errors={errors}
          buttonLabel='Create Project form'
          project={this.state}
          onSubmit={this.onSubmit}
          onChange={this.onChange}/>
      );
    }
  }
  
  AddProject.propTypes = {
    createProject: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
  };
  
  const mapStateToProps = state => ({
    errors: state.errors
  });
  
  export default connect(
    mapStateToProps,
    { createProject }
  )(AddProject);