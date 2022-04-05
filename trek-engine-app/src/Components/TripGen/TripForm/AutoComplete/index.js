// Written by Gavri Kepets, Greg Presser
import PlacesAutocomplete from 'react-places-autocomplete';
import { useState } from 'react';
import TinyColor from 'tinycolor2';
import "./styles.css"

const styleSheet = {
  dropdown: {
    position: "absolute",
    zIndex: 1000,
    borderRadius: "10px",
    boxShadow: "0 4px 10px -2px #000000",
    width: "100%",
    marginTop: "40px",
    width: "85%"
  },
  dropdownElement: {
    width: "5%",
  },
  input: (color) => {
    let darkColor = TinyColor(color).darken(40).toString();
    let lightColor = TinyColor(color).darken(20).toString();

    return ({
      width: "80%",
      fontSize: "16px",
      background: `linear-gradient(91.28deg, ${lightColor} 0%, ${darkColor} 100%)`,
      borderRadius: "10px",
      height: "30px",
      border: `2px solid ${color}`,
      color: "white",
    })
  },
  container: {
    width: "100%",
    display: "flex",
    justifyContent: "center",
  },
  parent: {
    width: "100%",
  }
}

const AutoComplete = ({ setName, inputColor = "red" }) => {

  const [address, setAddress] = useState('');

  const handleChange = ad => setAddress(ad);


  const handleSelect = async ad => {
    setAddress(ad);
    setName(ad);
  }

  return (
    <div style={styleSheet.parent}>
      <PlacesAutocomplete
        value={address}
        onChange={handleChange}
        onSelect={handleSelect}
      >
        {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
          <div style={styleSheet.container}>
            <input style={styleSheet.input(inputColor)}
              {...getInputProps({
                placeholder: 'Search Places ...',
                className: 'location-search-input, autocompleteInput',
              })}
            />
            <div style={styleSheet.dropdown} className="autocomplete-dropdown-container">
              {loading && <div>Loading...</div>}
              {suggestions.map(suggestion => {
                const className = suggestion.active
                  ? 'suggestion-item--active'
                  : 'suggestion-item';
                // inline style for demonstration purpose
                const style = suggestion.active
                  ? { backgroundColor: '#a9a9a9', cursor: 'pointer' }
                  : { backgroundColor: '#000000', cursor: 'pointer' };
                return (
                  <div style={styleSheet.dropdownElement}
                    {...getSuggestionItemProps(suggestion, {
                      className,
                      style,
                    })}
                  >
                    <span>{suggestion.description}</span>
                  </div>
                );
              })}
            </div>
          </div>
        )}
      </PlacesAutocomplete>
    </div>
  )
}

export default AutoComplete; 