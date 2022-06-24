import React, {useEffect, useState} from "react";
import {Container, Nav, Navbar, NavDropdown} from "react-bootstrap";
import {Link} from "react-router-dom";
import styles from "./header.module.css"
import {useDispatch, useSelector} from "react-redux";
import {selectLogin, setLogin} from "../../app/modules/login";
import axios from "axios";

const Header = () => {
    const login = useSelector(selectLogin);
    const dispatch = useDispatch();
    const [user, setUser] = useState({
        id: 0,
        username: "",
        email: "",
        image: "",
    })

    useEffect(() => {
        if(sessionStorage.getItem("token")) {
            dispatch(setLogin(true))
        }
    },[])

    useEffect(() => {
        if(sessionStorage.getItem("token")) {
            getUser();
        }
    },[login])

    const getUser = async () => {
        await axios.get(`http://localhost:8080/api/user/info?id=${sessionStorage.getItem("userId")}`)
            .then((res) => {
                setUser(res.data)
            })
    }

    const logoutClick = () => {
        sessionStorage.clear()
        dispatch(setLogin(false));
    }

    return (
        <Navbar bg="light" expand="lg">
            <Container fluid="xl">
                <Navbar.Brand>
                    <Link to="/">
                        오늘 뭐먹지!?
                    </Link>

                </Navbar.Brand>
                <Navbar.Toggle
                    className={styles.hamburgerMenu}
                    aria-controls="basic-navbar-nav"
                />
                <Navbar.Collapse className="justify-content-end" id="basic-navbar-nav">
                    <Nav className="justify-content-end">
                        <Navbar.Text className={styles.allRecipeBtn}>
                            <Link to="/all-recipes">
                                전체레시피
                            </Link>
                        </Navbar.Text>
                        {login &&
                            <NavDropdown
                            title="마이페이지"
                            id="basic-nav-dropdown"
                        >
                            <NavDropdown.Item as="span">
                                <Link to="/my-recipe">
                                    나의 레시피
                                </Link>
                            </NavDropdown.Item>
                            <NavDropdown.Item as="span">
                                <Link to="/refrigerator">
                                    나의 냉장고
                                </Link>
                            </NavDropdown.Item>
                            <NavDropdown.Item as="span">
                                <Link to="/my-page">
                                    내 정보관리
                                </Link>
                            </NavDropdown.Item>
                        </NavDropdown>}
                        <Navbar.Text>
                            {login ?
                                <Link to="/" onClick={() => {logoutClick()}}>
                                    로그아웃
                                </Link>
                                :
                                <Link to="/login">
                                    로그인
                                </Link>
                            }
                        </Navbar.Text>
                        {login &&
                            <Navbar.Text>
                                <img className={styles.profileImg} src={user.image} alt="userProfileImg"/>
                            </Navbar.Text>
                        }
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;
