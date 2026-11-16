import React from "react";


const CopyRight = () => {
  return (
    <div>
      <p style={{textAlign: 'center', fontSize: '11px', backgroundColor: 'var(--primary)', color: 'var(--body)'}}>&copy; {new Date().getFullYear()} | CloudCart | All Rights Reserved</p>
    </div>
  );
}

export default CopyRight;
