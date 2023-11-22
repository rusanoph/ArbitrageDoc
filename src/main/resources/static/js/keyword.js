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
    isLemma = false;

    constructor() {
        this.openButton();

        this.formatTool();
        this.wordColorTool();
        this.lemmatizationTool();

        this.fontTool();
        this.lineHeightTool();
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

    //#region Special tools
    formatTool() {
        const formatTool = document.getElementById("format-tool");
        formatTool.addEventListener('click', async () => {
            this.isFormated = !this.isFormated;

            DocumentService.getDocumentText(this.documentPath, this.documentFileName, this.isFormated, this.isLemma)
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

    // Didn't work
    wordColorTool() {
        const wordColorTool = document.getElementById("word-color-tool");
        
        const spanHighlight = function() {
            let textElement = document.getElementById("document-text");
            let words = textElement.innerText.split(' ');

            textElement.innerHTML = "";
            for (let word of words) {
                const span = document.createElement("span");
                span.textContent = word + ' ';
                textElement.appendChild(span);
            }

            textElement.addEventListener('mouseover', highlightWords);
            textElement.addEventListener('mouseout', unhighlightWords);
        }

        const highlightWords = function(event) {
            let target = event.target;

            if (target.tagName === "SPAN") {
                target.classList.add('highlight-text');
            }
        }

        const unhighlightWords = function(event) {
            let target = event.target;

            if (target.tagName === "SPAN") {
                target.classList.remove('highlight-text');
            }
        }


        wordColorTool.addEventListener('click', async () => {
            this.isWordColor = !this.isWordColor;

            if (this.isWordColor) {
                spanHighlight();
            } 

            wordColorTool.classList.toggle("active-tool");
        });
    }

    lemmatizationTool() {
        const lemmatizationTool = document.getElementById("lemma-tool");

        lemmatizationTool.addEventListener('click', async () => {
            this.isLemma = !this.isLemma;
            
            DocumentService.getDocumentText(this.documentPath, this.documentFileName, this.isFormated, this.isLemma)
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

            lemmatizationTool.classList.toggle("active-tool");
        });


    }
    //#endregion

    //#region Default tools
    fontTool() {
        const fontSizeSelect = document.getElementById("document-font-size");

        fontSizeSelect.addEventListener('change', () => {
            const docTextHTML = document.getElementById("document-text");
            const lineHeightSelect = document.getElementById("document-line-height");

            if (parseInt(lineHeightSelect.value, 10) < parseInt(fontSizeSelect.value, 10)) {
                lineHeightSelect.value = fontSizeSelect.value; 
                docTextHTML.style.lineHeight = fontSizeSelect.value; 
            }

            docTextHTML.style.fontSize = fontSizeSelect.value;
        });
    }

    lineHeightTool() {
        const lineHeightSelect = document.getElementById("document-line-height");

        lineHeightSelect.addEventListener('change', () => {
            const docTextHTML = document.getElementById("document-text");
            docTextHTML.style.lineHeight = lineHeightSelect.value;
        });
    }
    //#endregion

    async generateWordStatisticList(keywordListId) {
        let path = Util.getSavedValue("document-path");
        let name = Util.getSavedValue("document-name");

        let keywords = await DocumentService.getDocumentText(path, name, true)
            .then((data) => DocumentService.postDocumentWordStatistic(data.text));
    
        const keywordListElement = document.getElementById(keywordListId);
        for (let keyword of Object.keys(keywords)) {
            let divKeyValueRow = document.createElement("div");
            divKeyValueRow.classList.add("key-value-row");
            
            let divKey = document.createElement("div");
            divKey.innerHTML = `'${keyword}'`;
            divKey.classList.add("key");

            let divValue = document.createElement("div");
            divValue.innerHTML = keywords[keyword];
            divValue.classList.add("value");

            divKeyValueRow.appendChild(divKey);
            divKeyValueRow.appendChild(divValue);

            keywordListElement.appendChild(divKeyValueRow);
        }
    }

    async generateLemmaValidList(listId) {
        let path = Util.getSavedValue("document-path");
        let name = Util.getSavedValue("document-name");

        let validLemmas = await DocumentService.getDocumentText(path, name, true)
            .then((data) => DocumentService.postDocumentTextLemmaValid(data.text));

        const validLemmasListElement = document.getElementById(listId);
        for (let lemma of Object.keys(validLemmas)) {
            let divKeyValueRow = document.createElement("div");
            divKeyValueRow.classList.add("key-value-row");
            
            let divKey = document.createElement("div");
            divKey.innerHTML = `'${lemma}'`;
            divKey.classList.add("key");

            let divValue = document.createElement("div");
            divValue.innerHTML = validLemmas[lemma];
            divValue.classList.add("value");

            divKeyValueRow.appendChild(divKey);
            divKeyValueRow.appendChild(divValue);

            validLemmasListElement.appendChild(divKeyValueRow);
        }
    }

    async generateLemmaInvalidList(listId) {
        let path = Util.getSavedValue("document-path");
        let name = Util.getSavedValue("document-name");

        let validLemmas = await DocumentService.getDocumentText(path, name, true)
            .then((data) => DocumentService.postDocumentTextLemmaInvalid(data.text));

        const validLemmasListElement = document.getElementById(listId);
        for (let lemma of Object.keys(validLemmas)) {
            let divKeyValueRow = document.createElement("div");
            divKeyValueRow.classList.add("key-value-row");
            
            let divKey = document.createElement("div");
            divKey.innerHTML = `'${lemma}'`;
            divKey.classList.add("key");

            let divValue = document.createElement("div");
            divValue.innerHTML = validLemmas[lemma];
            divValue.classList.add("value");

            divKeyValueRow.appendChild(divKey);
            divKeyValueRow.appendChild(divValue);

            validLemmasListElement.appendChild(divKeyValueRow);
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
keywordPage.generateWordStatisticList("all-words-list");
keywordPage.generateLemmaValidList("lemma-list");
keywordPage.generateLemmaInvalidList("unknown-list");