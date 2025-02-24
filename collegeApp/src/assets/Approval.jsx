import { useState, useEffect } from "react";
import axios from "axios";
import PropTypes from 'prop-types';
import Swal from 'sweetalert2';
import { useNavigate } from "react-router";
import { FaBook, FaCheckCircle } from 'react-icons/fa';

const Approval = ({ user }) => {
  const [courseTitle, setCourseTitle] = useState("");
  const [courseCode, setCourseCode] = useState("");
  const [duration, setDuration] = useState("0 Weeks");
  const [credits, setCredits] = useState("0");
  const [courses, setCourses] = useState([]);
  const [filteredCourses, setFilteredCourses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isUndertakenChecked, setIsUndertakenChecked] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchCourses = async () => 
    {
      try 
      {
        const response = await axios.get('http://localhost:8080/api/courses');
        setCourses(response.data);
        setFilteredCourses(response.data);
      } 
      catch (error) {
        console.error('Error fetching courses', error);
      }
    };

    fetchCourses();
  }, []);

  const handleCourseTitleChange = (e) => {
    const searchTerm = e.target.value;
    setCourseTitle(searchTerm);
    if (searchTerm === "") 
    {
      setFilteredCourses(courses);
    } 
    else 
    {
      const filtered = courses.filter(course => course.name.toLowerCase().startsWith(searchTerm.toLowerCase()));
      setFilteredCourses(filtered);
    }
  };
  
  
  const handleCourseSelection = (e) => {
    const selectedCourse = courses.find(course => course.name === e.target.value);
    if (selectedCourse) 
    {
      setCourseTitle(selectedCourse.name);
      setCourseCode(selectedCourse.code);
      setDuration(selectedCourse.week);
      handleCreditsChange(selectedCourse.week);
    }
    else 
    {
      setCourseCode("");
      setDuration("0 Weeks");
      setCredits("0");
      setorgCourseCode("");
    }
  };

  const handleCreditsChange = (week) => {
    switch (week) 
    {
      case "4 Weeks":
        setCredits("1");
        break;
      case "8 Weeks":
        setCredits("2");
        break;
      case "12 Weeks":
        setCredits("3");
        break;
      case "16 Weeks":
        setCredits("4");
        break;
      default:
        setCredits("0");
        break;
    }
  };

  const handleBlur = () => {
    const selectedCourse = courses.find(course => course.name.toLowerCase() === courseTitle.toLowerCase());
    if (selectedCourse) 
    {
      handleCourseSelection({ target: { value: selectedCourse.name } });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    console.log(user.email)
    try 
    {
      const response = await axios.post("http://localhost:8080/api/addapproval", {
      name: `${user.first} ${user.last}`,
      sec: user.sec,
      roll: user.roll,
      year: user.year,
      dept: user.dept,
      email: user.email,
      courseTitle,
      duration,
      credits,
      courseCode
    });
    console.log(response.data);
    showAlert();
    navigate('../home');
    } 
    catch (error) 
    {
      console.error(error);
      Swal.fire({
        title: 'Submission Error',
        text: 'There was an error submitting your form. Please try again.',
        icon: 'error',
        confirmButtonText: 'OK'
      });
    } 
    finally 
    {
      setLoading(false);
    }
  };

  function showAlert() 
  {
    Swal.fire({
      title: 'Approval Sent!',
      text: 'A copy of the form has been sent to your email.',
      icon: 'success',
      confirmButtonText: 'Continue'
    });
  }

  const isFormValid = () => {
    return isUndertakenChecked && !loading;
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-green-200 to-green-400 flex flex-col items-center justify-center p-4">
      <div className="bg-green-200/80 backdrop-blur-lg rounded-2xl shadow-lg p-4 sm:p-6 md:p-8 lg:p-12 mt-14 w-full max-w-4xl">
        <div className="text-green-800 text-2xl sm:text-3xl md:text-4xl font-bold mb-4 sm:mb-6 text-center">
          Course Registration Form
        </div>
        <form onSubmit={handleSubmit} className="space-y-4 sm:space-y-6 md:space-y-8">
          <div>
            <label className="block text-sm sm:text-base md:text-lg font-semibold mb-2" htmlFor="courseTitle">
              <FaBook className="inline-block mr-2 text-green-500"/> Course Title:<span className="text-red-500">*</span>
            </label>
            <div className="flex">
              <input
                id="courseTitle"
                className="border-2 border-gray-300 rounded-lg p-2 sm:p-3 w-3/4 focus:outline-none focus:ring-2 focus:ring-green-500 transition duration-200"
                value={courseTitle}
                onChange={handleCourseTitleChange}
                required
                list="course-options"
              />
              <datalist id="course-options">
                {filteredCourses.map(course => (
                  <option key={course._id} value={course.name} />
                ))}
              </datalist>
              <button 
                type="button" 
                onClick={handleBlur}
                className="bg-green-600 ms-4 hover:bg-green-700 text-white font-semibold py-2 px-4 sm:py-3 sm:px-6 md:py-4 md:px-8 rounded-lg shadow-md transition duration-200 disabled:bg-green-400 disabled:cursor-not-allowed"
              >
                Apply
              </button>
            </div>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-2 sm:gap-4 md:gap-6">
            <div className="col-span-1">
              <label className="block text-sm sm:text-base md:text-lg font-semibold mb-2" htmlFor="courseCode">
                <FaCheckCircle className="inline-block mr-2 text-green-500"/>NPTEL Code:<span className="text-red-500">*</span>
              </label>
              <input 
                id="courseCode"
                required 
                value={courseCode}
                className="border-2 border-gray-300 rounded-lg p-2 sm:p-3 w-full bg-gray-100 focus:outline-none"
                readOnly
              />
            </div>
            <div className="col-span-1">
              <label className="block text-sm sm:text-base md:text-lg font-semibold mb-2" htmlFor="duration">
                <FaCheckCircle className="inline-block mr-2 text-green-500"/> Duration:<span className="text-red-500">*</span>
              </label>
              <input 
                id="duration"
                required 
                value={duration}
                className="border-2 border-gray-300 rounded-lg p-2 sm:p-3 w-full bg-gray-100 focus:outline-none"
                readOnly
              />
            </div>
            <div className="col-span-1">
              <label className="block text-sm sm:text-base md:text-lg font-semibold mb-2" htmlFor="credits">
                <FaCheckCircle className="inline-block mr-2 text-green-500"/> Credits:<span className="text-red-500">*</span>
              </label>
              <div className="border-2 border-gray-300 rounded-lg p-2 sm:p-3 w-full bg-gray-100">
                <p>{credits}</p>
              </div>
            </div>
          </div>
          <div>
            <label className="flex items-center">
              <input 
                type="checkbox" 
                checked={isUndertakenChecked}
                onChange={(e) => setIsUndertakenChecked(e.target.checked)}
                className="h-4 w-4 text-green-600 focus:ring-green-500 border-gray-300 rounded transition duration-200"
              />
              <span className="ml-2 text-sm sm:text-base md:text-lg">
                I confirm that the details provided are correct to the best of my knowledge.
              </span>
            </label>
          </div>

          <div className="text-center">
            <button 
              type="submit" 
              className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 sm:py-3 sm:px-6 md:py-4 md:px-8 rounded-lg shadow-md transition duration-200 disabled:bg-green-400 disabled:cursor-not-allowed"
              disabled={!isFormValid()}
            >
              {loading ? 'Submitting...' : 'Submit for Approval'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

Approval.propTypes = {
  user: PropTypes.object.isRequired,
};

export default Approval;
