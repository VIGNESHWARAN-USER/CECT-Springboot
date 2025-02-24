import { useEffect, useState } from 'react';
import axios from 'axios';

const ApprovedStudDrop = () => {
    const [details, setDetails] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

        const user = JSON.parse(localStorage.getItem('user'));
        
    useEffect(() => {
        if (!user) return;
        const fetchDetails = async () => {
            try 
            {
                const endpoint = 'http://localhost:8080/api/detailsapproveddrop';
                const response = await axios.post(endpoint, 
                {
                    role: user.role, 
                    year: user.year,
                    dept:user.dept,
                    sec:user.sec          
                });
                setDetails(response.data);
                localStorage.setItem('details', JSON.stringify(response.data));
                setLoading(false);
            } 
            catch (error) 
            {
                console.error('Error fetching details:', error);
                setError('Error fetching details');
                setLoading(false);
            }
        };
        fetchDetails();
    }, []);

    const handleDelete = async () => {
        try 
        {
            const response = await axios.post(`http://localhost:8080/api/deletedrops`,{
                    role: user.role, 
                    year: user.year,
                    dept:user.dept,
                    sec:user.sec          
                });
            if (response.data.message) 
            {
                setDetails([]);
                console.log(response.data.message);
            }
        } 
        catch (err) 
        {
            console.error("Error Occurred:", err);
        }
    };

    if (loading) 
    {
        return <div className="flex justify-center items-center h-screen text-green-700 text-2xl animate-pulse">Loading...</div>;
    }

    if (error) 
    {
        return <div className="flex justify-center items-center h-screen text-red-700 text-2xl">{error}</div>;
    }

    return (
        <div className="min-h-screen bg-gradient-to-b from-green-200 to-green-400 flex flex-col items-center justify-center p-4">
            <div className="w-full max-w-7xl mx-auto">
                <div className="bg-green-200/80 backdrop-blur-lg rounded-2xl shadow-lg p-6 md:p-12">
                    <div className='flex flex-col md:flex-row justify-between items-center mb-8'>
                        <h1 className="text-2xl md:text-4xl font-bold text-green-900 text-center mb-6 md:mb-0">
                            Approved Drop Records
                        </h1>
                        <button 
                            onClick={handleDelete} 
                            className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 md:py-2 md:px-6 rounded-full shadow-md transition-all duration-300 transform hover:-translate-y-1 text-sm md:text-base"
                        >
                            Delete All Records
                        </button>
                    </div>
                    <hr className="mb-6 border-2 border-green-800" />
                    <div className="overflow-x-auto shadow-lg rounded-lg">
                        <table className="w-full border-collapse bg-green-100/70 backdrop-blur-lg rounded-lg">
                            <thead className="bg-green-600 text-white text-left text-xs md:text-sm">
                                <tr>
                                    <th className="py-3 px-4 md:py-4 md:px-6 font-semibold">Candidate Name</th>
                                    <th className="py-3 px-4 md:py-4 md:px-6 font-semibold">Register Number</th>
                                    <th className="py-3 px-4 md:py-4 md:px-6 font-semibold">Course Name</th>
                                    <th className="py-3 px-4 md:py-4 md:px-6 font-semibold">Credits</th>
                                    <th className="py-3 px-4 md:py-4 md:px-6 font-semibold">Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                {details.length === 0 ? (
                                    <tr>
                                        <td colSpan="4" className="py-6 text-green-700 text-center text-lg font-bold">
                                            NO RECORDS FOUND
                                        </td>
                                    </tr>
                                ) : (
                                    details.map((student) => (
                                        <tr key={student._id} className="hover:bg-green-100 transition-all duration-200">
                                            <td className="py-3 px-4 md:py-4 md:px-6 text-xs md:text-sm">{student.name}</td>
                                            <td className="py-3 px-4 md:py-4 md:px-6 text-xs md:text-sm">{student.roll}</td>
                                            <td className="py-3 px-4 md:py-4 md:px-6 text-xs md:text-sm">{student.courseName}</td>
                                            <td className="py-3 px-4 md:py-4 md:px-6 text-xs md:text-sm">{student.credits}</td>
                                            <td className="py-3 px-4 md:py-4 md:px-6 text-xs md:text-sm">{student.status}</td>
                                        </tr>
                                    ))
                                )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ApprovedStudDrop;
