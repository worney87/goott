import React from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import DetailHead from './DetailHead';
import DetailBody from './DetailBody';

const DetailWrapper = styled.div`
    margin : 60px auto 0;
    max-width : 1200px;
    width : 80%;
`;

function Detail({data}) {

    const {pId} = useParams();

    
    //const product = data.filter(obj => obj.id === parseInt(pId));
    const product = data.find(obj => obj.id === parseInt(pId));
    
    return (
        <DetailWrapper>
            <DetailHead data={product}/>
            <DetailBody detail={product.detail}/>
        </DetailWrapper>
    );
}

export default Detail;