import axios from 'axios';

const API_URL = `${import.meta.env.VITE_API_URL}/auth`;

const login = async (username, password) => {
  const response = await axios.post(`${API_URL}/login`, {
    username,
    password
  });
  return response.data;
};

const AuthService = {
  login
};

export default AuthService;