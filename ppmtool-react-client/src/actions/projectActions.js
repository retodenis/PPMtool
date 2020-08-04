import axios from 'axios';
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT } from './types';

const PROJECTS_URL = 'http://localhost:8080/api/project';

export const createProject = (project, history) => async dispatch => {
    try {
        const response = await axios.post(PROJECTS_URL, project);
        history.push("/dashboard");

    } catch (error) {
        dispatch({
            type:GET_ERRORS,
            payload:error.response.data
        })
    }
}

export const getProjects = () => async dispatch => {
    try {
        const response = await axios.get(PROJECTS_URL);

        dispatch({
            type:GET_PROJECTS,
            payload: response.data
        })
    } catch (error) {
        
    }
    
}

export const getProject = (id, history) => async dispatch => {
    try {
        const response = await axios.get(`${PROJECTS_URL}/${id}`);

        dispatch({
            type:GET_PROJECT,
            payload: response.data
        })
    } catch (error) {
        
    }
    
}