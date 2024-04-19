import React from 'react';
import styled from 'styled-components';

const DL = styled.dl`
    display: table-row;
`;
const DT = styled.dt`
    display: table-cell;
    vertical-align: top;
    width: 100px;
    padding: 12px 0;
    color: #666;
`;
const DD = styled.dd`
    display: table-cell;
    position: relative;
    vertical-align: top;
    padding: 12px 0;
    color: #333;
`;

function DescriptionList({contents}) {
    return (
        <DL>
            <DT>{contents.dt}</DT>
            <DD>{contents.dd}</DD>
        </DL>
    );
}

export default DescriptionList;