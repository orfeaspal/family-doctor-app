import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/header/Header";
import LoginPage from "./components/login_page/LoginPage";
import Users from "./components/admin/Users";
import UserCreate from "./components/admin/UserCreate";
import UserEdit from "./components/admin/UserEdit";
import UserDetail from "./components/admin/UserDetail";
import RegisterPage from "./components/register_page/RegisterPage";
import Citizen from "./components/citizens/Citizen";
import Doctor from "./components/doctors/Doctor";

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/users" element={<Users />} />
        <Route path="/citizen" element={<Citizen />} />
        <Route path="/doctor" element={<Doctor />} />
        <Route path="/edit_users/:id" element={<UserEdit />} />
        <Route path="/create_users" element={<UserCreate />} />
        <Route path="/user_data/:id" element={<UserDetail />} />
      </Routes>
    </Router>
  );
}

export default App;
