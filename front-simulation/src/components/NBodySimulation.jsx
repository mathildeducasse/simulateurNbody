import { useState, useEffect, useRef } from "react";

const SCALE = 1e-9;
const WIDTH = 800;
const HEIGHT = 600;

export default function NBodySimulation() {
    const [bodies, setBodies] = useState([]);
    const [dt, setDt] = useState(10000);
    const [newBody, setNewBody] = useState({ x: 0, y: 0, vx: 0, vy: 0, mass: 1 });
    const canvasRef = useRef(null);

    useEffect(() => {
        const socket = new WebSocket("ws://localhost:8080/nbody");

        socket.onopen = () => {
            console.log("WebSocket connection established");
        };

        socket.onmessage = (event) => {
            const data = JSON.parse(event.data);
            setBodies(data);
        };

        return () => {
            socket.close();
        };
    }, []);

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

    const handleAddBody = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/simulation/add", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(newBody),
            });

            if (!response.ok) throw new Error("Failed to add body");

            setNewBody({ x: 0, y: 0, vx: 0, vy: 0, mass: 1 });
        } catch (error) {
            console.error("Error adding body:", error);
        }
    };

    return (
        <div style={{ textAlign: "center", padding: "20px", background: "#222" }}>
            <h1 style={{ color: "white" }}>N-Body Simulation (WebSocket)</h1>

            <canvas
                ref={canvasRef}
                width={WIDTH}
                height={HEIGHT}
                style={{ border: "1px solid white" }}
            />

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
                <h2>Add Body</h2>
                <label>X: <input type="number" value={newBody.x} onChange={(e) => setNewBody({ ...newBody, x: parseFloat(e.target.value) })} /></label>
                <label>Y: <input type="number" value={newBody.y} onChange={(e) => setNewBody({ ...newBody, y: parseFloat(e.target.value) })} /></label>
                <label>VX: <input type="number" value={newBody.vx} onChange={(e) => setNewBody({ ...newBody, vx: parseFloat(e.target.value) })} /></label>
                <label>VY: <input type="number" value={newBody.vy} onChange={(e) => setNewBody({ ...newBody, vy: parseFloat(e.target.value) })} /></label>
                <label>Mass: <input type="number" value={newBody.mass} onChange={(e) => setNewBody({ ...newBody, mass: parseFloat(e.target.value) })} /></label>
                <button onClick={handleAddBody} style={{ marginLeft: "10px" }}>Add Body</button>
            </div>
        </div>
    );
}
