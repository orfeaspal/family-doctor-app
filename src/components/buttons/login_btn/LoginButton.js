import classes from "./LoginButton.module.css";
import { useNavigate } from "react-router-dom";

const LoginButton = () => {


    return(
        <>
            <button className={classes.login_btn}>
                Σύνδεση / Εγγραφή
            </button>
        </>
    );
}

export default LoginButton;