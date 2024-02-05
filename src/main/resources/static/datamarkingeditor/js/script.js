import { DocumentService } from "../../arbitragestatistics/js/api/DocumentService.js";
import { DMEService } from "./api/DMEService.js";

const dmeService = new DMEService();

//#region Helper
function wrapText(txt, startIndex, endIndex, startWrap, endWrap) {

    let wrappedTxt = txt.substring(0, startIndex) +
        startWrap +
        txt.substring(startIndex, endIndex) +
        endWrap +
        txt.substring(endIndex);

    return wrappedTxt;
}

function getCurrentTime() {
    const date = new Date();

    let hours = date.getHours();
    let minutes = date.getMinutes();
    let seconds = date.getSeconds();

    return `${hours}:${minutes}:${seconds}`;
}

const clone = (items) => items.map(item => Array.isArray(item) ? clone(item) : item);
//#endregion

//#region Components
function EventElement(stateIndex, startWrap, endWrap, isCurrentState) {
    const eventDiv = document.createElement("div");
    
    eventDiv.classList.add("button");
    eventDiv.classList.add("event");
    if (isCurrentState) {
        eventDiv.classList.add("current-event");
    }

    eventDiv.innerHTML = `
        <div class="text-wrap-action">${stateIndex}. ${startWrap} ... ${endWrap}</div>
        <div class="text-wrap-time">${undoRedoStack[stateIndex].time}</div>
    `;

    eventDiv.addEventListener('click', () => {
        currentStateStackIndex = stateIndex;
        
        setCurrentState(undoRedoStack[stateIndex]);
        updateUndoRedoManagerView();
    });

    return eventDiv;
}

function DirectoryListElement(directoryName, currDir=null) {
    if (currDir === null) currDir = "";

    let li = document.createElement("li");

    let span = document.createElement("span");
    span.classList.add("caret");
    span.classList.add("panel");
    span.classList.add("button");

    span.innerText = directoryName;
    span.path = `${currDir}/${directoryName}`; 

    const recursionGenerateDocs = async () => {
        const ul = document.createElement("ul");
        ul.classList.add("nested-tree");
        ul.id = `ul-${span.path}`;
        span.insertAdjacentElement("afterend", ul);
        
        treeViewToggler();
        
        await generateDocumentList(ul.id, span.path)
        .then(() => span.removeEventListener('click', recursionGenerateDocs));
            
        span.click();
    };
    
    span.addEventListener('click', recursionGenerateDocs, { once: true });
    
    li.appendChild(span);

    return li
}

function FileListElement(fileName, currDir=null) {
    if (currDir === null) currDir = "";

    let li = document.createElement("li");
    li.classList.add("panel");
    li.classList.add("button");
    li.innerHTML = fileName;

    li.addEventListener('click', async () => {

        // Save state here

        await DocumentService.getDocumentText(currDir, fileName)
        .then((data) => {
            const docFileName = document.getElementById("filename");
            const docTitle = document.getElementById("filetitle");
            const docText = document.getElementById("text-data-marking");
            const docTextView = document.getElementById("text-data-marking-view");
        
            docFileName.innerHTML = `${currDir}/${fileName}`;
            
            if (data.error === undefined) {
                docTitle.innerHTML = `${data.title}`;
                docText.value = data.text;
                docTextView.innerHTML = data.text;
            } else {
                docTitle.innerHTML = "Error";
                docText.value = data.error;
                docTextView.innerHTML = data.error;
            }

            docText.dispatchEvent(new Event('change'));

            filepath = fileName;
            filename = currDir;
        })
        // .then(() => {
        //     if (this.fileViewClass.isRegEx) {
        //         document.getElementById("regex-input").dispatchEvent(new Event('input'));
        //     }
        // });



        let previouslySelected = document.querySelector('.selected');
        if (previouslySelected) {
            previouslySelected.classList.remove('selected');
        }

        li.classList.add('selected');
    });

    return li;
}
//#endregion


//#region Event Listener Handlers
async function saveAsButtonClickEvent() {    
    const saveType = document.getElementById("save-as-select").value;
    const dataToSave = textarea.value;

    await dmeService.save(dataToSave, saveType);
}

function textareaMouseupEvent() {
    startIndex = textarea.selectionStart;
    endIndex = textarea.selectionEnd;  

    startIndexField.innerText = startIndex;
    endIndexField.innerText = endIndex;
}

function tokenSelectChangeEvent() {
    const tokenSelectValue = tokenSelect.value;

    const startWrapInput = document.getElementById("starts-with-wrap");
    const endWrapInput = document.getElementById("ends-with-wrap"); 

    let startWrap = "";
    let endWrap = "";

    if (tokenSelectValue !== "Other") {
        startWrap = `<${tokenSelectValue}>`;
        endWrap = `</${tokenSelectValue}>`;
    }

    startWrapInput.value = startWrap;
    endWrapInput.value = endWrap;
}

function wrapButtonClickEvent() {
    
    let txt = textarea.value;
    
    const startWrap = document.getElementById("starts-with-wrap").value;
    const endWrap = document.getElementById("ends-with-wrap").value;    
    
    textarea.value = wrapText(txt, startIndex, endIndex, startWrap, endWrap);
    textareaView.innerHTML = textarea.value;

    if (currentStateStackIndex !== undoRedoStack.length - 1) {
        undoRedoStack = undoRedoStack.slice(0, currentStateStackIndex + 1);
    }
    
    saveState(getCurrentState());
}

function viewButtonClickEvent() {
    viewButton.style.display = "none";
    editButton.style.display = "flex";

    textareaView.style.display = "block";
    textarea.style.display = "none";

    startIndexField.innerText = "?";
    endIndexField.innerText = "?";
}

function editButtonClickEvent() {
    viewButton.style.display = "flex";
    editButton.style.display = "none";

    textareaView.style.display = "none";
    textarea.style.display = "block";

    startIndexField.innerText = startIndex;
    endIndexField.innerText = endIndex;
}

function undoButtonClickEvent() {
    undoState();
}

function redoButtonClickEvent() {
    redoState();
}
//#endregion

//#region State 
function getCurrentState() {
    // Copying objects
    let state = {
        filepathState: filepath,
        filenameState: filename,

        textareaState: textarea.value,
        textareaViewState: textareaView.innerHTML,

        startIndexFieldState: startIndexField.innerHTML,
        endIndexFieldState: endIndexField.innerHTML,
        startIndexState: startIndex,
        endIndexState: endIndex,

        startsWith: document.getElementById("starts-with-wrap").value,
        endsWith: document.getElementById("ends-with-wrap").value,

        viewButtonState: viewButton.innerHTML,
        editButtonState: editButton.innerHTML,

        time: getCurrentTime(),
    };


    return state;
}

function setCurrentState(state) {
    ({
        filenameState: filename,
        filepathState: filepath,

        textareaState: textarea.value,
        textareaViewState: textareaView.innerHTML,

        startIndexFieldState: startIndexField.innerHTML,
        endIndexFieldState: endIndexField.innerHTML,
        startIndexState: startIndex,
        endIndexState: endIndex,

        viewButtonState: viewButton.innerHTML,
        editButtonState: editButton.innerHTML,
    } = state);
}

function saveState(state) {
    undoRedoStack.push(state);
    currentStateStackIndex++;

    // saveStack

    updateUndoRedoManagerView();
}

function undoState() {
    if (currentStateStackIndex - 1 < 0) return;

    setCurrentState(undoRedoStack[--currentStateStackIndex]);

    updateUndoRedoManagerView();
}

function redoState() {
    if (currentStateStackIndex + 1 >= undoRedoStack.length) return;

    setCurrentState(undoRedoStack[++currentStateStackIndex]);
    
    updateUndoRedoManagerView();
}

function updateUndoRedoManagerView() {
    saveStackStateToLocal();

    const eventListElement = document.getElementById("event-list");
    eventListElement.innerHTML = "";

    for (let stateIndex = 0; stateIndex < undoRedoStack.length; stateIndex++) {

        let startWrap = undoRedoStack[stateIndex].startsWith.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        let endWrap = undoRedoStack[stateIndex].endsWith.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        let isCurrentState = stateIndex === currentStateStackIndex;

        eventListElement.appendChild(EventElement(stateIndex, startWrap, endWrap, isCurrentState));

        eventListElement.getElementsByClassName("event")[stateIndex]
    }   
}

function saveStackStateToLocal() {
    const stateStack = {undoRedoStackState: undoRedoStack, currentStateStackIndexState: currentStateStackIndex};
    localStorage.setItem("stateStack", JSON.stringify(stateStack));
}

//#endregion

//#region Variables
let filepath = null;
let filename = null;

const saveAsButton = document.getElementById("save-as-button");

let textarea = document.getElementById("text-data-marking");
let textareaView = document.getElementById("text-data-marking-view");
textareaView.innerHTML = textarea.value;

const wrapButton = document.getElementById("wrap-button");

const tokenSelect = document.getElementById("standard-tokens-select");

let startIndexField = document.getElementById("start-index");
let endIndexField = document.getElementById("end-index");
let startIndex = 0;
let endIndex = 0;

let viewButton = document.getElementById("view-button");
let editButton = document.getElementById("edit-button");

const undoButton = document.getElementById("undo-button");
const redoButton = document.getElementById("redo-button");
let undoRedoStack = [];
let currentStateStackIndex = -1;

if (localStorage.getItem("stateStack")) {
    const stateStack = JSON.parse(localStorage.getItem("stateStack"));
    console.log(stateStack);
    ({
        undoRedoStackState: undoRedoStack, 
        currentStateStackIndexState: currentStateStackIndex
    } = stateStack);
    
    setCurrentState(undoRedoStack[currentStateStackIndex]);
    updateUndoRedoManagerView();
} else {
    saveState(getCurrentState());
}
//#endregion

//#region Evet Listeners
saveAsButton.addEventListener('click', saveAsButtonClickEvent);
// When click on select element, button element click event listener don't call
document.getElementById("save-as-select").addEventListener('click', (e) => {e.stopPropagation();});

textarea.addEventListener('mouseup', textareaMouseupEvent);
textarea.addEventListener('keyup', textareaMouseupEvent);

tokenSelect.addEventListener('change', tokenSelectChangeEvent);

wrapButton.addEventListener('click', wrapButtonClickEvent);

viewButton.addEventListener("click", viewButtonClickEvent);
editButton.addEventListener("click", editButtonClickEvent);

undoButton.addEventListener('click', undoButtonClickEvent);
redoButton.addEventListener('click', redoButtonClickEvent);
//#endregion


//#region File Catalog
function treeViewToggler() {
    let treeViewToggler = document.getElementsByClassName("caret");
    for (let i = 0; i < treeViewToggler.length; i++) {

        treeViewToggler[i].addEventListener('click', function() {
            this.parentElement.querySelector(".nested-tree").classList.toggle("active-tree");
            this.classList.toggle("caret-down");

            this.classList.toggle("panel");
            this.classList.toggle("inner-panel");
        });

    }
}

treeViewToggler();

let documentCount = 0;
async function generateDocumentList(elementId, currDir=null) {
    if (currDir === null) {
        currDir = "";
    }

    const documentListElement = document.getElementById(elementId);

    // Append Dirs
    let dirList = await DocumentService.getListOfDirectories(currDir);
    for (let dir of dirList) {
        documentListElement
        .appendChild(DirectoryListElement(dir, currDir));
    }


    // Append Files
    let fileList = await DocumentService.getListOfFiles(currDir);
    
    for (let file of fileList) {
        documentListElement
        .appendChild(FileListElement(file, currDir));
        
        documentCount++;
    }

    if (documentCount !== 0) {
        document.getElementById("doc-list-count").innerHTML = documentCount;
    }

    console.log("Dirs generated");
}

generateDocumentList("file-catalog");
//#endregion


