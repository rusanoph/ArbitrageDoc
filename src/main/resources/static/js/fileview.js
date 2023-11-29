
import { ComponentTools } from "./helper/ComponentTools.js";
import { Util } from "./util/Util.js";

export class FileView {
    isFormated = false;
    isLemma = false;
    isRegEx = false;

    constructor() {
        document.getElementById("regex-input").value = Util.getSavedValue("regex-input");
        document.getElementById("regex-input").addEventListener('keyup', function () {
            Util.saveValue(this);
        })

        this.openButton();

        this.formatTool();
        this.lemmatizationTool();
        this.regexTool();

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

            ComponentTools.updateDocumentView("document-filename", "document-title", "document-text", docPathInputValue, docFileNameInputValue, this.isFormated, this.isLemma)
            .then(() => {
                if (this.isRegEx) {
                    document.getElementById("regex-input").dispatchEvent(new Event('click'));
                }
            });
        });
    }

    //#region Special Tools
    formatTool() {
        const formatTool = document.getElementById("format-tool");
        formatTool.addEventListener('click', async () => {
            this.isFormated = !this.isFormated;

            ComponentTools.updateDocumentView("document-filename", "document-title", "document-text", this.documentPath, this.documentFileName, this.isFormated, this.isLemma);

            formatTool.classList.toggle("active-tool");
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

    regexTool() {
        if (this.isRegEx) {
            document.getElementById("regex-input-component").style.display = "block";
        } else {
            document.getElementById("regex-input-component").style.display = "none";
        }

        const regexTool = document.getElementById("regex-tool");
        const regexInput = document.getElementById("regex-input");

        const text = document.getElementById("document-text");

        const regexParam = {
            "global": document.getElementById("regex-param-global"),
            "caseInsensitive": document.getElementById("regex-param-case-insensitive"),
            "multiline": document.getElementById("regex-param-multiline"),
            "dotall": document.getElementById("regex-param-dotall"),
        };

        regexTool.addEventListener('click', () => {
            this.isRegEx = !this.isRegEx;
            regexTool.classList.toggle("active-tool");

            if (this.isRegEx) {
                document.getElementById("regex-input-component").style.display = "flex";
            } else {
                document.getElementById("regex-input-component").style.display = "none";
            }
        });

        for (let param of Object.keys(regexParam)) {
            regexParam[param].addEventListener("change", () => {
                regexInput.dispatchEvent(new Event('input'));
            });
        }

        const regexEvenets = ["input", "click"];

        regexEvenets.forEach(function(regexEvent) {
            regexInput.addEventListener(regexEvent, () => {
                let regexInputValue = regexInput.value;
    
                let flags = "";
                if (regexParam.global.checked) {
                    flags += "g";
                }
                if (regexParam.caseInsensitive.checked) {
                    flags += "i";
                }
                if (regexParam.multiline.checked) {
                    flags += "m";
                }
                if (regexParam.dotall.checked) {
                    flags += "s";
                }
    
                const regexIsValid = document.getElementById("regex-is-valid");
                let regex;
                try {
                    regex = new RegExp(`(${regexInputValue})`, flags);
                    regexIsValid.innerText = "";
                } catch(e) {
                    regexIsValid.innerText = "Не верный синтаксис регулярного выражения";
                    return;
                }
                
                if (regex.source === "()") {
                    text.innerHTML = text.innerText;
                    return;
                }

                let newText;
                if (regexParam.global.checked) {
                    newText = text.innerText.replaceAll(regex, "<span class='highlight'>$1</span>");
                } else {
                    newText = text.innerText.replace(regex, "<span class='highlight'>$1</span>");
                }
    
                text.innerHTML = newText;
            });
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

}