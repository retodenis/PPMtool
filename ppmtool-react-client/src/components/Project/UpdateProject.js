import React, { Component } from 'react'
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getProject, createProject } from '../../actions/projectActions';
import ProjectForm from './ProjectForm';

class UpdateProject extends Component {
  constructor() {
    super()

    this.state = {
      id: "",
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
    
    const {
      id,
      projectName,
      projectIdentifier,
      description,
      startDate,
      endDate
    } = nextProps.project;

    this.setState({
      id,
      projectName,
      projectIdentifier,
      description,
      startDate,
      endDate
    });
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getProject(id, this.props.history);
  }

  onChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit = e => {
    e.preventDefault();

    const updateProject = {
      id: this.state.id,
      projectName: this.state.projectName,
      projectIdentifier: this.state.projectIdentifier,
      description: this.state.description,
      startDate: this.state.startDate,
      endDate: this.state.endDate
    };
    
    this.props.createProject(updateProject, this.props.history);
  }

  render() {
    const { errors } = this.state;

    return (
        <ProjectForm 
          errors={errors}
          buttonLabel='Update Project form'
          project={this.state}
          onSubmit={this.onSubmit}
          onChange={this.onChange}/>
    );
  }
}

UpdateProject.propTypes = {
  getProject: PropTypes.func.isRequired,
  project: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  project: state.project.project,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getProject, createProject }
)(UpdateProject);