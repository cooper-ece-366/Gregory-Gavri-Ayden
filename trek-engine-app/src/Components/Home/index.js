const styleSheet = {
    container: (url = "./assets/images/img1.jpg") => ({
        display: "flex",
        height: "100%",
        width: "100%",
        backdropFilter: "blur(4px)",
        background: `linear-gradient(180deg, rgba(5, 13, 43, 0.5) 36.98%, rgba(222, 45, 22, 0.5) 100%), url(${url})`,
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
        overflow: "hidden"
    }),
    background: {
        flexShrink: "0",
        minWidth: "100%",
        minHeight: "100%"
    }
}

const Home = (props) => {
    return (
        <div style={styleSheet.container()}>
        </div>
    )
}

export default Home;