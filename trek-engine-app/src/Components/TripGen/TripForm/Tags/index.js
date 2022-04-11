// Written by Gavri Kepets
import React, { useEffect } from 'react';
import { useRef, useState } from 'react';
import { WithContext as ReactTags } from 'react-tag-input';

const styleSheet = {

}

const Tags = ({ tags, setTags }) => {
    const handleDelete = i => setTags(tags.filter((requirement, index) => index !== i));

    const handleAddition = requirement => {
        setTags([...tags, requirement]);
    };

    return (
        // TODO: Write this from scratch using the autocomplete component
        <ReactTags
            tags={tags}
            delimiters={[188, 13]}
            handleDelete={handleDelete}
            handleAddition={handleAddition}
            inputFieldPosition="bottom"
            allowDragDrop={false}
            placeholder="Press enter to add!"
        />
    )
}

export default Tags;