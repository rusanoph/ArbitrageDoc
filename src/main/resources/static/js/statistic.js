
function createLineChart(x, y, ctx, color=null) {
    let config = {
        type: 'line',
        data: {
            labels: x,
            datasets: [{
                data: y,
                backgroundColor: `rgba(${color}, 0.5)`,
                borderColor: `rgba(${color}, 0.9)`,
                borderWidth: 2,
                // tension: 2,
            }]
        },
        options: {
            animation: false,
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    }

    new Chart(ctx, config);
}


let Y = Array.from({length: 400}, () => Math.floor(Math.random() * 1000)).sort();
X = [...Y.keys()];


const testChartContext = document.getElementById("test-chart");
createLineChart(X, Y, testChartContext, "255, 109, 122");