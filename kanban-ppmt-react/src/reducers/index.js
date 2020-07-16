import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";

//Reducer State
export default combineReducers({
  errors: errorReducer,
  project: projectReducer,
});
