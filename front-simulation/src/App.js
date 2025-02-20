import { useState, useEffect, useRef } from "react";

const SCALE = 1e-9;
const WIDTH = 800;
const HEIGHT = 600;

export default function NBodySimulation() {
  const [bodies, setBodies] = useState([]);
  const [dt, setDt] = useState(10000);
  const canvasRef = useRef(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/simulation/step?dt=${dt}`);
        if (!response.ok) throw new Error("Failed to fetch simulation data");
        const data = await response.json();
        setBodies(data);
      } catch (error) {
        console.error(error);
      }
    };

    const interval = setInterval(fetchData, 100);
    return () => clearInterval(interval);
  }, [dt]);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext("2d");
    if (!ctx) return;

    ctx.clearRect(0, 0, WIDTH, HEIGHT);
    ctx.fillStyle = "black";
    ctx.fillRect(0, 0, WIDTH, HEIGHT);

    bodies.forEach((body, index) => {
      const x = WIDTH / 2 + body.x * SCALE;
      const y = HEIGHT / 2 + body.y * SCALE;
      ctx.beginPath();
      ctx.arc(x, y, body.mass > 1e30 ? 10 : 5, 0, 2 * Math.PI);
      ctx.fillStyle = index === 0 ? "yellow" : "blue";
      ctx.fill();
    });
  }, [bodies]);

  return (
      <div style={{ textAlign: "center", padding: "20px", background: "#222" }}>
        <h1 style={{ color: "white" }}>N-Body Simulation</h1>
        <canvas ref={canvasRef} width={WIDTH} height={HEIGHT} style={{ border: "1px solid white" }} />
        <div style={{ marginTop: "20px" }}>
          <label style={{ color: "white" }}>Simulation Speed: {dt}</label>
          <input
              type="range"
              min="0"
              max="100000"
              step="5000"
              value={dt}
              onChange={(e) => setDt(Number(e.target.value))}
              style={{ width: "300px", marginLeft: "10px" }}
          />
        </div>
      </div>
  );
}
