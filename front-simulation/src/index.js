import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import NBodySimulation from "./App";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <NBodySimulation />
  </React.StrictMode>
);

