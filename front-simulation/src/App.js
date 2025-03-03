import { useState, useEffect, useRef } from "react";

const SCALE = 1e-9;
const WIDTH = 800;
const HEIGHT = 600;

export default function NBodySimulation() {
  const [bodies, setBodies] = useState([]);
  const [dt, setDt] = useState(10000);
  const [newBody, setNewBody] = useState({ x: 0, y: 0, vx: 0, vy: 0, mass: 1e30 });
  const canvasRef = useRef(null);

  // Récupère les données de la simulation
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

  // Dessine les corps sur le canvas
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

  // Gère l'ajout d'un nouveau corps
  const handleAddBody = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/simulation/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newBody),
      });
      if (!response.ok) throw new Error("Failed to add new body");
      setNewBody({ x: 0, y: 0, vx: 0, vy: 0, mass: 1e30 }); // Réinitialise le formulaire
    } catch (error) {
      console.error(error);
    }
  };

  // Gère les changements dans le formulaire
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewBody((prev) => ({ ...prev, [name]: parseFloat(value) }));
  };

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

        <div style={{ marginTop: "20px", color: "white" }}>
          <h2>Ajouter un nouveau corps</h2>
          <input
              type="number"
              name="x"
              placeholder="x"
              value={newBody.x}
              onChange={handleInputChange}
          />
          <input
              type="number"
              name="y"
              placeholder="y"
              value={newBody.y}
              onChange={handleInputChange}
          />
          <input
              type="number"
              name="vx"
              placeholder="vx"
              value={newBody.vx}
              onChange={handleInputChange}
          />
          <input
              type="number"
              name="vy"
              placeholder="vy"
              value={newBody.vy}
              onChange={handleInputChange}
          />
          <input
              type="number"
              name="mass"
              placeholder="mass"
              value={newBody.mass}
              onChange={handleInputChange}
          />
          <button onClick={handleAddBody} style={{ marginLeft: "10px" }}>Ajouter</button>
        </div>
      </div>
  );
}
