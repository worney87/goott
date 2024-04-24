import React from 'react';
import { Link } from 'react-router-dom';

function Header(props) {
    return (
        <div>
            <Link to={'/'}>홈</Link>&nbsp;&nbsp; | &nbsp;&nbsp; 
            <Link to='/post'>게시판</Link>
            <hr/>
        </div>
    );
}

export default Header;