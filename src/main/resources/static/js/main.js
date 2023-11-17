

//#region Save input docPath, docFileName after reload

document.getElementById("document-path").value = getSavedValue("document-path");
document.getElementById("document-name").value = getSavedValue("document-name");

function saveValue(e) {
    let id = e.id;
    let value = e.value;
    localStorage.setItem(id, value);
}

function getSavedValue(v) {
    if (!localStorage.getItem(v)) {
        return "";
    }
    return localStorage.getItem(v);
}

//#endregion


//#region Load Document Text
async function getDocumentText(docFileNameId, docTextId, path, filename) {
    let params = {
        "documentPath": path,
        "documentFileName": filename,
    }

    let query = Object.keys(params)
        .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
        .join("&");

    let url = `/api/document/text?${query}`;

    const response = await fetch(url);
    let data = await response.json();

    const docFileName = document.getElementById(docFileNameId);
    const docText = document.getElementById(docTextId);

    docFileName.innerHTML = `Имя файла - ${await data.filename}`;
    docText.innerHTML = await data.text;
} 

const openButton = document.getElementById("document-open-text");

openButton.addEventListener('click', async () => {
    const docPathInputValue = document.getElementById("document-path").value;
    const docFileNameInputValue = document.getElementById("document-name").value;

    getDocumentText("document-filename", "document-text", docPathInputValue, docFileNameInputValue);
});
//#endregion


//#region Get List of Files 

async function getListOfFiles(path=null) {
    if (path === null) {
        path = "";
    }

    let params = {
        "directoryPath": path,
    }

    let query = Object.keys(params)
        .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
        .join("&");

    let url = `/api/document/list/doc?${query}`;

    const response = await fetch(url);
    let data = await response.json();

    return data;
}

async function getListOfDirectories(path=null) {

    if (path === null) {
        path = "";
    }

    let params = {
        "directoryPath": path,
    }

    let query = Object.keys(params)
        .map(k => `${encodeURIComponent(k)}=${encodeURIComponent(params[k])}`)
        .join("&");

    let url = `/api/document/list/dir?${query}`;

    const response = await fetch(url);
    let data = await response.json();

    return data;
}

async function generateDocumentList(elementId, currDir=null) {
    if (currDir === null) {
        currDir = "";
    }

    const documentListElement = document.getElementById(elementId);

    // Append Dirs
    let dirList = await getListOfDirectories(currDir);
    for (let dir of dirList) {
        let li = document.createElement("li");

        let span = document.createElement("span");
        span.classList.add("caret");
        span.innerHTML = dir;
        span.path = `${currDir}/${dir}` 

        span.addEventListener('click', function recursionGenerateDocs() {
            const ul = document.createElement("ul");
            ul.classList.add("nested-tree");
            ul.id = `ul-${span.path}`;
            span.insertAdjacentElement("afterend", ul);
            
            treeViewToggler();
            generateDocumentList(ul.id, this.path);
            span.removeEventListener('click', recursionGenerateDocs);
            this.click();
        });

        li.appendChild(span);
        documentListElement.appendChild(li);
    }


    // Append Files
    let fileList = await getListOfFiles(currDir);
    for (let file of fileList) {
        let li = document.createElement("li");

        li.innerHTML = file;

        li.addEventListener('click', () => {

            getDocumentText("document-filename", "document-text", currDir, file);
        }); 

        documentListElement.appendChild(li);
    }

    
}

generateDocumentList("document-list-tree");
//#endregion