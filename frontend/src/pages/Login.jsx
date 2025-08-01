import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Form, Container, Row, Col, Alert } from 'react-bootstrap';
import useAuth from '../hooks/useAuth';
import useDocumentTitle from '../hooks/useDocumentTitle';
import '../assets/css/Login.css';

const Login = () => {
    useDocumentTitle('Đăng nhập - Elegance Curtains');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(false);
    const [error, setError] = useState(null);
    const { login, loading } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            await login(username, password, rememberMe);
            navigate('/');
        } catch (err) {
            setError(err.message || 'Tên đăng nhập hoặc mật khẩu không đúng');
        }
    };

    return (
        <div className="login-wrapper">
            <div className="login-left">
                <img src="/public/logo.png" alt="Elegance Curtains" width={64} height={64} />
                <h2>Elegance Curtains</h2>
                <p>Nâng tầm không gian sống của bạn với những tấm rèm đẳng cấp</p>
            </div>

            <div className="login-right">
                <div className="form-container">
                    <h2>Chào mừng trở lại</h2>
                    <p>Đăng nhập để tiếp tục mua sắm</p>

                    {error && <Alert variant="danger" className="py-2">{error}</Alert>}

                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Nhập địa chỉ email"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                                disabled={loading}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <div className="d-flex justify-content-between">
                                <Form.Label>Mật khẩu</Form.Label>
                                <a href="/forgot-password" className="text-decoration-none fs-7">
                                    Quên mật khẩu?
                                </a>
                            </div>
                            <Form.Control
                                type="password"
                                placeholder="Nhập mật khẩu"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                                disabled={loading}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Check
                                type="checkbox"
                                label="Ghi nhớ đăng nhập"
                                checked={rememberMe}
                                onChange={(e) => setRememberMe(e.target.checked)}
                                disabled={loading}
                            />
                        </Form.Group>

                        <Button type="submit" className="w-100 btn-login mb-3" disabled={loading}>
                            {loading ? 'Đang đăng nhập...' : 'Đăng Nhập'}
                        </Button>
                    </Form>

                    <div className="divider-with-text">
                        <span>Hoặc đăng nhập với</span>
                    </div>

                    <div className="d-flex justify-content-center gap-3">
                        <Button variant="outline-secondary" className="btn-social">G</Button>
                        <Button variant="outline-secondary" className="btn-social">f</Button>
                    </div>

                    <div className="register-link">
                        Bạn chưa có tài khoản? <a href="/register">Đăng ký ngay</a>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Login;