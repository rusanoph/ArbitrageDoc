import { DocumentService } from "./api/DocumentService.js";
import { Util } from "./util/Util.js";

class MainPageDocument {
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

                DocumentService.getDocumentText(currDir, file, this.isFormated)
                .then((data) => {
                    const docFileName = document.getElementById("document-filename");
                    const docText = document.getElementById("document-text");
                
                    docFileName.innerHTML = `Имя файла - ${currDir}/${file}`;
                    
                    if (data.error === undefined) {
                        docText.innerHTML = data.text;
                    } else {
                        docText.innerHTML = data.error;
                    }
                });;

                let previouslySelected = document.querySelector('.selected');
                if (previouslySelected) {
                    previouslySelected.classList.remove('selected');
                }

                li.classList.add('selected');
            });

            documentListElement.appendChild(li);
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


let mainPageDocument = new MainPageDocument(); 
mainPageDocument.generateDocumentList("document-list-tree");

