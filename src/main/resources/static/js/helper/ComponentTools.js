import { DocumentService } from "./../api/DocumentService.js";

export class ComponentTools {    

    static async updateDocumentView(docFileNameId, docTitleId, docTextId, documentPath, documentFileName, isFormated, isLemma) {

        await DocumentService.getDocumentText(documentPath, documentFileName, isFormated, isLemma)
        .then((data) => {
            const docFileName = document.getElementById(docFileNameId);
            const docTitle = document.getElementById(docTitleId);
            const docText = document.getElementById(docTextId);
        
            docFileName.innerHTML = `Файл: ${documentPath}/${documentFileName}`;
            
            if (data.error === undefined) {
                docTitle.innerHTML = `Заголовок: ${data.title}`;
                docText.innerHTML = data.text;
            } else {
                docTitle.innerHTML = "Заголовок: Ошибка";
                docText.innerHTML = data.error;
            }
        });

    }

    static async updateDocumentStructureView(headerId, foundId, determinedId, decidedId, solutionId, structureObj) {
        const headerElement = document.getElementById(headerId);
        const foundElement = document.getElementById(foundId);
        const determinedElement = document.getElementById(determinedId);
        const decidedElement = document.getElementById(decidedId);
        const solutionElement = document.getElementById(solutionId);

        this.showAccordionIfCondition(headerId, structureObj.header.length > 0);
        this.showAccordionIfCondition(foundId, structureObj.found.length > 0);
        this.showAccordionIfCondition(determinedId, structureObj.determined.length > 0);
        this.showAccordionIfCondition(decidedId, structureObj.decided.length > 0);
        this.showAccordionIfCondition(solutionId, structureObj.solution.length > 0);

        headerElement.innerHTML = structureObj.header;
        foundElement.innerHTML = structureObj.found;
        determinedElement.innerHTML = structureObj.determined;
        decidedElement.innerHTML = structureObj.decided;
        solutionElement.innerHTML = structureObj.solution;
    }

    static async updateComplainantAndDefendantView(complainantDefendantRegexId, complainantDefendantTreeId, complainantDefendantGraphId, structureObj) {
        const complainantDefendantRegexElement = document.getElementById(complainantDefendantRegexId);
        complainantDefendantRegexElement.innerHTML = structureObj.complainantAndDefendantRegex;

        const complainantDefendantTreeElement = document.getElementById(complainantDefendantTreeId);
        complainantDefendantTreeElement.innerHTML = structureObj.complainantAndDefendantTree;

        const complainantDefendantGraphElement = document.getElementById(complainantDefendantGraphId);
        complainantDefendantGraphElement.innerHTML = structureObj.complainantAndDefendantGraph;
    }

    static showAccordionIfCondition(elementId, condition) {
        const element = document.getElementById(elementId);
        const accPanel = element.parentElement;
        const accTab = accPanel.previousElementSibling;

        if (!condition) {
            element.style.display = "none";
            accPanel.style.display = "none";
            accTab.style.display = "none";
        } else {
            element.style.display = "block";
        }
    }
}