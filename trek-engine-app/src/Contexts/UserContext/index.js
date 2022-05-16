// Written By Gregory Presser
import React, { useState, useContext, createContext, useRef, useEffect } from 'react';
import axios from "axios";

const UserContextP = createContext();
export const useUserContext = () => useContext(UserContextP);

const UserContext = ({ children }) => {
    const [user, setUser] = useState(null);
    const api_obj = useRef(null);

    useEffect(() => { }, []);

    const login = async (g_api_obj) => {
        const { data: db_user } = await axios.post("http://localhost:4567/api/v1/user/me", { id_token: g_api_obj.tokenObj.id_token });
        api_obj.current = g_api_obj;
        setUser(db_user);
        const refresh = async () => {
            api_obj.current = await api_obj.current.reloadAuthResponse();
            setTimeout(refresh, api_obj.current.expires_in * 1000);
        }
        setTimeout(refresh, api_obj.current.expires_in * 1000);
    }
    const logout = () => { setUser(null); api_obj.current = null; }
    const getIdToken = () => api_obj.current.id_token || api_obj.current.tokenId;
    return (
        <UserContextP.Provider value={{ user, setUser, login, logout, getIdToken }}>
            {children}
        </UserContextP.Provider>
    );
}

export default UserContext;


