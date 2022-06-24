import React, {useEffect, useState} from 'react';
import {Button, Container, Form} from "react-bootstrap";
import styles from "./my-page.module.css";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const MyPage = () => {
    const navigate = useNavigate();
    const [editClick, setEditClick] = useState(true)
    const [user, setUser] = useState({
        id: 0,
        username: "",
        email: "",
        image: "",
    })

    useEffect(() => {
        if (!sessionStorage.getItem("token")) {
            navigate("/");
        }
    }, [])

    const getUser = async () => {
        await axios.get(`http://localhost:8080/api/user/info?id=${sessionStorage.getItem("userId")}`)
            .then((res) => {
                setUser(res.data)
            })
    }

    useEffect(() => {
        getUser();
    }, [])

    const changeHandle = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();
        setUser({...user, username: e.target.value})
    }

    const editClickHandle = async () => {
        await axios.post("http://localhost:8080/api/user/update", user).catch(err => console.error(err));
        setEditClick(!editClick)
    }


    return (
        <Container className={styles.container}>
            <h2 className={styles.title}>
                내 정보관리.
            </h2>
            <h3 className={styles.subTitle}>
                오늘은 어떤 요리사가 되볼까요?
            </h3>
            <hr/>
            <div className={styles.userInfoContainer}>
                <img src={user.image}/>
                {editClick ?
                    <div className={styles.userInfoBox}>
                        <p>
                            닉네임: {user.username}
                        </p>
                        <p>
                            Email: {user.email}
                        </p>
                        <Button
                            onClick={() => {
                                setEditClick(!editClick)
                            }}
                            variant="outline-dark"
                            size='sm'
                        >
                            정보수정

                        </Button>
                    </div>
                    :
                    <div className={styles.userInfoBox}>

                        <Form.Control
                            className={styles.textField}
                            type="text"
                            id="searchRecipe"
                            value={user.username}
                            onChange={changeHandle}
                        />

                        <p>
                            Email: {user.email}
                        </p>

                        <Button
                            onClick={() => {
                                editClickHandle()
                            }}
                            variant="outline-dark"
                            size="sm"
                        >
                            저장
                        </Button>
                    </div>
                }
            </div>
        </Container>
    );
};

export default MyPage;