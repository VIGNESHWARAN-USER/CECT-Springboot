import React, { useEffect, useState } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { FaCheckCircle, FaTimesCircle } from 'react-icons/fa';

const ReqApprlhod = ({ dept }) => {
  const [details, setDetails] = useState([]);
  const [loading, setLoading] = useState(true);
  const [loading1, setLoading1] = useState(null);
  const [error, setError] = useState(null);
  const [selectedDetails, setSelectedDetails] = useState([]);
  const [selectAll, setSelectAll] = useState(false);
  const [storedDept, setStoredDept] = useState(dept);
  const navigate = useNavigate();

  function showAlert() {
    Swal.fire({
      title: 'Approval is Done!',
      text: 'You can check view the records in Approved Records',
      icon: 'success',
      confirmButtonText: 'OK'
    });
  }

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user) {
      setStoredDept(user.dept);
    }
  }, [dept]);

  useEffect(() => {
    if (!storedDept) return;

    const fetchDetails = async () => {
      try {
        const response = await axios.post('http://localhost:8080/api/detailshod',  {
            dept: storedDept
          });
        setDetails(response.data);
        localStorage.setItem('details', JSON.stringify(response.data));
        setLoading(false);
      } catch (error) {
        console.error('Error fetching details:', error);
        setError('Error fetching details');
        setLoading(false);
      }
    };

    fetchDetails();
  }, [storedDept]);

  useEffect(() => {
    if (selectAll) {
      setSelectedDetails(details.map(detail => detail._id));
    } else {
      setSelectedDetails([]);
    }
  }, [selectAll, details]);

  const handleSelectDetail = (id) => {
    if (selectedDetails.includes(id)) {
      setSelectedDetails(selectedDetails.filter(detailId => detailId !== id));
    } else {
      setSelectedDetails([...selectedDetails, id]);
    }
  };

  const handleApproveAll = async () => {
    try {
      setLoading1('approve')
      console.log(selectedDetails)
      await axios.post('http://localhost:8080/api/approveallhod', { ids: selectedDetails });
      setDetails([]);
      showAlert();
    } catch (error) {
      console.error('Error approving details:', error);
    }finally{
      setLoading1(null)
    }
  };

  const handleViewApproved = () => {
    navigate('/approvedstud');
  };

  const handleViewRejected = () => {
    navigate('/rejectedstud');
  };

  if (loading) {
    return <div className="flex justify-center items-center h-screen text-green-700 text-2xl animate-pulse">Loading...</div>;
  }

  if (error) {
    return <div className="flex justify-center items-center h-screen text-green-700 text-2xl">{error}</div>;
  }

  return (
    <div className="min-h-screen bg-gradient-to-b from-green-200 to-green-400 flex flex-col items-center justify-center p-4">
      <div className="max-w-7xl w-full mx-auto bg-green-200/80 backdrop-blur-lg rounded-2xl shadow-lg p-4 md:p-8">
        <h1 className="text-2xl md:text-4xl font-bold text-green-900 text-center mb-6">
          Course Approval Form
        </h1>
        <hr className="mb-6 border-2 border-green-800" />

        <div className="flex flex-col md:flex-row justify-between mb-8 space-y-4 md:space-y-0">
          <button
            onClick={handleViewApproved}
            className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 md:px-6 rounded-full shadow-md transition-all duration-300 transform hover:-translate-y-1"
          >
            View Approved Records
          </button>
          <button
            onClick={handleViewRejected}
            className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 md:px-6 rounded-full shadow-md transition-all duration-300 transform hover:-translate-y-1"
          >
            View Rejected Records
          </button>
        </div>

          <div className="overflow-x-auto shadow-lg rounded-lg">
            <table className="w-full border-collapse bg-green-100/70 backdrop-blur-lg rounded-lg text-xs md:text-sm">
              <thead className="bg-green-600 text-white">
                <tr>
                  <th className="py-2 md:py-4 px-2 md:px-6 font-semibold">
                    <span className="me-2">Select All</span>
                    <input
                      type="checkbox"
                      checked={selectAll}
                      onChange={() => setSelectAll(!selectAll)}
                    />
                  </th>
                  <th className="py-2 md:py-4 px-2 md:px-6 font-semibold">Candidate Name</th>
                  <th className="py-2 md:py-4 px-2 md:px-6 font-semibold">Register Number</th>
                  <th className="py-2 md:py-4 px-2 md:px-6 font-semibold">Course Name</th>
                  <th className="py-2 md:py-4 px-2 md:px-6 font-semibold">Credits</th>
                  <th className="py-2 md:py-4 px-2 md:px-6 font-semibold">Status</th>
                  <th className="py-2 md:py-4 px-2 md:px-6 font-semibold">Actions</th>
                </tr>
              </thead>
              <tbody>
              {details.length === 0 ? (
                  <tr>
                    <td colSpan="6" className="py-6 text-green-700 text-center text-sm sm:text-lg font-bold">
                      NO RECORDS FOUND
                    </td>
                  </tr>
                ) : (
                  details.map((detail) => (
                    <tr key={detail._id} className="hover:bg-green-100 transition-all duration-200">
                      <td className="py-4 px-3 sm:px-6 text-xs md:text-sm">
                        <input
                          type="checkbox"
                          checked={selectedDetails.includes(detail._id)}
                          onChange={() => handleSelectDetail(detail._id)}
                          className="form-checkbox h-4 w-4"
                        />
                      </td>
                      <td className="py-4 px-3 sm:px-6 text-xs sm:text-sm md:text-base">{detail.name}</td>
                      <td className="py-4 px-3 sm:px-6 text-xs sm:text-sm md:text-base">{detail.roll}</td>
                      <td className="py-4 px-3 sm:px-6 text-xs sm:text-sm md:text-base">{detail.courseTitle}</td>
                      <td className="py-4 px-3 sm:px-6 text-xs sm:text-sm md:text-base">{detail.credits}</td>
                      <td className={`py-4 px-3 sm:px-6 text-xs sm:text-sm md:text-base font-semibold ${detail.status === 'Approved' ? 'text-green-700' : 'text-red-700'}`}>
                        {detail.status === 'Approved' ? <FaCheckCircle className="inline-block mr-1"/> : <FaTimesCircle className="inline-block mr-1"/>}
                        {detail.status}
                      </td>
                      <td className="py-4 px-3 sm:px-6 text-center">
                        <button
                          onClick={() => navigate('/preview', { state: { detail } })}
                          className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-full shadow-md transition-all duration-300 transform hover:-translate-y-1 text-xs md:text-sm"
                        >
                          OPEN
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        

        <button
          onClick={handleApproveAll}
          className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 md:px-6 rounded-full shadow-md transition-all duration-300 mt-4 md:mt-6"
          disabled={selectedDetails.length === 0}
        >
          {loading1 === 'approve' ? 'Approving...' : 'Approve All'}
        </button>
      </div>
    </div>
  );
};

ReqApprlhod.propTypes = {
  dept: PropTypes.string.isRequired,
};

export default ReqApprlhod;
