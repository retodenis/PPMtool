import axios from 'axios';
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from './types';

const PROJECTS_URL = '/api/project';

export const createProject = (project, history) => async dispatch => {
    try {
        await axios.post(PROJECTS_URL, project);
        history.push("/dashboard");

        dispatch({
            type: GET_ERRORS,
            payload: {}
        })

    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
}

export const getProjects = () => async dispatch => {
    try {
        const response = await axios.get(PROJECTS_URL);

        dispatch({
            type: GET_PROJECTS,
            payload: response.data
        })
    } catch (error) {

    }

}

/*export const getProject = (id, history) => async dispatch => {
    try {
        const response = await axios.get(`${PROJECTS_URL}/${id}`);

        dispatch({
            type: GET_PROJECT,
            payload: response.data
        })
    } catch (error) {
        history.push('/dashboard');
    }

}*/
export const getProject = (project, history) => async dispatch => {
    try {
        dispatch({
            type: GET_PROJECT,
            payload: project
        })
    } catch (error) {
        history.push('/dashboard');
    }

}

export const deleteProject = id => async dispatch => {
    try {
        await axios.delete(`${PROJECTS_URL}/${id}`);
        dispatch({
            type: DELETE_PROJECT,
            payload: id
        })

    } catch (error) {

    }
}