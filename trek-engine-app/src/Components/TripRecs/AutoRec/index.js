// Written By Gavri Kepets
const styleSheet = {
    container: {
        width: "100%",
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between"
    }
}

const AutoRec = (props) => {
    console.log("props: ", props);
    return (
        <div style={styleSheet.container}>
            <div>{props.trip.meta.name}</div>
            <button onClick={() => props.override(props.trip)}>Select Trip</button>
        </div>
    );
}

export default AutoRec;