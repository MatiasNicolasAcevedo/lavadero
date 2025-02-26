import React, {
  createContext,
  useContext,
  ReactNode,
  useEffect,
  useState,
} from "react";
import { UserI } from "../types/Types";

interface AuthContextType {
  token: string | null;
  isAuthenticated: boolean;
  user: UserI | null;
  login: (email: string, password: string) => Promise<void>;
  register: (
    email: string,
    password: string,
    firstName: string,
    lastName: string,
    phone: string
  ) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const API_URL = "https://lavaderoweb.onrender.com/v1/api";

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [token, setToken] = useState<string | null>(
    localStorage.getItem("token")
  );
  const [user, setUser] = useState<UserI | null>(null);
  const isAuthenticated = !!token;

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    const storedIdUser = localStorage.getItem("idUser");
    if (storedToken && storedIdUser) {
      setToken(storedToken);
    }
  }, []);

    // Login -----------------------------------
  const login = async (email: string, password: string) => {
    try {
      const response = await fetch(`${API_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });
      if (!response.ok) {
        throw new Error("Failed to login");
      }
      const data = await response.json();
      const accessToken = data?.token;
      const id = data?.id;
      // Validar que venga el token
      if (!accessToken) {
        throw new Error("Access token not found in response");
      }
      // Construir el objeto de usuario
      const userData: UserI = {
        id,
        email: data?.email,
        firstName: data?.firstName,
        lastName: data?.lastName,
        fullName: `${data?.firstName} ${data?.lastName}`,
      };
  
      setToken(accessToken);
      setUser(userData);
      localStorage.setItem("token", accessToken);
      localStorage.setItem("idUser", String(id));
    } catch (error) {
      console.error("Failed to login", error);
      throw error;
    }
  };

  // Register -----------------------------------
  const register = async (
    email: string,
    password: string,
    firstName: string,
    lastName: string,
    phone: string
  ) => {
    try {
      const response = await fetch(`${API_URL}/auth/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password, firstName, lastName, phone }),
      });
  
      if (!response.ok) {
        throw new Error("Failed to register");
      }
    } catch (error) {
      console.error("Failed to register", error);
      throw error;
    }
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem("token");
    localStorage.removeItem("idUser");
  };

  const contextValue: AuthContextType = {
    token,
    isAuthenticated,
    user,
    login,
    register,
    logout
  };

  return (
    <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
  );
};

const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
export default useAuth;

