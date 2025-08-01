import { useState, useContext, useCallback } from 'react';
import AuthContext from '../context/AuthContext';
import AuthService from '../services/AuthService';

const useAuth = () => {
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const { user, setUser } = useContext(AuthContext);

  const login = useCallback(async (username, password, rememberMe) => {
    setLoading(true);
    setError(null);
    
    try {
      const userData = await AuthService.login(username, password);
      setUser(userData);
      
      // Store based on remember me choice
      const storage = rememberMe ? localStorage : sessionStorage;
      storage.setItem('user', JSON.stringify(userData));
      
      return userData;
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Đăng nhập thất bại');
      throw err;
    } finally {
      setLoading(false);
    }
  }, [setUser]);

  const logout = useCallback(() => {
    setUser(null);
    localStorage.removeItem('user');
    sessionStorage.removeItem('user');
  }, [setUser]);

  return { user, login, logout, error, loading };
};

export default useAuth;