
import Header from "./Components/Header";
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
    <div style={styleSheet.app}>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/tripgen" element={<TripGen />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
};


export default App; 