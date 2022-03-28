
import Header from "./Components/Header";
import UserContext from "./Contexts/UserContext";
import TripGen from "./Components/TripGen";
import Home from "./Components/Home";
import React from 'react';
import { Link } from 'react-router-dom';
import { BrowserRouter, Route, Routes } from "react-router-dom";

const styleSheet = {
  app: {
    fontFamily: "'Sen', sans-serif",
    height: "100vh"
  }
}

const App = () => {
  return (
    <UserContext>
      <div style={styleSheet.app}>
        <BrowserRouter>
          <Header >
            <Link to = "/">Home</Link>
            <Link to = "/explore" >Explore</Link>
            <Link to = "/about">About Us</Link>
          </Header>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/tripgen" element={<TripGen />} />
          </Routes>
        </BrowserRouter>
      </div>
    </UserContext>
  );
};


export default App; 