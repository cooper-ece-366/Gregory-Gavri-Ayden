
import Header from "./Components/Header";
import UserContext from "./Contexts/UserContext";
const App = ()=>{
  return (
    <UserContext>
      <div>
        <Header >
          <div>Trip Gen</div>
          <div>About page</div>
        </Header> 
      </div>
    </UserContext>
  );
}; 


export default App; 