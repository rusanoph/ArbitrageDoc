import { DocumentService } from "./api/DocumentService.js";
import { FileView } from "./fileview.js";
import { ComponentTools } from "./helper/ComponentTools.js";
import { Util } from "./util/Util.js";

class MainPageDocument {
    documentCount = 0;

    isFormated = false;
    isLemma = false;

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

                ComponentTools.updateDocumentView("document-filename", "document-title", "document-text", currDir, file, this.isFormated, this.isLemma)
                .then(() => {
                    if (this.fileViewClass.isRegEx) {
                        document.getElementById("regex-input").dispatchEvent(new Event('input'));
                    }
                });

                await DocumentService.getDocumentStructureParts(currDir, file)
                .then((data) => {
                    this.fileViewClass.documentPartsObject = data;
                });

                let structureData = this.fileViewClass.getMainStructureData();
                ComponentTools.updateDocumentStructureView(
                    "document-header-text", 
                    "document-found-text", 
                    "document-determined-text", 
                    "document-decided-text", 
                    "document-solution-text", 
                    structureData
                );

                let complainantAndDefendantData = this.fileViewClass.getComplainantAndDefendantData();
                ComponentTools.updateComplainantAndDefendantView("document-complainant-defendant-text", complainantAndDefendantData);

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

/*
--- main structure ---


--- subject structure ---

*/


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


