import {createSlice} from "@reduxjs/toolkit";
import {AppState} from "../store";


const initialState = {
    login: false
}

export const LoginSlice = createSlice({
    name: 'login',
    initialState,
    reducers: {
        setLogin: (state, action) => {
            state.login = action.payload;
        },
    }
})

export default LoginSlice.reducer;
export const {setLogin} = LoginSlice.actions;
export const selectLogin = (state: AppState) => state.login.login;