var canvas = document.getElementsByTagName("canvas")[0];
var context = canvas.getContext("2d");
var height = canvas.height = window.innerHeight;
var width = canvas.width = window.innerWidth;
var mouseClicked = false, mouseReleased = true;
document.addEventListener("click", onMouseClick, false);
document.addEventListener("mousemove", onMouseMove, false);
function onMouseClick(e) {
    mouseClicked = !mouseClicked;
}
function onMouseMove(e) {
    if (mouseClicked) {
        context.beginPath();
        context.arc(e.clientX, e.clientY,1, 0, Math.PI * 2, false);
        context.lineWidth = 1;
        context.strokeStyle = "#fff";
        context.stroke();
    }
}

