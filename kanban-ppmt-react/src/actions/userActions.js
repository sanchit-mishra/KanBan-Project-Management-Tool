import axios from "axios";
import { GET_ERRORS, SET_USER } from "./types";
import { setJwtToken } from "../securityUtil/setJwtToken";
import jwt_decode from "jwt-decode";

export const registerUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const loginUser = (loginRequest) => async (dispatch) => {
  try {
    //hit endPoint
    const res = await axios.post("/api/users/login", loginRequest);
    //extract token
    const { token } = res.data;
    //store in local Storage
    localStorage.setItem("jwtToken", token);
    //set token in header
    setJwtToken(token);
    //decode token on React
    const decoded = jwt_decode(token);
    //dispatch to our securityReducer
    dispatch({
      type: SET_USER,
      payload: decoded,
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};
