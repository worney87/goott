import React from 'react';
import { useParams } from 'react-router-dom';

function Detail({data}) {

    const {pId} = useParams();
    console.log(pId)

    const product = data.filter(obj => obj.id === parseInt(pId));

    return (
        <div>
        </div>
    );
}

export default Detail;