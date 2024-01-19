import { DocumentService } from "./api/DocumentService.js";
import { FileView } from "./view/fileview.js";
import { ComponentTools } from "./helper/ComponentTools.js";
import { Util } from "./util/Util.js";

class ParserPageDocument {
    documentCount = 0;

    isFormated = false;
    isLemma = false;
    isParsed = false;

    fileViewClass;

    constructor() {
        this.fileViewClass = new FileView();
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

            const recursionGenerateDocs = async () => {
                const ul = document.createElement("ul");
                ul.classList.add("nested-tree");
                ul.id = `ul-${span.path}`;
                span.insertAdjacentElement("afterend", ul);
                
                treeViewToggler();
                
                await this.generateDocumentList(ul.id, span.path)
                .then(() => {
                        span.removeEventListener('click', recursionGenerateDocs);
                    });
                    
                    span.click();
            };
            
            span.addEventListener('click', recursionGenerateDocs, { once: true });
            
            li.appendChild(span);
            documentListElement.appendChild(li);
        }


        // Append Files
        let fileList = await DocumentService.getListOfFiles(currDir);
        for (let file of fileList) {
            let li = document.createElement("li");

            li.innerHTML = file;

            li.addEventListener('click', async () => {
                this.fileViewClass.documentPath = currDir;
                this.fileViewClass.documentFileName = file;

                document.getElementById("document-path").value = currDir;
                document.getElementById("document-name").value = file;

                Util.saveValue(document.getElementById("document-path"));
                Util.saveValue(document.getElementById("document-name"));

                this.isParsed = true;
                ComponentTools.updateDocumentView("document-filename", "document-title", "document-text", currDir, file, this.isFormated, this.isLemma, this.isParsed)
                .then(() => {
                    if (this.fileViewClass.isRegEx) {
                        document.getElementById("regex-input").dispatchEvent(new Event('input'));
                    }
                });

                let previouslySelected = document.querySelector('.selected');
                if (previouslySelected) {
                    previouslySelected.classList.remove('selected');
                }
                li.classList.add('selected');
            });

            documentListElement.appendChild(li);
            
            this.documentCount++;
        }

        if (this.documentCount !== 0) {
            document.getElementById("doc-list-count").innerHTML = this.documentCount;
        }
    }
}

//#region Save input docPath, docFileName after reload

const documentPathElement = document.getElementById("document-path");
const documentNameElement = document.getElementById("document-name");

if (documentNameElement !== null && documentPathElement !== null) {
    documentPathElement.value = Util.getSavedValue("document-path");
    documentNameElement.value = Util.getSavedValue("document-name");

    documentPathElement.addEventListener('keyup', function () {
        Util.saveValue(this);
    })
    documentNameElement.addEventListener('keyup', function () {
        Util.saveValue(this);
    })
}

//#endregion

const doucmentListTreeElement = document.getElementById("document-list-tree");
if (doucmentListTreeElement !== null) {
    let parsePageDocument = new ParserPageDocument(); 
    parsePageDocument.generateDocumentList("document-list-tree");
}




