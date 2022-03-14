const styleSheet = {
    container : {
        width: "200px",
    },
    form : {
        display: "flex",
        flexDirection: "column",
    }
    
}

const TripForm = (props) => {
    return (
        <div style = {styleSheet.container}>
            <h1>Build Your Trip Here!</h1>
            <form style={styleSheet.form}>
                From
                <input type="text" placeholder="Start Location"></input>
                To
                <input type="text" placeholder="End Location"></input>
            </form>
            {/* Start Location | End Location | Trip Length | Advanced Options     */}
        </div>
    )
}

export default TripForm;