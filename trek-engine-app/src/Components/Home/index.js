import Logo from '../Utils/Logo';

const styleSheet = {
    container: (url = "./assets/images/img3.jpg") => ({
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        height: "100%",
        width: "100%",
        backdropFilter: "blur(4px)",
        background: `linear-gradient(180deg, rgba(5, 13, 43, 0.5) 36.98%, rgba(222, 45, 22, 0.5) 100%), url(${url})`,
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
        overflow: "hidden",
        userSelect: "none"
    }),
    background: {
        flexShrink: "0",
        minWidth: "100%",
        minHeight: "100%"
    },
    title: {
        color: "white",
        textAlign: "center",
        fontSize: "5em",
    },
    text: {
        color: "white",
        textAlign: "center",
        fontSize: "2em",
    },
    buildButton: {
        backgroundColor: "rgba(222, 45, 22, 0.5)",
        color: "white",
        background: "#DE2D16",
        border: "4px solid #781C10",
        boxSizing: "border-box",
        boxShadow: "0px 4px 4px rgba(0, 0, 0, 0.25)",
        borderRadius: "25px",
        height: "72px",
        width: "300px",
        fontSize: "1.5em",
        marginTop: "150px",
        fontFamily: "'Sen', sans-serif",
    }
}

const Home = (props) => {
    return (
        <div style={styleSheet.container()}>
            <div><h1 style={styleSheet.title}>let's explore the world.</h1></div>
            <div><Logo size="100" color="white"></Logo></div>
            <div><h1 style={styleSheet.text}>TrekEngine provides a fast, smart, and simple solution for planning your next adventure.</h1></div>
            <div><button style={styleSheet.buildButton}>Build your trip now!</button></div>
        </div>
    )
}

export default Home;