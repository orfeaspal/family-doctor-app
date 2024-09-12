import classes from "./LoginPage.module.css";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";

const LoginPage = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const loginFunc = (event) => {
    event.preventDefault();

    axios
      .post(
        "http://localhost:8080/api/login",
        formData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        },
        { withCredentials: true }
      )
      .then((response) => {
        // Handle successful response here
        if (response.status === 200) {
          if (response.data.role === "ADMIN") {
            localStorage.setItem("id", response.data.userId);
            navigate("/users");
          } else if (response.data.role === "CITIZEN") {
            localStorage.setItem("id", response.data.entityId);
            navigate("/citizen");
          } else if (response.data.role === "DOCTOR") {
            localStorage.setItem("id", response.data.entityId);
            navigate("/doctor");
          }
        }
      })
      .catch((error) => {
        // Handle error here5
        if (error.response.status === 401) {
          alert("Εισάγατε λάθος στοιχεία!");
        } else {
          alert("Κάποιο σφάλμα προέκυψε!");
        }
      });
  };

  return (
    <fragment>
      <div className={classes.login_page}>
        <div className={classes.login}>
          <form onSubmit={loginFunc}>
            <span className={classes.logo}>
              <h2>Σύνδεση</h2>
            </span>
            <input
              placeholder="username"
              name="username"
              type="text"
              onChange={handleChange}
            />
            <input
              placeholder="password"
              name="password"
              type="password"
              onChange={handleChange}
            />
            <button>Σύνδεση</button>
            <div className={classes.reg}>
              <Link style={{ color: "black" }} to="/register">
                <span>Δεν έχετε λογαριασμό;</span>
              </Link>
            </div>
          </form>
        </div>
      </div>
    </fragment>
  );
};

export default LoginPage;
