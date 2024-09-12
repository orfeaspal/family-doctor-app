import classes from "./Header.module.css";
import { useNavigate } from "react-router-dom";

const Header = (props) => {
  const navigate = useNavigate();

  return (
    <>
      <header className={classes.header}>
        <span
          style={{ cursor: "pointer" }}
          onClick={() => {
            navigate("/");
          }}
          className={classes.logo}
        >
          <h1>Αιτήσεις για ιατρεία</h1>
        </span>
      </header>
    </>
  );
};

export default Header;
