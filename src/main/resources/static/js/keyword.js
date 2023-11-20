import { DocumentService } from "./api/DocumentService.js";
import { Util } from "./util/Util.js";

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

class KeywordPage {
    documentPath;
    documentFileName;

    isFormated = false;
    isWordColor = false;

    constructor() {
        this.openButton();

        this.formatTool();
        this.wordColorTool();
    }

    openButton() {
        const openButton = document.getElementById("document-open-text");

        openButton.addEventListener('click', async () => {
            const docPathInputValue = document.getElementById("document-path").value;
            const docFileNameInputValue = document.getElementById("document-name").value;

            this.documentPath = docPathInputValue;
            this.documentFileName = docFileNameInputValue;

            DocumentService.getDocumentText(docPathInputValue, docFileNameInputValue, this.isFormated)
            .then((data) => {
                const docFileName = document.getElementById("document-filename");
                const docText = document.getElementById("document-text");
            
                docFileName.innerHTML = `Имя файла - ${docPathInputValue}/${docFileNameInputValue}`;
                
                if (data.error === undefined) {
                    docText.innerHTML = data.text;
                } else {
                    docText.innerHTML = data.error;
                }
            });
        });
    }

    formatTool() {
        const formatTool = document.getElementById("format-tool");
        formatTool.addEventListener('click', async () => {
            this.isFormated = !this.isFormated;

            DocumentService.getDocumentText(this.documentPath, this.documentFileName, this.isFormated)
            .then((data) => {
                const docFileName = document.getElementById("document-filename");
                const docText = document.getElementById("document-text");
            
                docFileName.innerHTML = `Имя файла - ${this.documentPath}/${this.documentFileName}`;
                
                if (data.error === undefined) {
                    docText.innerHTML = data.text;
                } else {
                    docText.innerHTML = data.error;
                }
            });
            

            formatTool.classList.toggle("active-tool");
        });
    }

    wordColorTool() {
        const wordColorTool = document.getElementById("word-color-tool");
        wordColorTool.addEventListener('click', async () => {
            this.isWordColor = !this.isWordColor;


            wordColorTool.classList.toggle("active-tool");
        });
    }

    async generateKeyWordList(keywordListId) {
        let path = Util.getSavedValue("document-path");
        let name = Util.getSavedValue("document-name");

        let keywords = await DocumentService.getDocumentText(path, name, true)
            .then((data) => DocumentService.getDocumentWordStatistic(data.text));
    
        const keywordListElement = document.getElementById(keywordListId);
        for (let keyword of Object.keys(keywords)) {
            let li = document.createElement("li");
            li.innerHTML = `'${keyword}': ${keywords[keyword]}`;
            keywordListElement.appendChild(li);
        }
    }
}

//#region Save input docPath, docFileName after reload

document.getElementById("document-path").value = Util.getSavedValue("document-path");
document.getElementById("document-name").value = Util.getSavedValue("document-name");

document.getElementById("document-path").addEventListener('keyup', function () {
    Util.saveValue(this);
})
document.getElementById("document-name").addEventListener('keyup', function () {
    Util.saveValue(this);
})

//#endregion



const keywordPage = new KeywordPage();
keywordPage.generateKeyWordList("key-word-list");