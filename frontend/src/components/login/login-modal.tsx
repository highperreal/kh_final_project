import React from 'react';
import styles from "../../pages/login/login.module.css";
import GoogleLogin from "react-google-login";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {setLogin} from "../../app/modules/login";

interface User {
    providerId: string,
    email: string,
    username: string,
    image: string,
    accessToken: string,
}

// @ts-ignore
const googleClientId: string = process.env.REACT_APP_GOOGLE_CLIENT_KEY;

const LoginModal = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    //회원가입(최초 로그인시에만), 로그인
    const socialJoinLogin = async (userData: User) => {

        await axios.post("http://localhost:8080/api/user/join", userData)
            .then((res) => {
                const {id, token, image} = res.data;
                sessionStorage.setItem("token", token);
                sessionStorage.setItem("userId", id);
                sessionStorage.setItem("profileImg", image)
                dispatch(setLogin(true))
                navigate("/refrigerator")
            })
            .catch((err) => {
                console.error(err)
            })
    }

    const responseGoogle = async (res: any) => {
        if (res.profileObj) {
            const {accessToken} = res;
            const {googleId, email, name, imageUrl} = res.profileObj;

            const userData: User = {
                providerId: googleId,
                email: email,
                username: name,
                image: imageUrl,
                accessToken: accessToken,
            }

            await socialJoinLogin(userData);
        }
    }

    return (
        <>
            <GoogleLogin
                clientId={googleClientId}
                render={({onClick}) => (
                    <img
                        onClick={(e) => {
                            e.preventDefault();
                            onClick();
                        }}
                        className={styles.img}
                        src="/images/google.png"
                        alt="googleLogin"
                    />
                )}
                onSuccess={responseGoogle}
                onFailure={responseGoogle}
                cookiePolicy={'single_host_origin'}
            />
        </>
    );
};

export default LoginModal;