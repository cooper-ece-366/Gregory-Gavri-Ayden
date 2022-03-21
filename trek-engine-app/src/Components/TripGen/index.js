import TripForm from "./TripForm"

const styleSheet = {
    container: {
        display: "flex",
        paddingTop: "50px",
        height: "100%",
        width: "100%",
    }
}

const TripGen = (props) => {
    return (
        <div style={styleSheet.container}>
            <TripForm></TripForm>
        </div>
    )
}

export default TripGen;