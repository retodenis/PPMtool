import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const middleware = [thunk];

let store;

const loadState = () => {
  try {
    const serializedState = localStorage.getItem('state');
    if(serializedState === null) {
      return undefined;
    }
    return JSON.parse(serializedState);
  } catch (e) {
    return undefined;
  }
};

const saveState = (state) => {
  try {
    const serializedState = JSON.stringify(state);
    localStorage.setItem('state', serializedState);
  } catch (e) {
    // Ignore write errors;
  }
};

const peristedState = loadState();


if (window.navigator.userAgent.includes("Chrome")) {
  store = createStore(
    rootReducer,
    peristedState,
    compose(
      applyMiddleware(...middleware),
      window.__REDUX_DEVTOOLS_EXTENSION__ &&
        window.__REDUX_DEVTOOLS_EXTENSION__()
    )
  );
} else {
  store = createStore(
    rootReducer,
    peristedState,
    compose(applyMiddleware(...middleware))
  );
}

store.subscribe(() => {
  saveState(store.getState());
});

export default store;