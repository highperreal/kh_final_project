import React, {useEffect} from 'react';
import {Container} from "react-bootstrap";
import styles from "./login.module.css"

import {useNavigate} from "react-router-dom";
import LoginModal from "../../components/login/login-modal";

const Login = () => {

    const navigate = useNavigate();

    //로그인상태에서 login 페이지 진입 불가 설정
    useEffect(() => {
        if (sessionStorage.getItem("token")) {
            navigate("/");
        }
    }, [])


    return (
        <Container>
            <p className={styles.title}>
                맛있는 요리가 기다리고있어요.
            </p>
            <div className={styles.loginBox}>
            <p className={styles.subTitle}>
                로그인하세요.
            </p>
                <LoginModal/>
            </div>
        </Container>
    )
};

export default Login;
