import { createContext, useContext, useState, useEffect } from 'react';
import http from '../services/http'; // axios instance

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const user = localStorage.getItem('user');
    if (user) setCurrentUser(JSON.parse(user));
    setLoading(false);
  }, []);

  // Login
  const login = async (email, password) => {
    try {
      setError('');
      setLoading(true);

      const { data: users } = await http.get('/users/getAllUsers');

      const user = (users || []).find(
        (u) => u?.email?.toLowerCase() === email.toLowerCase() && u?.password === password
      );

      if (!user) {
        throw new Error('Invalid email or password');
      }

      setCurrentUser(user);
      localStorage.setItem('user', JSON.stringify(user));
      return user;
    } catch (err) {
      const msg = err.response?.data?.message || err.message;
      setError('Failed to log in: ' + msg);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  // Register
  const register = async (name, email, password) => {
    try {
      setError('');
      setLoading(true);

      const [firstName, ...rest] = (name || '').trim().split(' ');
      let lastName = rest.join(' ');
      if (!lastName) lastName = 'N/A'; // fallback if no last name provided

      const { data } = await http.post('/users/addUser', {
        firstName,
        lastName,
        email,
        password,
      });

      return data;
    } catch (err) {
      const msg = err.response?.data?.message || err.message;
      setError('Failed to register: ' + msg);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    localStorage.removeItem('user');
    setCurrentUser(null);
  };

  const value = { currentUser, login, register, logout, loading, error };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => useContext(AuthContext);
export default AuthContext;
