
import Header from "./Components/Header";
import TripGen from "./Components/TripGen";

const styleSheet = {
  app: {
    fontFamily: "'Lato', sans-serif"
  }
}

const App = ()=>{
  return (
    <div style={styleSheet.app}>
      <Header >
        <div>Trip Gen</div>
        <div>About page</div>
      </Header> 
      <TripGen></TripGen>
    </div>
  );
}; 


export default App; 