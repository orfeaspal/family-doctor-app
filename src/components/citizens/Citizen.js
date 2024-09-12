import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import classes from "./Citizen.module.css";
import AppPagination from "../Pagination/AppPagination";
import axios from "axios";

const items_per_page = 6;
const Citizen = () => {
  const [doctors, setDoctors] = useState([]);
  const [requests, setRequests] = useState([]);
  const navigate = useNavigate();
  const [page, setPage] = useState(1);

  // Assuming you have a way to get the logged-in citizen's ID
  const citizenId = localStorage.getItem("id"); // Replace this with the actual citizen ID

  const handleChangePagination = (event, value) => {
    setPage(value);
  };

  // Function to fetch requests
  const fetchRequests = () => {
    axios
      .get("http://localhost:8080/api/requests/citizen/" + citizenId, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        if (response.status === 200) {
          setRequests(response.data);
        }
      })
      .catch((error) => {
        console.log("Error fetching requests:", error.response);
      });
  };

  useEffect(() => {
    fetchRequests(); // Fetch requests on component mount

    axios
      .get("http://localhost:8080/api/users/doctors", {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        if (response.status === 200) {
          setDoctors(response.data);
        }
      })
      .catch((error) => {
        console.log("Error fetching doctors:", error.response);
      });
  }, []);

  const handleRequestClick = (doctorId) => {
    // Check if the user has any non-rejected requests
    const hasActiveRequest = requests.some(
      (request) => request.status === "PENDING" || request.status === "APPROVED"
    );

    if (hasActiveRequest) {
      alert(
        "Δεν μπορείτε να κάνετε αίτηση, καθώς υπάρχει ήδη μία αίτηση σε εξέλιξη ή εγκριθείσα."
      );
      return;
    }

    axios
      .post(
        "http://localhost:8080/api/requests",
        new URLSearchParams({
          citizenId: citizenId.toString(), // Convert to string
          doctorId: doctorId.toString(),
          status: "PENDING", // Assuming 'PENDING' is a valid RequestStatus
        }),
        {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
        }
      )
      .then((response) => {
        if (response.status === 201) {
          alert("Η αίτηση σας ολοκληρώθηκε επιτυχώς!");
          fetchRequests(); // Refetch requests after successful submission
        }
      })
      .catch((error) => {
        alert("Κάποιο σφάλμα προέκυψε κατά την καταχώρηση της αίτησης σας!");
      });
  };

  return (
    <div className={classes.citizen}>
      <div className="container">
        <div className="card">
          <div className="card-title" style={{ textAlign: "center" }}>
            <h2>Διαθέσιμοι Ιατροί</h2>
          </div>
          <div
            className="card-body"
            style={{
              display: "flex",
              alignItems: "flex-start",
              justifyContent: "flex-start",
              gap: "0.4rem",
              flexDirection: "column",
            }}
          >
            <table className="table table-bordered">
              <thead className="bg-dark text-white">
                <tr>
                  <td>Ονοματεπώνυμο</td>
                  <td>Email</td>
                  <td>Τηλέφωνο</td>
                  <td>Ειδικότητα</td>
                  <td>Αίτηση</td>
                </tr>
              </thead>
              <tbody>
                {doctors
                  .slice((page - 1) * items_per_page, page * items_per_page)
                  .map((doctor) => (
                    <tr key={doctor.id}>
                      <td>
                        {doctor.firstName} {doctor.lastName}
                      </td>
                      <td>{doctor.email}</td>
                      <td>{doctor.phone}</td>
                      <td>{doctor.specialty}</td>
                      <td
                        style={{
                          display: "flex",
                          alignItems: "flex-start",
                          justifyContent: "flex-start",
                          gap: "0.25rem",
                        }}
                      >
                        <button
                          type="button"
                          className="btn btn-success"
                          onClick={() => handleRequestClick(doctor.id)}
                          disabled={requests.some(
                            (request) =>
                              request.status === "PENDING" ||
                              request.status === "APPROVED"
                          )} // Disable if any request is pending or approved
                        >
                          Αίτηση
                        </button>{" "}
                      </td>
                    </tr>
                  ))}
              </tbody>
            </table>
            <AppPagination
              total_cards={doctors.length}
              items_per_page={items_per_page}
              page={page}
              onChange={handleChangePagination}
            />
          </div>
        </div>
      </div>
      <div className="container">
        <div className="card">
          <div className="card-title" style={{ textAlign: "center" }}>
            <h2>Οι αιτήσεις μου</h2>
          </div>
          <div
            className="card-body"
            style={{
              display: "flex",
              alignItems: "flex-start",
              justifyContent: "flex-start",
              gap: "0.4rem",
              flexDirection: "column",
            }}
          >
            <table className="table table-bordered">
              <thead className="bg-dark text-white">
                <tr>
                  <td>ID Αίτησης</td>
                  <td>Ονοματεπώνυμο Ιατρού</td>
                  <td>Διεύθυνση Ιατρού</td>
                  <td>Κατάσταση Αίτησης</td>
                </tr>
              </thead>
              <tbody>
                {requests
                  .slice((page - 1) * items_per_page, page * items_per_page)
                  .map((request) => (
                    <tr key={request.id}>
                      <td>{request.id}</td>
                      <td>{request.doctorFullName}</td>
                      <td>{request.doctorAddress}</td>
                      <td>{request.status}</td>
                    </tr>
                  ))}
              </tbody>
            </table>
            <AppPagination
              total_cards={requests.length}
              items_per_page={items_per_page}
              page={page}
              onChange={handleChangePagination}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Citizen;
