import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";
import prTaskReducer from "./prTaskReducer";

export default combineReducers({
  errors: errorReducer,
  project: projectReducer,
  prTasks: prTaskReducer
});