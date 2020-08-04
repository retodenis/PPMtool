import React, { Component } from 'react'
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getProject } from '../../actions/projectActions';
import ProjectForm from './ProjectForm';

class UpdateProject extends Component {

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getProject(id, this.props.history);
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
        buttonLabel='Update Project form'
        project={this.state}
        onSubmit={this.onSubmit}
        onChange={this.onChange} />
    );
  }
}

UpdateProject.propTypes = {
  getProject: PropTypes.func.isRequired,
  project: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  project: state.project.project
});

export default connect(
  mapStateToProps,
  { getProject }
)(UpdateProject);