import {GET_PR_TASKS, GET_PR_TASK, DELETE_PR_TASK} from '../actions/types';

const initialState = {
    prTasks: [],
    prTask: {}
}

export default function(state=initialState, action) {
    switch(action.type) {

        case GET_PR_TASKS:
            return {
                ...state,
                prTasks: action.payload
            }
        
        case GET_PR_TASK:
            return {
                ...state,
                prTask: action.payload
            }

        case DELETE_PR_TASK:
            return {
                ...state,
                prTasks: state.prTasks.filter(
                    task => task.ptSeq !== action.payload
                )
            }

        default:
            return state;
    }
}