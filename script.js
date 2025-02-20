const canvas = document.getElementById("canvas");
const ctx = canvas.getContext("2d");
canvas.width = window.innerWidth * 0.8;
canvas.height = window.innerHeight * 0.8;

async function fetchData() {
    try {
        const response = await fetch("http://localhost:8080/api/simulation/step?dt=10000");
        const data = await response.json();
        drawPlanets(data);
    } catch (error) {
        console.error("Erreur lors de la récupération des données:", error);
    }
}

function drawPlanets(planets) {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    planets.forEach(planet => {
        const x = (planet.x * 50) + canvas.width / 2; // Échelle
        const y = (planet.y * 50) + canvas.height / 2;
        ctx.beginPath();
        ctx.arc(x, y, 5, 0, Math.PI * 2);
        ctx.fillStyle = "white";
        ctx.fill();
        ctx.closePath();
    });
}

setInterval(fetchData, 100); // Rafraîchit la simulation toutes les 100ms