import React from "react";

function Recently(props) {
  const recentlyData = JSON.parse(localStorage.getItem("recently"));
  return (
    <>
      {!recentlyData
        ? null
        : recentlyData.map((item) => {
            return <div key={item.id}>{item.title}</div>;
          })}
    </>
  );
}

export default Recently;
