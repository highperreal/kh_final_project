import styles from "./main-page.module.css"
import Refrigerator from "../../components/refrigerator/refrigerator";
import {Container} from "react-bootstrap";

const MainPage = () => {

    return (
        <div>

            <div className={styles.videoBox}>

                <video className={styles.video} muted autoPlay loop>
                    <source src="/images/chop.mp4" type="video/mp4"/>
                    <strong>Your browser does not support the video tag.</strong>
                </video>

                <div className={styles.titleDesktop}>
                    <h1>하루. 당신의 소중한 한끼</h1>
                    <p>고민 하지말고 바로 요리해보세요.</p>
                </div>
            </div>

            <Container className={styles.refrigeratorTitle}>
                <hr/><br/>
                <div>
                    넣고, 찾고,<br/>
                    먹고.
                </div>
                <p>재료를 넣어보세요.</p>
            </Container>

            <Container className={styles.titleMobile}>
                <div>
                    <p>하루. 당신의 소중한 한끼</p>
                    <p>고민 하지말고 바로 요리해보세요.</p>
                </div>
                <div>
                    <hr/><br/>
                    <p>넣고, 찾고,</p>
                    <p>먹고.</p>
                    <p>재료를 넣어보세요.</p>
                </div>
            </Container>

            <Refrigerator user={false}/>

        </div>
    );

};

export default MainPage;
