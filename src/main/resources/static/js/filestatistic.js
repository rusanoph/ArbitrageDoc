import { DocumentService } from "./api/DocumentService.js";
import { Util } from "./util/Util.js";

class FileStatisticPage {

    allWordChartContext = document.getElementById("all-word-chart").getContext('2d');
    lemmaChartContext = document.getElementById("lemma-chart").getContext('2d');
    randomChartContext = document.getElementById("random-chart").getContext('2d');

    allWordChartTabLink = document.getElementById("all-word-tab-link");
    lemmaChartTabLink = document.getElementById("lemma-tab-link");
    randomChartTabLink = document.getElementById("random-chart-tab-link");

    allWordChart = null;
    lemmaValidChart = null;

    constructor() {
        const allWordChartTabLink = () => {
            this.plotAllWordChart().then(chart => {
                addResetZoomButton("all-word-chart", chart);
            });

            this.allWordChartTabLink.removeEventListener('click', allWordChartTabLink);
        };
        this.allWordChartTabLink.addEventListener('click', allWordChartTabLink);


        const lemmaChartTabLink = () => {
            this.plotLemmaValidChart().then(chart => {
                addResetZoomButton("lemma-chart", chart);
            });

            

            this.lemmaChartTabLink.removeEventListener('click', lemmaChartTabLink);
        }
        this.lemmaChartTabLink.addEventListener('click', lemmaChartTabLink);
    }

    async plotAllWordChart() {
        let path = Util.getSavedValue("document-path");
        let name = Util.getSavedValue("document-name");

        let wordStatistic = await DocumentService.getDocumentText(path, name, true)
            .then((data) => DocumentService.postDocumentWordStatistic(data.text));

        let x = [];
        let y = [];

        for (let key of Object.keys(wordStatistic)) {
            x.push(key);
            y.push(wordStatistic[key]);
        }

        let arrayOfObj = x.map(function(d, i) {
            return {
                label: d,
                data: y[i] || 0
            };
        });

        let sortedArrayOfObj = arrayOfObj.sort(function(a, b) {
            return b.data - a.data;
        });

        let sortedX = [];
        let sortedY = [];

        sortedArrayOfObj.forEach(obj => {
            sortedX.push(obj.label);
            sortedY.push(obj.data);
        });

        if (this.allWordChart !== null) {
            this.allWordChart.destroy();
        }

        return createChart(sortedX, sortedY, this.allWordChartContext, "77, 147, 89");
    }

    async plotLemmaValidChart() {
        let path = Util.getSavedValue("document-path");
        let name = Util.getSavedValue("document-name");

        let validLemmas = await DocumentService.getDocumentText(path, name, true)
            .then((data) => DocumentService.postDocumentTextLemmaValid(data.text));

        let x = [];
        let y = [];

        for (let key of Object.keys(validLemmas)) {
            x.push(key);
            y.push(validLemmas[key]);
        }

        let arrayOfObj = x.map(function(d, i) {
            return {
                label: d,
                data: y[i] || 0
            };
        });

        let sortedArrayOfObj = arrayOfObj.sort(function(a, b) {
            return b.data - a.data;
        });

        let sortedX = [];
        let sortedY = [];

        sortedArrayOfObj.forEach(obj => {
            sortedX.push(obj.label);
            sortedY.push(obj.data);
        });
        
        if (this.lemmaValidChart !== null) {
            this.lemmaValidChart.destroy();
        }

        return createChart(sortedX, sortedY, this.lemmaChartContext, "255, 0, 0");
    }

    plotRandomChart() {
        // ...
    }
}



//#region On page start

const fileStatisticPage = new FileStatisticPage();
document.getElementById("document-name").innerText = Util.getSavedValue("document-name");
document.getElementById('all-word-tab-link').click();

//#endregion

function addResetZoomButton(canvasId, chartObj) {
    let resetBtn = document.createElement("div");
    resetBtn.innerHTML = "Вернуть масштаб (Alt)";
    resetBtn.classList.add("button-component");
    resetBtn.classList.add("tool");
    resetBtn.addEventListener('click', function() {
        chartObj.resetZoom();
    }); 
    document.getElementById(canvasId).insertAdjacentElement("beforebegin", resetBtn);
}

function createChart(x, y, ctx, color=null) {
    let config = {
        type: 'bar',
        data: {
            labels: x,
            datasets: [{
                data: y,
                backgroundColor: `rgba(${color}, 0.5)`,
                borderColor: `rgba(${color}, 0.9)`,
                borderWidth: 1,
                // tension: 2,
            }]
        },
        options: {
            interaction: {
                intersect: false,
                mode: "x"
            },
            animation: false,
            plugins: {
                legend: {
                    display: false
                },
                zoom: {
                    limits: {
                        y: {min: 0, minRange: 0.01}
                    },
                    pan: {
                        enabled: true,
                        mode: 'x',
                        modifierKey: 'shift'
                    },
                    zoom: {
                        wheel: {
                            enabled: true,
                        },
                        pinch: {
                            enabled: true,
                        },
                        drag: {
                            enabled: true,
                            modifierKey: 'alt',
                        },
                        mode: 'xy',
                    }
                },
            },
            scales: {
                x: {
                    ticks: {
                        maxRotation: 90,
                        minRotation: 80
                    }
                }
            }
        }
    }

    return new Chart(ctx, config);
}

//#region Random Chart
const context = document.getElementById("random-chart");
let tmp;
tmp = createChart([], [], context);

document.getElementById("random-chart-reset").addEventListener('click', () => {
    
    const min = 0;
    const max = 1000;
    
    let x = [...Array(100).keys()];
    let y = x.map((v) => Math.pow(v, 2) * Math.floor(Math.random() * (max - min) ) + min);
    
    if (tmp !== undefined) {
        tmp.destroy();
    }
    
    tmp = createChart(x, y, context);
    
});
document.getElementById("random-chart-reset").click();
//#endregion
