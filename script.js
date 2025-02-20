const canvas = document.getElementById("simulationCanvas");
const ctx = canvas.getContext("2d");

async function fetchBodies() {
    const res = await fetch("http://localhost:8080/api/simulation/step?dt=10000");
    return await res.json();
}

function draw(bodies) {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    bodies.forEach(body => {
        const x = (body.x / 1e11) * 400 + 400; // Normalisation
        const y = (body.y / 1e11) * 300 + 300;
        ctx.beginPath();
        ctx.arc(x, y, 5, 0, Math.PI * 2);
        ctx.fillStyle = "white";
        ctx.fill();
    });
}

async function update() {
    const bodies = await fetchBodies();
    draw(bodies);
    requestAnimationFrame(update);
}

update();
