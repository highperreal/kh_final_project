import React, {useState} from "react";
import {Button, Modal} from "react-bootstrap";
import styles from "./ingredient-modal.module.css"
import IngredientBox from "../ingredient-box/ingredient-box";

interface IProps {
    chosenIngredients: {
        ingredientId: number,
        ingredientName: string,
    }[],
    user: boolean,
}

export function IngredientModal(props: IProps) {

    const { chosenIngredients, user} = props
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <div className={styles.ingredientBtn}>
                <Button
                    variant="outline-danger"
                    onClick={handleShow}
                >
                    재료보기
                </Button>
            </div>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>
                        재료
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <IngredientBox user={user} chosenIngredients={chosenIngredients}/>
                </Modal.Body>
                <Modal.Footer>
                    <Button
                        variant="outline-dark"
                        onClick={handleClose}
                    >
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

