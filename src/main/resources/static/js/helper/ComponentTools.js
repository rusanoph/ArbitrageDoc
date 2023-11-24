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
}