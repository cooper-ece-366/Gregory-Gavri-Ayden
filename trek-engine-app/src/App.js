// CoWritten By Everyone
import Header from "./Components/Header";
import UserContext from "./Contexts/UserContext";
import TripGen from "./Components/TripGen";
import Home from "./Components/Home";
import Explore from "./Components/Explore";
import TripViewer from "./Components/TripViewer";
import React from 'react';
import { Link } from 'react-router-dom';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import About from "./Components/About";
import TripRecs from "./Components/TripRecs";

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
            <Link to="/">Home</Link>
            <Link to="/explore" >Explore</Link>
            <Link to="/about">About Us</Link>
          </Header>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/tripgen" element={<TripGen />} />
            <Route path="/explore" element={<Explore />} />
            <Route path="/viewTrip/:id" element={<TripViewer />} />
            <Route path="/triprecs/:id" element={<TripRecs />} />
            <Route path="/about" element={<About />} />
          </Routes>
        </BrowserRouter>
      </div>
    </UserContext>
  );
};


export default App; 