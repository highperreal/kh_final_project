import React, {useState} from 'react';
import {Card, Col, Modal, Row} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import styles from "./recipe-card.module.css";
import {Recipes} from "../../pages/all-recipes/all-recipes";
import LoginModal from "../login/login-modal";
import axios from "axios";
import {useDispatch} from "react-redux";
import {removeMyRecipes} from "../../app/modules/myRecipes";

interface IProps {
    my_recipe?: boolean
    recipes: Recipes,
}

const RecipeCard = (props: IProps) => {

    const {recipes, my_recipe} = props
    const navigate = useNavigate();
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const dispatch = useDispatch();

    const recipeClickHandle = (id: number) => {
        if (!sessionStorage.getItem("token")) {
            handleShow()
        } else {
            navigate(`/recipe-detail/${id}`)
        }
    }


    const removeMyRecipe = async (id: number) => {
        await axios.delete("http://localhost:8080/myrecipe/delete", {data: {
                userid: sessionStorage.getItem("userId"),
                recipeid: id
            }})
            .catch((err) => {
                console.error(err)
            })
        dispatch(removeMyRecipes(id))
    }

    return (
        <div className={styles.container}>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>로그인이 필요해요.</Modal.Title>
                </Modal.Header>
                <Modal.Body className={styles.loginModal}>
                    <p> 맛있는 레시피가 기다리고 있어요.</p>
                    <LoginModal/>
                </Modal.Body>
            </Modal>

            <Row xs={1} sm={2} xl={3} xxl={4} className="g-4">
                {recipes.data.map((recipe) => (
                    <Col key={recipe.id}>

                        <Card className={styles.card}>
                            <div
                                onClick={() => {
                                    recipeClickHandle(recipe.id)
                                }}>
                                <Card.Img
                                    className={styles.img}
                                    variant="top"
                                    src={recipe.image}
                                />
                            </div>
                            <Card.Body>
                                <div
                                    className={styles.cardBody}
                                    onClick={() => {
                                        recipeClickHandle(recipe.id)
                                    }}>
                                    <Card.Title
                                        className={styles.title}
                                    >
                                        {recipe.name}
                                    </Card.Title>
                                    <Card.Text
                                        className={styles.text}
                                    >
                                        {recipe.subtitle}
                                    </Card.Text>
                                </div>
                                <Card.Text
                                    as="div"
                                    className={styles.text}
                                >
                                    {recipe.calorie}kcal<br/>
                                    {recipe.likeQuantity}명이 좋아해요.
                                    <div className={styles.heartBtnBox}>
                                        {my_recipe &&
                                            <img
                                                onClick={() => {
                                                    removeMyRecipe(recipe.id)
                                                }}
                                                src="/images/heart.png"
                                            />
                                        }
                                    </div>
                                </Card.Text>
                            </Card.Body>
                        </Card>

                    </Col>
                ))}
            </Row>
        </div>
    );
};

export default RecipeCard;