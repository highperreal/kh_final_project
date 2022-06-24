import React, {useEffect} from 'react';
import {Container} from "react-bootstrap";
import Refrigerator from "../../components/refrigerator/refrigerator";
import styles from "../all-recipes/all-recipe.module.css";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {useDispatch} from "react-redux";
import {setUserIngredient} from "../../app/modules/userRefrigerator";

const MyRefrigerator = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const findRefrigerator= async () => {
        await axios.get(`http://localhost:8080/fridge/v1?id=${sessionStorage.getItem("userId")}`)
            .then((res) => {
                dispatch(setUserIngredient(res.data))
            })
            .catch(() =>
                console.error("냉장고비었음")
            )
    }

    useEffect(() => {
        if (!sessionStorage.getItem("token")) {
            navigate("/");
        } else {
            findRefrigerator()
        }
    }, [])

    return (
        <Container className={styles.container}>
            <h2 className={styles.title}>
                나의 냉장고.
            </h2>
            <h3 className={styles.subTitle}>
                오늘은 어떤걸 준비하셨어요?
            </h3>
            <hr/>
            <Refrigerator user={true}/>
        </Container>
    );
};

export default MyRefrigerator;