import { DocumentService } from "./api/DocumentService.js";
import { ComponentTools } from "./helper/ComponentTools.js";
import { Util } from "./util/Util.js";

class MainPageDocument {
    documentPath;
    documentFileName;

    documentCount = 0;

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

            ComponentTools.updateDocumentView("document-filename", "document-title", "document-text", docPathInputValue, docFileNameInputValue, this.isFormated);
        });
    }

    formatTool() {
        const formatTool = document.getElementById("format-tool");
        formatTool.addEventListener('click', async () => {
            this.isFormated = !this.isFormated;

            ComponentTools.updateDocumentView("document-filename", "document-title", "document-text", this.documentPath, this.documentFileName, this.isFormated);

            formatTool.classList.toggle("active-tool");
        });
    }

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
            
            ComponentTools.updateDocumentView("document-filename", "document-title", "document-text", this.documentPath, this.documentFileName, this.isFormated, this.isLemma);

            lemmatizationTool.classList.toggle("active-tool");
        });


    }

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

    async generateDocumentList(elementId, currDir=null) {
        if (currDir === null) {
            currDir = "";
        }

        const documentListElement = document.getElementById(elementId);

        // Append Dirs
        let dirList = await DocumentService.getListOfDirectories(currDir);
        for (let dir of dirList) {
            let li = document.createElement("li");

            let span = document.createElement("span");
            span.classList.add("caret");
            span.innerHTML = dir;
            span.path = `${currDir}/${dir}` 

            const obj = this;
            const recursionGenerateDocs = async function() {
                const ul = document.createElement("ul");
                ul.classList.add("nested-tree");
                ul.id = `ul-${span.path}`;
                span.insertAdjacentElement("afterend", ul);
                
                treeViewToggler();
                
                await obj.generateDocumentList(ul.id, span.path)
                .then(() => {
                    span.removeEventListener('click', recursionGenerateDocs, false);
                });
                
                span.click();
            }

            span.addEventListener('click', recursionGenerateDocs);

            li.appendChild(span);
            documentListElement.appendChild(li);
        }


        // Append Files
        let fileList = await DocumentService.getListOfFiles(currDir);
        for (let file of fileList) {
            let li = document.createElement("li");

            li.innerHTML = file;

            li.addEventListener('click', () => {
                this.documentPath = currDir;
                this.documentFileName = file;

                document.getElementById("document-path").value = currDir;
                document.getElementById("document-name").value = file;

                Util.saveValue(document.getElementById("document-path"));
                Util.saveValue(document.getElementById("document-name"));

                ComponentTools.updateDocumentView("document-filename", "document-title", "document-text", currDir, file, this.isFormated);

                let previouslySelected = document.querySelector('.selected');
                if (previouslySelected) {
                    previouslySelected.classList.remove('selected');
                }

                li.classList.add('selected');
            });

            documentListElement.appendChild(li);
            this.documentCount++;
        }

        document.getElementById("doc-list-count").innerHTML = this.documentCount;
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


let mainPageDocument = new MainPageDocument(); 
mainPageDocument.generateDocumentList("document-list-tree");


