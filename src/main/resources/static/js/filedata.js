import { DocumentService } from "./api/DocumentService.js";
import { FileView } from "./view/fileview.js";
import { Util } from "./util/Util.js";

class FileDataPage {
    allWordsCount = 0;
    lemmaCount = 0;
    unknownCount = 0;
    moneySumCount = 0;

    fileViewClass;

    constructor() {
        this.fileViewClass = new FileView();
    }

    //#region Statistic
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

            this.allWordsCount++;
        }

        document.getElementById("all-words-count").innerHTML = this.allWordsCount;
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

            this.lemmaCount++;
        }

        document.getElementById("lemma-count").innerHTML = this.lemmaCount;

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

            this.unknownCount++;
        }

        document.getElementById("unknown-count").innerHTML = this.unknownCount;
    }
    //#endregion

    async generateMoneySumList(listId) {
        let path = Util.getSavedValue("document-path");
        let name = Util.getSavedValue("document-name");

        let moneySums = await DocumentService.getDocumentText(path, name)
            .then((data) => DocumentService.postDocumentMoneySum(data.text));

        const moneySumsListElement = document.getElementById(listId);
        for (let moneySum of moneySums) {
            let divKeyValueRow = document.createElement("div");
            divKeyValueRow.classList.add("key-value-row");
            
            let divValue = document.createElement("div");
            divValue.innerHTML = moneySum;

            divKeyValueRow.appendChild(divValue);

            moneySumsListElement.appendChild(divKeyValueRow);

            this.moneySumCount++;
        }

        document.getElementById("money-sum-count").innerHTML = this.moneySumCount;
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


const filedataPage = new FileDataPage();

document.getElementById("all-words-tab-link").addEventListener("click", function allWordStatisticEvent() {
    filedataPage.generateWordStatisticList("all-words-list");
    document.getElementById("all-words-tab-link").removeEventListener("click", allWordStatisticEvent);
});

document.getElementById("lemma-tab-link").addEventListener("click", function lemmaValidEvent() {
    filedataPage.generateLemmaValidList("lemma-list");
    document.getElementById("lemma-tab-link").removeEventListener("click", lemmaValidEvent);
});

document.getElementById("unknown-tab-link").addEventListener("click", function unknownEvent() {
    filedataPage.generateLemmaInvalidList("unknown-list");
    document.getElementById("unknown-tab-link").removeEventListener("click", unknownEvent);
});

document.getElementById("money-sum-tab-link").addEventListener("click", function moneySumEvent() {
    filedataPage.generateMoneySumList("money-sum-list");
    document.getElementById("money-sum-tab-link").removeEventListener("click", moneySumEvent);
});





